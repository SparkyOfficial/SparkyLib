package com.sparky.libgdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.sparky.core.SparkyLogger;
import com.sparky.particles.ParticleSpec;

/**
 * Адаптер для відображення частинок LibGDX зі специфікацій SparkyLib.
 *
 * @author Андрій Будильников
 * @author Богдан Кравчук
 */
public class LibGDXParticleAdapter {
    private static final SparkyLogger logger = SparkyLogger.getLogger(LibGDXParticleAdapter.class);
    
    /**
     * Відображає ефект частинок LibGDX.
     */
    public void renderParticleEffect(com.sparky.particles.ParticleEffect effect, 
                                   SpriteBatch batch, 
                                   float x, float y) {
        // Convert SparkyLib particle specs to LibGDX particle effects and render them
        logger.debug("Rendering particle effect: " + effect.getName() + " at (" + x + ", " + y + ")");
        
        // This is a simplified example - in practice, you would need to convert
        // the SparkyLib particle specifications to LibGDX particle effects
        for (ParticleSpec spec : effect.getSpecs()) {
            // Render each particle spec
            renderParticleSpec(spec, batch, x, y);
        }
    }
    
    /**
     * Відображає окрему специфікацію частинок.
     */
    private void renderParticleSpec(ParticleSpec spec, SpriteBatch batch, float x, float y) {
        // Render the particle spec using LibGDX
        // This is a simplified implementation that draws simple particles
        logger.debug("Rendering particle spec: " + spec.getType());
        
        // For this implementation, we'll draw simple colored squares for particles
        batch.end(); // End the current batch
        
        // Set up shape renderer for particle drawing
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        
        // Set color based on particle type
        setColorByType(shapeRenderer, spec.getType());
        
        // Draw particles as small squares
        for (int i = 0; i < spec.getCount(); i++) {
            // Add some randomness to particle positions
            float offsetX = (float) (Math.random() * 20 - 10);
            float offsetY = (float) (Math.random() * 20 - 10);
            float size = 2 + (float) (Math.random() * 3); // Random size between 2 and 5
            
            shapeRenderer.rect(x + offsetX, y + offsetY, size, size);
        }
        
        shapeRenderer.end();
        batch.begin(); // Resume the batch
        
        logger.debug("Drawing " + spec.getCount() + " particles of type " + spec.getType() + 
                    " at (" + x + ", " + y + ") with speed " + spec.getSpeed());
    }
    
    /**
     * Sets the color based on particle type.
     */
    private void setColorByType(ShapeRenderer shapeRenderer, String type) {
        switch (type.toLowerCase()) {
            case "flame":
                shapeRenderer.setColor(Color.RED);
                break;
            case "smoke":
                shapeRenderer.setColor(Color.GRAY);
                break;
            case "heart":
                shapeRenderer.setColor(Color.PINK);
                break;
            case "note":
                shapeRenderer.setColor(Color.CYAN);
                break;
            case "portal":
                shapeRenderer.setColor(Color.PURPLE);
                break;
            default:
                shapeRenderer.setColor(Color.WHITE);
                break;
        }
    }
}