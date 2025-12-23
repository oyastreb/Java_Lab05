package com.example.interfaces;

/**
 * Еще один пример интерфейса для демонстрации работы механизма внедрения зависимостей.
 * Определяет контракт для выполнения другого действия.
 *
 * @see com.example.implementations.SODoer
 * @since 1.0
 */
public interface SomeOtherInterface {

    /**
     * Выполняет другое действие.
     * Конкретная реализация метода определяется в классах-реализациях.
     */
    void doSomeOther();
}