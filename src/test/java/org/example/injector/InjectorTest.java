package com.example.injector;

import com.example.annotation.AutoInjectable;
import com.example.interfaces.SomeInterface;
import com.example.interfaces.SomeOtherInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import static org.junit.jupiter.api.Assertions.*;

class InjectorTest {

    // Тестовые классы
    static class TestBean {
        @AutoInjectable
        private SomeInterface field1;

        @AutoInjectable
        private SomeOtherInterface field2;

        private String nonInjectableField;

        public SomeInterface getField1() {
            return field1;
        }

        public SomeOtherInterface getField2() {
            return field2;
        }
    }

    static class EmptyBean {
        private String field;
    }

    private Injector injector;
    private static final String TEST_CONFIG = "test-config.properties";

    @BeforeEach
    void setUp() throws IOException {
        // Создаем тестовый конфигурационный файл
        Properties props = new Properties();
        props.setProperty("com.example.interfaces.SomeInterface",
                "com.example.implementations.SomeImpl");
        props.setProperty("com.example.interfaces.SomeOtherInterface",
                "com.example.implementations.SODoer");

        try (FileWriter writer = new FileWriter(TEST_CONFIG)) {
            props.store(writer, "Test configuration");
        }

        // Можно создать кастомный Injector для тестов
        // В реальном проекте лучше использовать @BeforeAll и статическую инициализацию
    }

    @Test
    void testInjectWithValidConfiguration() {
        TestBean bean = new TestBean();
        assertNull(bean.getField1(), "Поле должно быть null до инъекции");
        assertNull(bean.getField2(), "Поле должно быть null до инъекции");

        Injector injector = new Injector();
        TestBean injected = injector.inject(bean);

        assertNotNull(injected.getField1(), "Поле должно быть инициализировано после инъекции");
        assertNotNull(injected.getField2(), "Поле должно быть инициализировано после инъекции");
        assertEquals("com.example.implementations.SomeImpl",
                injected.getField1().getClass().getName());
        assertEquals("com.example.implementations.SODoer",
                injected.getField2().getClass().getName());
    }

    @Test
    void testInjectEmptyBean() {
        EmptyBean bean = new EmptyBean();
        Injector injector = new Injector();
        EmptyBean injected = injector.inject(bean);

        assertSame(bean, injected, "Должен возвращаться тот же объект");
        // Не должно быть исключений
    }

    @Test
    void testInjectWithMissingConfiguration() {
        // Класс с неизвестным интерфейсом
        class UnknownInterfaceBean {
            @AutoInjectable
            private Runnable unknownField; // Интерфейс, которого нет в конфигурации
        }

        UnknownInterfaceBean bean = new UnknownInterfaceBean();
        Injector injector = new Injector();

        // Не должно падать, только логировать ошибку
        assertDoesNotThrow(() -> {
            injector.inject(bean);
        });
    }

    @Test
    void testInjectReturnsSameInstance() {
        TestBean bean = new TestBean();
        Injector injector = new Injector();
        TestBean result = injector.inject(bean);

        assertSame(bean, result, "Метод inject должен возвращать тот же объект");
    }

    @Test
    void testInjectWithPrivateField() {
        TestBean bean = new TestBean();
        Injector injector = new Injector();

        // Не должно быть IllegalAccessException
        assertDoesNotThrow(() -> {
            injector.inject(bean);
        });

        assertNotNull(bean.getField1(), "Приватное поле должно быть доступно через reflection");
    }
}