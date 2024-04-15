package edu.ucf.cop4331project.common.config;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.kotlin.ObjectMappingKt;
import org.spongepowered.configurate.reference.ConfigurationReference;
import org.spongepowered.configurate.reference.ValueReference;
import org.spongepowered.configurate.reference.WatchServiceListener;
import org.spongepowered.configurate.serialize.TypeSerializerCollection;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

class ConfigHandler<T> implements AutoCloseable {

    private final Class<T> clazz;
    private final ConfigurationReference<CommentedConfigurationNode> base;
    private final ValueReference<T, CommentedConfigurationNode> config;
    private static final Logger LOGGER = Logger.getLogger(ConfigHandler.class.getName());

    public ConfigHandler(Class<T> clazz, WatchServiceListener fileWatcher, TypeSerializerCollection serializers) throws IOException {
        String fileName = clazz.getSimpleName().toLowerCase() + ".yml";
        Path configFile = Paths.get(fileName);

        // If the file doesn't already exist, let's try to find a default one
        if (!Files.exists(configFile)) {
            try (InputStream includedDefault =  clazz.getResourceAsStream("/" + fileName)) {
                if (includedDefault != null) Files.copy(includedDefault, configFile);
            }
        }

        // Load it with Configurate
        YamlConfigurationLoader.Builder loader = YamlConfigurationLoader.builder()
                .nodeStyle(NodeStyle.BLOCK)
                .defaultOptions(opts -> opts
                        .shouldCopyDefaults(true)
                        .implicitInitialization(false)
                        .serializers(b -> b.registerAll(serializers).registerAnnotatedObjects(ObjectMappingKt.objectMapperFactory()))
                );
        this.clazz = clazz;
        this.base = fileWatcher.listenToConfiguration(file -> loader.path(file).build(), configFile);
        this.config = base.referenceTo(clazz);

        // Also, log when the file is updated
        fileWatcher.listenToFile(
                configFile,
                event -> LOGGER.log(Level.INFO, "Updated config file {}/{}", new Object[]{configFile.getFileName()})
        );
    }

    public T getConfig() {
        return config.get();
    }

    public void saveConfig() throws ConfigurateException {
        base.node().set(clazz, getConfig());
        base.loader().save(base.node());
    }

    @Override
    public void close() {
        base.close();
    }
}
