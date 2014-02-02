package ru.bozaro.processor;

import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import org.jetbrains.annotations.NotNull;
import ru.bozaro.annotation.FortyTwoAnnotation;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Простой пример обработчика аннотаций.
 *
 * @author Artem V. Navrotskiy (bozaro at buzzsoft.ru)
 */
public class FortyTwoProcessor extends AbstractProcessor {
    @NotNull
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<>(Arrays.asList(
                FortyTwoAnnotation.class.getName()
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
        final Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
        final JavacElements elementUtils = (JavacElements) processingEnv.getElementUtils();
        final TreeMaker treeMaker = TreeMaker.instance(context);
        for (Element element : roundEnv.getElementsAnnotatedWith(FortyTwoAnnotation.class)) {
            JCTree.JCVariableDecl jcVarDecl = (JCTree.JCVariableDecl) elementUtils.getTree(element);
            jcVarDecl.init = treeMaker.Literal(42);
        }
        return true;
    }
}
