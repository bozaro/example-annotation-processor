package ru.bozaro.processor;

import org.jetbrains.annotations.NotNull;
import ru.bozaro.annotation.SampleAnnotation;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * Простой пример обработчика аннотаций.
 *
 * @author Artem V. Navrotskiy (bozaro at buzzsoft.ru)
 */
public class SampleProcessor extends AbstractProcessor {
    @NotNull
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<>(Arrays.asList(
                SampleAnnotation.class.getName()
        ));
    }

    @NotNull
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            return true;
        }
        final List<String> content = new ArrayList<>();
        for (Element element : roundEnv.getElementsAnnotatedWith(SampleAnnotation.class)) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "Found annotated element: " + element.toString());
            content.add(element.toString());
        }
        try {
            FileObject resource = processingEnv.getFiler().createResource(StandardLocation.SOURCE_OUTPUT, "ru.bozaro", "content.txt");
            try (Writer writer = resource.openWriter()) {
                for (String line : content) {
                    writer.write(line + "\n");
                }
            }
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
        }
        return true;
    }
}
