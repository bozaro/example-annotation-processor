package ru.bozaro.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Тестовая аннотация.
 *
 * @author Artem V. Navrotskiy (bozaro at buzzsoft.ru)
 */
@Target(ElementType.METHOD)
public @interface HelloAnnotation {
}
