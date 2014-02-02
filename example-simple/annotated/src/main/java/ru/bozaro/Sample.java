package ru.bozaro;

import ru.bozaro.annotation.SampleAnnotation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Пример использования аннотаций.
 *
 * @author Artem V. Navrotskiy (bozaro at buzzsoft.ru)
 */
@SampleAnnotation
public class Sample {
    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (
                InputStream stream = classLoader.getResourceAsStream("ru/bozaro/content.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream))
        ) {
            System.out.println(reader.readLine());
        }
    }
}
