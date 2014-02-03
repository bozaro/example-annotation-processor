package ru.bozaro.processor;

import com.sun.tools.javac.model.JavacElements;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Простой пример обработчика аннотаций.
 *
 * @author Artem V. Navrotskiy (bozaro at buzzsoft.ru)
 */
@SupportedAnnotationTypes("*")
public class EchoProcessor extends AbstractProcessor {
    @NotNull
    private final Map<String, String> sources = new HashMap<>();

    @NotNull
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            return false;
        }
        final JavacElements elementUtils = (JavacElements) processingEnv.getElementUtils();
        for (Element element : roundEnv.getRootElements()) {
            if (element instanceof TypeElement) {
                TypeElement typeElement = (TypeElement) element;
                String className = typeElement.getQualifiedName().toString();
                sources.put(className, elementUtils.getTree(element).toString());
            }
        }
        return false;
    }

    @Nullable
    public String getSource(@NotNull String name) {
        return sources.get(name);
    }
}
