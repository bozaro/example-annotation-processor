package ru.bozaro.processor;

import net.java.dev.hickory.testing.Compilation;
import org.junit.Assert;
import org.junit.Test;

import java.io.StringWriter;

/**
 * Класс для проверки аннотаций.
 *
 * @author Artem V. Navrotskiy (bozaro at buzzsoft.ru)
 */
public class SampleProcessorTest {
    @Test
    public void testProcessor() {
        final Compilation compile = new Compilation();
        compile.useProcessor(new SampleProcessor());
        compile.addSource("ru.bozaro.Fake").addLine("package ru.bozaro;\n" +
                "import ru.bozaro.annotation.SampleAnnotation;\n" +
                "@SampleAnnotation\n" +
                "public class Fake {\n" +
                "}\n"
        );
        compile.doCompile(new StringWriter());
        Assert.assertEquals("[warning: Found annotated element: ru.bozaro.Fake]", compile.getDiagnostics().toString());
        final String resource = compile.getGeneratedResource("ru/bozaro/content.txt");
        Assert.assertEquals("ru.bozaro.Fake\n", resource);
    }
}
