package edu.ucf.cop4331project.common.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.reference.WatchServiceListener;
import org.spongepowered.configurate.serialize.TypeSerializerCollection;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class ConfigManager implements Closeable {

    private final WatchServiceListener fileWatcher;
    private final Map<Class<?>, ConfigHandler<?>> configs = new ConcurrentHashMap<>();
    private final TypeSerializerCollection.Builder serializerBuilder = TypeSerializerCollection.defaults().childBuilder();
    private final Logger logger = Logger.getLogger(ConfigManager.class.getName());

    @Inject
    public ConfigManager() throws IOException {
        this.fileWatcher = WatchServiceListener.builder()
                .taskExecutor(
                        Executors.newSingleThreadExecutor(
                                new ThreadFactoryBuilder()
                                        .setNameFormat("config-task-executor-%d")
                                        .setUncaughtExceptionHandler(($, e) -> logger.log(Level.SEVERE, "Uncaught exception in config task executor", e))
                                        .build()
                        )
                )
                .threadFactory(
                        new ThreadFactoryBuilder()
                                .setNameFormat("config-watcher-%d")
                                .setUncaughtExceptionHandler(($, e) -> logger.log(Level.SEVERE,"Uncaught exception in config watcher", e))
                                .build()
                )
                .build();
    }

    /**
     * Initialize the configuration for the specified class.
     *
     * @param clazz the class
     */
    public void initConfigs(Class<?> clazz) {
        try {
            configs.put(clazz, new ConfigHandler<>(clazz, fileWatcher, serializerBuilder.build()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the configuration for the specified class
     *
     * @param clazz the class
     * @param <T> the type of the configuration
     * @return the configuration
     */
    @SuppressWarnings("unchecked")
    public <T> T getConfig(Class<T> clazz) {
        return (T) configs.computeIfAbsent(clazz, klazz -> {
            // We can just auto-initialize the config here if the consumer didn't call initConfigs
            try {
                return new ConfigHandler<>(clazz, fileWatcher, serializerBuilder.build());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).getConfig();
    }

    public void saveConfig(Class<?> clazz) {
        try {
            configs.get(clazz).saveConfig();
        } catch (ConfigurateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws IOException {
        for (ConfigHandler<?> configHandler : configs.values()) configHandler.close();
        fileWatcher.close(); // This also closes the executor we made for it
    }
}
