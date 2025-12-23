package com.example.implementations;

import com.example.interfaces.SomeInterface;

/**
 * Вторая реализация интерфейса {@link SomeInterface}.
 * Выводит букву "B" при вызове метода {@link #doSomething()}.
 *
 * @see SomeInterface
 * @see SomeImpl
 * @since 1.0
 */
public class OtherImpl implements SomeInterface {

    /**
     * Выполняет действие - выводит букву "B" в стандартный вывод.
     *
     * @see SomeInterface#doSomething()
     */
    @Override
    public void doSomething() {
        System.out.println("B");
    }
}