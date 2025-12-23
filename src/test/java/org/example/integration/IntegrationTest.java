package com.example.integration;

import com.example.Main;
import com.example.beans.SomeBean;
import com.example.implementations.OtherImpl;
import com.example.implementations.SomeImpl;
import com.example.injector.Injector;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

class IntegrationTest {

    @Test
    void testFullInjectionFlow() {
        SomeBean bean = new SomeBean();
        Injector injector = new Injector();

        // Инъекция зависимостей
        SomeBean injectedBean = injector.inject(bean);

        // Проверяем, что поля инициализированы
        assertNotNull(getFieldValue(injectedBean, "field1"),
                "field1 должен быть инициализирован");
        assertNotNull(getFieldValue(injectedBean, "field2"),
                "field2 должен быть инициализирован");

        // Проверяем типы
        Object field1 = getFieldValue(injectedBean, "field1");
        assertTrue(field1 instanceof SomeImpl || field1 instanceof OtherImpl,
                "field1 должен быть SomeImpl или OtherImpl");
    }

    @Test
    void testMainMethodOutput() {
        // Перехватываем вывод консоли
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));

        try {
            // Запускаем main метод
            Main.main(new String[]{});

            String output = out.toString().replace("\r\n", "\n");

            // Проверяем ожидаемый вывод
            assertTrue(output.contains("=== Демонстрация внедрения зависимостей ==="),
                    "Должен быть заголовок");
            assertTrue(output.contains("Создание SomeBean:"),
                    "Должен создаваться SomeBean");
            assertTrue(output.contains("Результат вызова foo():"),
                    "Должен вызываться foo()");
            assertTrue(output.contains("Создание AnotherBean:"),
                    "Должен создаваться AnotherBean");

            // Проверяем, что вывод соответствует конфигурации
            if (output.contains("AC")) {
                assertTrue(output.contains("AnotherBean: A"),
                        "Для SomeImpl должно быть A");
            } else if (output.contains("BC")) {
                assertTrue(output.contains("AnotherBean: B"),
                        "Для OtherImpl должно быть B");
            }

        } finally {
            // Восстанавливаем System.out
            System.setOut(originalOut);
        }
    }

    @Test
    void testMultipleInjections() {
        Injector injector = new Injector();

        // Создаем и инжектим несколько объектов
        SomeBean bean1 = injector.inject(new SomeBean());
        SomeBean bean2 = injector.inject(new SomeBean());

        // Проверяем, что оба объекта инициализированы
        assertNotNull(getFieldValue(bean1, "field1"));
        assertNotNull(getFieldValue(bean2, "field1"));

        // Они должны быть разными экземплярами (не синглтоны)
        assertNotSame(getFieldValue(bean1, "field1"),
                getFieldValue(bean2, "field1"));
    }

    @Test
    void testErrorHandling() {
        // Тестируем обработку ошибок

        // 1. Несуществующий класс в конфигурации
        // (Для этого теста нужно временно изменить config.properties)

        // 2. Класс без конструктора по умолчанию
        class NoDefaultConstructor {
            public NoDefaultConstructor(String param) {}
        }

        // 3. Интерфейс без реализации в конфигурации
        class UnconfiguredInterfaceBean {
            @com.example.annotation.AutoInjectable
            private java.util.List<?> list; // Интерфейс, которого нет в конфигурации
        }

        Injector injector = new Injector();
        UnconfiguredInterfaceBean bean = new UnconfiguredInterfaceBean();

        // Не должно падать с исключением
        assertDoesNotThrow(() -> injector.inject(bean));
    }

    // Вспомогательный метод для получения значения поля через reflection
    private Object getFieldValue(Object obj, String fieldName) {
        try {
            java.lang.reflect.Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            return null;
        }
    }
}