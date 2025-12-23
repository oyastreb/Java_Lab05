package com.example.beans;

import com.example.implementations.OtherImpl;
import com.example.implementations.SODoer;
import com.example.implementations.SomeImpl;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;

class SomeBeanTest {

    @Test
    void testSomeBeanStructure() throws NoSuchFieldException {
        // Проверяем структуру класса
        Field field1 = SomeBean.class.getDeclaredField("field1");
        Field field2 = SomeBean.class.getDeclaredField("field2");

        assertTrue(field1.isAnnotationPresent(com.example.annotation.AutoInjectable.class),
                "field1 должен иметь аннотацию @AutoInjectable");
        assertTrue(field2.isAnnotationPresent(com.example.annotation.AutoInjectable.class),
                "field2 должен иметь аннотацию @AutoInjectable");

        assertEquals("com.example.interfaces.SomeInterface",
                field1.getType().getName());
        assertEquals("com.example.interfaces.SomeOtherInterface",
                field2.getType().getName());
    }

    @Test
    void testNullFieldsWithoutInjection() {
        SomeBean bean = new SomeBean();

        // Без инъекции поля должны быть null
        try {
            Field field1 = SomeBean.class.getDeclaredField("field1");
            Field field2 = SomeBean.class.getDeclaredField("field2");

            field1.setAccessible(true);
            field2.setAccessible(true);

            assertNull(field1.get(bean), "field1 должен быть null без инъекции");
            assertNull(field2.get(bean), "field2 должен быть null без инъекции");

        } catch (Exception e) {
            fail("Ошибка reflection: " + e.getMessage());
        }
    }
}