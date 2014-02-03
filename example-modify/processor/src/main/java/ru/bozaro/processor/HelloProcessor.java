package ru.bozaro.processor;

import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import org.jetbrains.annotations.NotNull;
import ru.bozaro.annotation.HelloAnnotation;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Простой пример обработчика аннотаций.
 *
 * @author Artem V. Navrotskiy (bozaro at buzzsoft.ru)
 */
public class HelloProcessor extends AbstractProcessor {
    @NotNull
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<>(Arrays.asList(
                HelloAnnotation.class.getName()
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
        for (Element element : roundEnv.getElementsAnnotatedWith(HelloAnnotation.class)) {
            JCTree.JCMethodDecl jcMethodDecl = (JCTree.JCMethodDecl) elementUtils.getTree(element);

            jcMethodDecl.body = treeMaker.Block(0, List.of(
                    treeMaker.Exec(
                            treeMaker.Apply(
                                    List.<JCTree.JCExpression>nil(),
                                    treeMaker.Select(
                                            treeMaker.Select(
                                                    treeMaker.Ident(
                                                            elementUtils.getName("System")
                                                    ),
                                                    elementUtils.getName("out")
                                            ),
                                            elementUtils.getName("println")
                                    ),
                                    List.<JCTree.JCExpression>of(
                                            treeMaker.Literal("Hello, world!!!")
                                    )
                            )
                    ),
                    jcMethodDecl.body
            ));
        }
        return true;
    }
}
