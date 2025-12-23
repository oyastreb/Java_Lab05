package com.example.implementations;

import com.example.interfaces.SomeOtherInterface;

/**
 * Реализация интерфейса {@link SomeOtherInterface}.
 * Выводит букву "C" при вызове метода {@link #doSomeOther()}.
 *
 * @see SomeOtherInterface
 * @since 1.0
 */
public class SODoer implements SomeOtherInterface {

    /**
     * Выполняет другое действие - выводит букву "C" в стандартный вывод.
     *
     * @see SomeOtherInterface#doSomeOther()
     */
    @Override
    public void doSomeOther() {
        System.out.println("C");
    }
}