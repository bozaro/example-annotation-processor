package ru.bozaro.processor;

import org.junit.Test;

/**
 * Класс для проверки аннотаций.
 *
 * @author Artem V. Navrotskiy (bozaro at buzzsoft.ru)
 */
public class HelloProcessorTest extends AnnotationProcessorTester {
    @Test
    public void testProcessor() {
        compareSource(
                "ru.bozaro.Fake",
                "package ru.bozaro;\n" +
                        "import ru.bozaro.annotation.HelloAnnotation;\n" +
                        "public class Fake {\n" +
                        "@HelloAnnotation\n" +
                        "private void check() {\n" +
                        "  System.out.println(\":)\");\n" +
                        "}\n" +
                        "}\n",
                "package ru.bozaro;\n" +
                        "import ru.bozaro.annotation.HelloAnnotation;\n" +
                        "public class Fake {\n" +
                        "@HelloAnnotation\n" +
                        "private void check() {\n" +
                        "  System.out.println(\"Hello, world!!!\");\n" +
                        "  {\n" +
                        "    System.out.println(\":)\");\n" +
                        "  }\n" +
                        "}\n" +
                        "}\n",
                new HelloProcessor());
    }
}
