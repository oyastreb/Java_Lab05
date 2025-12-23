package com.example;

import com.example.beans.SomeBean;
import com.example.beans.AnotherBean;
import com.example.injector.Injector;

/**
 * Главный класс приложения для демонстрации работы механизма внедрения зависимостей.
 *
 * <p>Этот класс содержит точку входа в программу и демонстрирует использование
 * класса {@link Injector} для автоматической инициализации объектов.</p>
 *
 * <p>Пример вывода программы зависит от конфигурации в файле
 * {@code src/main/resources/config.properties}:
 * <ul>
 *   <li>Если настроена реализация SomeImpl: вывод "AC" и "AnotherBean: A"</li>
 *   <li>Если настроена реализация OtherImpl: вывод "BC" и "AnotherBean: B"</li>
 * </ul>
 * </p>
 *
 * @see Injector
 * @see SomeBean
 * @see AnotherBean
 * @since 1.0
 */
public class Main {

    /**
     * Точка входа в программу.
     *
     * <p>Демонстрирует работу механизма внедрения зависимостей на двух примерах:
     * <ol>
     *   <li>Создание и инициализация объекта {@link SomeBean}</li>
     *   <li>Создание и инициализация объекта {@link AnotherBean}</li>
     * </ol>
     * </p>
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        System.out.println("=== Демонстрация внедрения зависимостей ===");

        // Пример 1: SomeBean
        System.out.println("\n1. Создание SomeBean:");
        SomeBean sb = (new Injector()).inject(new SomeBean());
        System.out.print("Результат вызова foo(): ");
        sb.foo();

        // Пример 2: AnotherBean
        System.out.println("\n2. Создание AnotherBean:");
        AnotherBean another = (new Injector()).inject(new AnotherBean());
        System.out.print("Результат вызова bar(): ");
        another.bar();

        System.out.println("\n=== Завершение работы ===");
    }
}