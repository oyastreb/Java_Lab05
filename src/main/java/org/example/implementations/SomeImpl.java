package com.example.implementations;

import com.example.interfaces.SomeInterface;

/**
 * Первая реализация интерфейса {@link SomeInterface}.
 * Выводит букву "A" при вызове метода {@link #doSomething()}.
 *
 * @see SomeInterface
 * @see OtherImpl
 * @since 1.0
 */
public class SomeImpl implements SomeInterface {

    /**
     * Выполняет действие - выводит букву "A" в стандартный вывод.
     *
     * @see SomeInterface#doSomething()
     */
    @Override
    public void doSomething() {
        System.out.println("A");
    }
}