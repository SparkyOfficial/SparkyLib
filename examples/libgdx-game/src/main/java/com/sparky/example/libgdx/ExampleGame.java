package com.sparky.example.libgdx;

import com.sparky.events.EventBus;
import com.sparky.events.Subscribe;
import com.sparky.libgdx.LibGDXAdapter;
import com.sparky.libgdx.LibGDXParticleAdapter;
import com.sparky.libgdx.LibGDXUIAdapter;
import com.sparky.libgdx.LibGDXAssetAdapter;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Приклад LibGDX гри, що використовує SparkyLib.
 *
 * @author Андрій Будильников
 */
public class ExampleGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture img;
    private LibGDXAdapter libgdxAdapter;
    private LibGDXParticleAdapter particleAdapter;
    private LibGDXUIAdapter uiAdapter;
    private LibGDXAssetAdapter assetAdapter;
    private EventBus eventBus;
    
    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        
        // Ініціалізація адаптерів SparkyLib для LibGDX
        libgdxAdapter = new LibGDXAdapter();
        particleAdapter = new LibGDXParticleAdapter();
        uiAdapter = new LibGDXUIAdapter();
        assetAdapter = new LibGDXAssetAdapter();
        eventBus = libgdxAdapter.getEventBus();
        
        // Реєстрація слухачів подій
        eventBus.registerListener(this);
        
        Gdx.app.log("SparkyLib", "Example LibGDX Game created!");
    }
    
    @Override
    public void render() {
        // Оновлення адаптера LibGDX
        libgdxAdapter.render();
        
        // Очистка екрану
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Відображення спрайту
        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();
    }
    
    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        Gdx.app.log("SparkyLib", "Example LibGDX Game disposed!");
    }
    
    /**
     * Приклад обробника подій.
     */
    @Subscribe(priority = 1, async = false)
    public void onExampleEvent(ExampleEvent event) {
        Gdx.app.log("SparkyLib", "Received example event: " + event.getMessage());
    }
    
    /**
     * Отримує адаптер LibGDX.
     */
    public LibGDXAdapter getLibgdxAdapter() {
        return libgdxAdapter;
    }
    
    /**
     * Отримує адаптер частинок.
     */
    public LibGDXParticleAdapter getParticleAdapter() {
        return particleAdapter;
    }
    
    /**
     * Отримує адаптер UI.
     */
    public LibGDXUIAdapter getUiAdapter() {
        return uiAdapter;
    }
    
    /**
     * Отримує адаптер ассетів.
     */
    public LibGDXAssetAdapter getAssetAdapter() {
        return assetAdapter;
    }
}