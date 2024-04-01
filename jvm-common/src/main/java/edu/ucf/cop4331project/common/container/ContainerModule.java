package edu.ucf.cop4331project.common.container;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.multibindings.Multibinder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Binds all the {@link Container}.
 */
public class ContainerModule extends AbstractModule {

    private final Set<Class<?>> containers;

    public ContainerModule() {
        try (InputStream stream = ContainerModule.class.getClassLoader().getResourceAsStream(ContainerProcessor.PATH)) {
            if (stream == null) {
                this.containers = Collections.emptySet();
                return;
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
                this.containers = reader.lines().distinct().map(it -> {
                    try {
                        return Class.forName(it);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toSet());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void configure() {
        // Bind all the containers
        Multibinder<Object> binder = Multibinder.newSetBinder(binder(), Object.class);
        containers.forEach(container -> binder.addBinding().to(container).in(Scopes.SINGLETON));
    }
}
