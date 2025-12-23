package com.example.beans;

import com.example.annotation.AutoInjectable;
import com.example.interfaces.SomeInterface;

/**
 * Дополнительный пример класса для демонстрации работы инжектора.
 * Показывает, что механизм внедрения зависимостей работает с любыми классами,
 * содержащими поля с аннотацией {@link AutoInjectable}.
 *
 * @see AutoInjectable
 * @see com.example.injector.Injector
 * @since 1.0
 */
public class AnotherBean {

    /**
     * Поле типа {@link SomeInterface}, требующее внедрения зависимости.
     * Конкретная реализация будет определена в файле конфигурации.
     */
    @AutoInjectable
    private SomeInterface field;

    /**
     * Выполняет действие с использованием внедренной зависимости.
     * Выводит префикс "AnotherBean: " и результат вызова метода
     * {@link SomeInterface#doSomething()} на внедренном объекте.
     */
    public void bar() {
        System.out.print("AnotherBean: ");
        field.doSomething();
    }
}