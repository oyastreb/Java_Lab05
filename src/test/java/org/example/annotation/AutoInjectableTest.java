package com.example.annotation;

import org.junit.jupiter.api.Test;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;

class AutoInjectableTest {

    // Тестовый класс для проверки аннотации
    static class TestClass {
        @AutoInjectable
        private String field1;

        private String field2;
    }

    @Test
    void testAnnotationRetention() {
        // Проверяем, что аннотация доступна во время выполнения
        Field field = null;
        try {
            field = TestClass.class.getDeclaredField("field1");
        } catch (NoSuchFieldException e) {
            fail("Field not found");
        }

        Annotation annotation = field.getAnnotation(AutoInjectable.class);
        assertNotNull(annotation, "Аннотация должна быть доступна через reflection");
        assertEquals(AutoInjectable.class, annotation.annotationType());
    }

    @Test
    void testAnnotationTarget() throws NoSuchFieldException {
        // Проверяем, что аннотация применяется только к полям
        Field annotatedField = TestClass.class.getDeclaredField("field1");
        Field nonAnnotatedField = TestClass.class.getDeclaredField("field2");

        assertTrue(annotatedField.isAnnotationPresent(AutoInjectable.class),
                "Поле с аннотацией должно быть помечено");
        assertFalse(nonAnnotatedField.isAnnotationPresent(AutoInjectable.class),
                "Поле без аннотации не должно быть помечено");
    }
}