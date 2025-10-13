package com.sparky.ui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static com.sparky.ui.UIDSL.*;

/**
 * Демонстраційний тест для розширеного модуля UI.
 *
 * @author Андрій Будильников
 */
class EnhancedUIDemoTest {
    
    @Test
    void testEnhancedUIFunctionality() {
        // Test creating UI elements using DSL
        Container mainContainer = container("main", c -> {
            c.setPosition(0, 0);
            c.setSize(800, 600);
        });
        
        // Test Button
        Button button = button("testButton", b -> {
            b.setText("Click Me");
            b.setPosition(10, 10);
            b.setSize(100, 30);
        });
        
        // Test Label
        Label label = label("testLabel", l -> {
            l.setText("Hello World");
            l.setPosition(10, 50);
            l.setSize(150, 20);
        });
        
        // Test TextField
        TextField textField = textField("testTextField", tf -> {
            tf.setText("Initial text");
            tf.setPlaceholder("Enter text here");
            tf.setPosition(10, 80);
            tf.setSize(200, 25);
        });
        
        // Test CheckBox
        CheckBox checkBox = checkBox("testCheckBox", cb -> {
            cb.setText("Check me");
            cb.setChecked(true);
            cb.setPosition(10, 120);
            cb.setSize(100, 20);
        });
        
        // Test Slider
        Slider slider = slider("testSlider", s -> {
            s.setMinValue(0);
            s.setMaxValue(100);
            s.setValue(50);
            s.setPosition(10, 150);
            s.setSize(200, 20);
        });
        
        // Test Panel
        Panel panel = panel("testPanel", p -> {
            p.setBackgroundColor(0xFFCCCCCC);
            p.setHasBorder(true);
            p.setBorderColor(0xFF000000);
            p.setPosition(300, 10);
            p.setSize(200, 200);
        });
        
        // Add elements to container
        addChild(mainContainer, button);
        addChild(mainContainer, label);
        addChild(mainContainer, textField);
        addChild(mainContainer, checkBox);
        addChild(mainContainer, slider);
        addChild(mainContainer, panel);
        
        // Test container children
        assertEquals(6, mainContainer.getChildren().size());
        
        // Test FlowLayout
        FlowLayout flowLayout = new FlowLayout(10, 10);
        mainContainer.setLayoutManager(flowLayout);
        mainContainer.doLayout();
        
        // Test BorderLayout
        BorderLayout borderLayout = new BorderLayout(5, 5);
        borderLayout.addComponent(button, BorderLayout.NORTH);
        borderLayout.addComponent(label, BorderLayout.SOUTH);
        borderLayout.addComponent(textField, BorderLayout.CENTER);
        
        Container borderContainer = container("borderContainer", c -> {
            c.setPosition(0, 0);
            c.setSize(400, 300);
            c.setLayoutManager(borderLayout);
        });
        
        // Test UI element properties
        assertEquals("Click Me", button.getText());
        assertEquals("Hello World", label.getText());
        assertEquals("Initial text", textField.getText());
        assertTrue(checkBox.isChecked());
        assertEquals(50.0f, slider.getValue(), 0.001f);
        
        // Test event handlers
        boolean[] buttonClicked = {false};
        button.setOnClick(b -> buttonClicked[0] = true);
        button.click();
        assertTrue(buttonClicked[0]);
        
        boolean[] checkBoxToggled = {false};
        checkBox.setOnCheck(checked -> checkBoxToggled[0] = true);
        checkBox.toggle();
        assertTrue(checkBoxToggled[0]);
        
        boolean[] sliderChanged = {false};
        slider.setOnValueChanged(value -> sliderChanged[0] = true);
        slider.setValue(75);
        assertTrue(sliderChanged[0]);
        assertEquals(75.0f, slider.getValue(), 0.001f);
        
        System.out.println("SparkyLib Enhanced UI functionality test passed!");
    }
}