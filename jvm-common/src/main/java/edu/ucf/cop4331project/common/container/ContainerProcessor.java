package edu.ucf.cop4331project.common.container;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.StandardLocation;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes({
        "edu.ucf.cop4331project.common.container.Container"
})
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class ContainerProcessor extends AbstractProcessor {

    public static final String PATH = "META-INF/containers";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) return false;

        // Find all the containers
        List<String> containers = roundEnv.getElementsAnnotatedWith(Container.class)
                .stream()
                .filter(it -> it.getKind() == ElementKind.CLASS)
                .map(it -> it.asType().toString())
                .toList();

        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Found %d containers".formatted(containers.size()));

        // Write the containers to a file
        try (BufferedWriter writer = new BufferedWriter(processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", PATH).openWriter())) {
            writer.write(String.join("\n", containers));
            writer.flush();
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
            e.printStackTrace();
        }

        return true;
    }
}
