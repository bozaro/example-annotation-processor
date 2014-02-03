package ru.bozaro.processor;

import org.junit.Test;

/**
 * Класс для проверки аннотаций.
 *
 * @author Artem V. Navrotskiy (bozaro at buzzsoft.ru)
 */
public class FortyTwoProcessorTest extends AnnotationProcessorTester {
    @Test
    public void testProcessor() {
        compareSource(
                "ru.bozaro.Fake",
                "package ru.bozaro;\n" +
                        "import ru.bozaro.annotation.FortyTwoAnnotation;\n" +
                        "public class Fake {\n" +
                        "@FortyTwoAnnotation\n" +
                        "private final int number = 0;\n" +
                        "}\n",
                "package ru.bozaro;\n" +
                        "import ru.bozaro.annotation.FortyTwoAnnotation;\n" +
                        "public class Fake {\n" +
                        "@FortyTwoAnnotation\n" +
                        "private final int number = 42;\n" +
                        "}\n",
                new FortyTwoProcessor());
    }
}
