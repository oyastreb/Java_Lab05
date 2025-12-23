package com.example.injector;

import com.example.annotation.AutoInjectable;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * Класс для автоматического внедрения зависимостей в объекты.
 * Использует механизм рефлексии для поиска полей, помеченных аннотацией
 * {@link AutoInjectable}, и инициализирует их экземплярами классов,
 * указанных в файле конфигурации {@code config.properties}.
 *
 * <p>Файл конфигурации должен находиться в classpath (обычно в папке
 * {@code src/main/resources}) и содержать строки вида:
 * {@code полное.имя.интерфейса=полное.имя.класса.реализации}</p>
 *
 * <p>Пример использования:
 * <pre>
 * SomeBean bean = new SomeBean();
 * Injector injector = new Injector();
 * SomeBean injectedBean = injector.inject(bean);
 * injectedBean.foo();
 * </pre>
 * </p>
 *
 * @see AutoInjectable
 * @since 1.0
 */
public class Injector {

    /**
     * Свойства, загруженные из файла конфигурации.
     * Ключи - полные имена интерфейсов, значения - полные имена классов реализаций.
     */
    private final Properties properties;

    /**
     * Создает новый экземпляр инжектора и загружает конфигурацию
     * из файла {@code config.properties}.
     *
     * <p>Если файл конфигурации не найден или не может быть прочитан,
     * выводится сообщение об ошибке в стандартный поток ошибок.</p>
     */
    public Injector() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                System.err.println("Файл config.properties не найден в classpath!");
                return;
            }
            properties.load(input);
        } catch (Exception e) {
            System.err.println("Не удалось загрузить файл конфигурации: " + e.getMessage());
        }
    }

    /**
     * Внедряет зависимости в переданный объект.
     *
     * <p>Метод анализирует все поля переданного объекта, находит поля,
     * помеченные аннотацией {@link AutoInjectable}, и инициализирует их
     * экземплярами классов, указанных в файле конфигурации для соответствующих
     * типов полей (интерфейсов).</p>
     *
     * @param <T> тип объекта для внедрения зависимостей
     * @param object объект, в который нужно внедрить зависимости
     * @return тот же объект с инициализированными полями
     *
     * @throws RuntimeException если возникает ошибка при создании экземпляра
     *         класса реализации (ошибка выводится в stderr, но исключение не пробрасывается)
     *
     * @see AutoInjectable
     */
    public <T> T inject(T object) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(AutoInjectable.class)) {
                processField(object, field);
            }
        }

        return object;
    }

    /**
     * Обрабатывает отдельное поле, требующее внедрения зависимости.
     *
     * @param object объект, содержащий поле
     * @param field поле для обработки
     */
    private void processField(Object object, Field field) {
        Class<?> fieldType = field.getType();
        String interfaceName = fieldType.getName();

        String implementationClassName = properties.getProperty(interfaceName);

        if (implementationClassName != null && !implementationClassName.trim().isEmpty()) {
            initializeField(object, field, implementationClassName.trim());
        } else {
            System.err.println("Для интерфейса " + interfaceName +
                    " не найдена реализация в файле конфигурации");
        }
    }

    /**
     * Инициализирует поле экземпляром указанного класса.
     *
     * @param object объект, содержащий поле
     * @param field поле для инициализации
     * @param implementationClassName полное имя класса реализации
     */
    private void initializeField(Object object, Field field, String implementationClassName) {
        try {
            Class<?> implementationClass = Class.forName(implementationClassName);
            Object implementationInstance = implementationClass.getDeclaredConstructor().newInstance();

            field.setAccessible(true);
            field.set(object, implementationInstance);

            System.out.println("Успешно внедрена зависимость: " +
                    field.getType().getSimpleName() + " -> " +
                    implementationClass.getSimpleName());
        } catch (Exception e) {
            System.err.println("Ошибка при создании экземпляра класса " +
                    implementationClassName + ": " + e.getMessage());
        }
    }
}