package com.sparky.libgdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.sparky.core.SparkyLogger;
import com.sparky.ui.Button;
import com.sparky.ui.Container;
import com.sparky.ui.UIElement;

/**
 * Адаптер для відображення UI елементів LibGDX зі специфікацій SparkyLib.
 *
 * @author Андрій Будильников
 * @author Богдан Кравчук
 */
public class LibGDXUIAdapter {
    private static final SparkyLogger logger = SparkyLogger.getLogger(LibGDXUIAdapter.class);
    
    private BitmapFont font;
    
    public LibGDXUIAdapter() {
        this.font = new BitmapFont();
    }
    
    /**
     * Відображає контейнер UI.
     */
    public void renderContainer(Container container, SpriteBatch batch) {
        if (!container.isVisible()) {
            return;
        }
        
        // Render all children
        for (UIElement child : container.getChildren()) {
            if (child instanceof Button) {
                renderButton((Button) child, batch);
            } else if (child instanceof Container) {
                renderContainer((Container) child, batch);
            }
        }
        
        logger.debug("Rendered container: " + container.getId());
    }
    
    /**
     * Відображає кнопку.
     */
    public void renderButton(Button button, SpriteBatch batch) {
        if (!button.isVisible()) {
            return;
        }
        
        // Render the button using LibGDX
        // Draw a rectangle for the button background
        batch.end(); // End the current batch
        
        // Set up shape renderer for button drawing
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        
        // Draw button background
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(button.getX(), button.getY(), button.getWidth(), button.getHeight());
        
        // Draw button border
        shapeRenderer.end();
        
        batch.begin(); // Resume the batch
        
        // Draw the button text
        font.draw(batch, button.getText(), button.getX() + 10, button.getY() + button.getHeight() - 5);
        
        logger.debug("Rendering button: " + button.getId() + " with text: " + button.getText());
    }
    
    /**
     * Встановлює шрифт для відображення тексту.
     */
    public void setFont(BitmapFont font) {
        this.font = font;
    }
}