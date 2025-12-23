package com.example.interfaces;

/**
 * Пример интерфейса для демонстрации работы механизма внедрения зависимостей.
 * Определяет контракт для выполнения некоторого действия.
 *
 * @see com.example.implementations.SomeImpl
 * @see com.example.implementations.OtherImpl
 * @since 1.0
 */
public interface SomeInterface {

    /**
     * Выполняет определенное действие.
     * Конкретная реализация метода определяется в классах-реализациях.
     */
    void doSomething();
}