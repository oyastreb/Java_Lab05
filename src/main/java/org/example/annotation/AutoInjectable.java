package com.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для пометки полей, требующих автоматического внедрения зависимостей.
 * Помеченные этой аннотацией поля будут инициализированы экземплярами классов,
 * указанными в файле конфигурации {@code config.properties}.
 *
 * <p>Аннотация может применяться только к полям классов.</p>
 *
 * @see com.example.injector.Injector
 * @since 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoInjectable {
}