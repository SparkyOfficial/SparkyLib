package com.sparky.ui;

import java.util.function.Consumer;

/**
 * DSL для створення UI елементів.
 *
 * @author Андрій Будильников
 */
public class UIDSL {
    
    /**
     * Створює контейнер.
     */
    public static Container container(String id, Consumer<Container> config) {
        Container container = new Container(id);
        if (config != null) {
            config.accept(container);
        }
        return container;
    }
    
    /**
     * Створює кнопку.
     */
    public static Button button(String id, Consumer<Button> config) {
        Button button = new Button(id);
        if (config != null) {
            config.accept(button);
        }
        return button;
    }
    
    /**
     * Створює мітку.
     */
    public static Label label(String id, Consumer<Label> config) {
        Label label = new Label(id);
        if (config != null) {
            config.accept(label);
        }
        return label;
    }
    
    /**
     * Створює текстове поле.
     */
    public static TextField textField(String id, Consumer<TextField> config) {
        TextField textField = new TextField(id);
        if (config != null) {
            config.accept(textField);
        }
        return textField;
    }
    
    /**
     * Створює прапорець.
     */
    public static CheckBox checkBox(String id, Consumer<CheckBox> config) {
        CheckBox checkBox = new CheckBox(id);
        if (config != null) {
            config.accept(checkBox);
        }
        return checkBox;
    }
    
    /**
     * Створює повзунок.
     */
    public static Slider slider(String id, Consumer<Slider> config) {
        Slider slider = new Slider(id);
        if (config != null) {
            config.accept(slider);
        }
        return slider;
    }
    
    /**
     * Створює панель.
     */
    public static Panel panel(String id, Consumer<Panel> config) {
        Panel panel = new Panel(id);
        if (config != null) {
            config.accept(panel);
        }
        return panel;
    }
    
    /**
     * Додає дочірній елемент до контейнера.
     */
    public static <T extends UIElement> T addChild(Container parent, T child) {
        parent.addChild(child);
        return child;
    }
    
    /**
     * Встановлює менеджер компонування для контейнера.
     */
    public static Container withLayout(Container container, LayoutManager layoutManager) {
        container.setLayoutManager(layoutManager);
        return container;
    }
}