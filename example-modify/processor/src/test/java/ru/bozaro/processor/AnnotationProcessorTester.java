package ru.bozaro.processor;

import net.java.dev.hickory.testing.Compilation;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;

import javax.annotation.processing.AbstractProcessor;
import java.io.StringWriter;

/**
 * Простой класс для тестирования Annotation Processor-ов.
 *
 * @author Artem V. Navrotskiy (bozaro at buzzsoft.ru)
 */
public abstract class AnnotationProcessorTester {

    protected void compareSource(@NotNull String className, @NotNull String originalSource, @NotNull String modifiedSource, @NotNull AbstractProcessor... processors) {
        final String actualSource = compileSource(className, originalSource, processors);
        final String expectedSource = compileSource(className, modifiedSource);
        Assert.assertEquals(actualSource, expectedSource);
    }

    @NotNull
    private String compileSource(@NotNull String className, @NotNull String source, @NotNull AbstractProcessor... processors) {
        final Compilation compile = new Compilation();
        for (AbstractProcessor processor : processors) {
            compile.useProcessor(processor);
        }
        final EchoProcessor echoProcessor = new EchoProcessor();
        compile.useProcessor(echoProcessor);
        compile.addSource(className).addLine(source);
        compile.doCompile(new StringWriter());
        Assert.assertEquals("[]", compile.getDiagnostics().toString());

        final String modified = echoProcessor.getSource(className);
        Assert.assertNotNull(modified);
        return modified;
    }
}
