package com.sparky.ui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Демонстраційний тест для модуля UI.
 *
 * @author Андрій Будильников
 */
class UIDemoTest {
    
    @Test
    void testUIFunctionality() {
        // Test Container
        Container container = new Container("testContainer");
        assertEquals("testContainer", container.getId());
        assertTrue(container.isVisible());
        assertEquals(0, container.getX());
        assertEquals(0, container.getY());
        assertEquals(0, container.getWidth());
        assertEquals(0, container.getHeight());
        
        // Test setting properties
        container.setPosition(10, 20);
        container.setSize(100, 200);
        container.setVisible(false);
        
        assertEquals(10, container.getX());
        assertEquals(20, container.getY());
        assertEquals(100, container.getWidth());
        assertEquals(200, container.getHeight());
        assertFalse(container.isVisible());
        
        // Test Button
        Button button = new Button("testButton");
        assertEquals("testButton", button.getId());
        assertTrue(button.isVisible());
        
        // Test button properties
        button.setText("Click Me");
        button.setPosition(5, 10);
        button.setSize(50, 30);
        
        assertEquals("Click Me", button.getText());
        assertEquals(5, button.getX());
        assertEquals(10, button.getY());
        assertEquals(50, button.getWidth());
        assertEquals(30, button.getHeight());
        
        // Test button click handler
        boolean[] clicked = {false};
        button.setOnClick(b -> clicked[0] = true);
        button.click();
        assertTrue(clicked[0]);
        
        // Test container with children
        container.addChild(button);
        assertEquals(1, container.getChildren().size());
        assertTrue(container.getChildren().contains(button));
        
        System.out.println("SparkyLib UI functionality test passed!");
    }
}