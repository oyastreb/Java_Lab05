package com.example.beans;

import com.example.annotation.AutoInjectable;
import com.example.interfaces.SomeInterface;
import com.example.interfaces.SomeOtherInterface;

/**
 * Пример класса, использующего внедрение зависимостей через аннотацию {@link AutoInjectable}.
 * Содержит два поля, которые должны быть автоматически инициализированы инжектором.
 *
 * <p>Обратите внимание, что инициализация полей не происходит в конструкторе или явно в коде -
 * она выполняется классом {@link com.example.injector.Injector}.</p>
 *
 * @see AutoInjectable
 * @see com.example.injector.Injector
 * @since 1.0
 */
public class SomeBean {

    /**
     * Поле типа {@link SomeInterface}, требующее внедрения зависимости.
     * Конкретная реализация будет определена в файле конфигурации.
     */
    @AutoInjectable
    private SomeInterface field1;

    /**
     * Поле типа {@link SomeOtherInterface}, требующее внедрения зависимости.
     * Конкретная реализация будет определена в файле конфигурации.
     */
    @AutoInjectable
    private SomeOtherInterface field2;

    /**
     * Выполняет действия с использованием внедренных зависимостей.
     * Вызывает методы {@link SomeInterface#doSomething()} и
     * {@link SomeOtherInterface#doSomeOther()} на внедренных объектах.
     *
     * <p>Пример вывода:
     * <ul>
     *   <li>Если field1 = {@link com.example.implementations.SomeImpl}, а field2 = {@link com.example.implementations.SODoer}: "AC"</li>
     *   <li>Если field1 = {@link com.example.implementations.OtherImpl}, а field2 = {@link com.example.implementations.SODoer}: "BC"</li>
     * </ul>
     * </p>
     */
    public void foo() {
        field1.doSomething();
        field2.doSomeOther();
    }
}