package com.mygdx.machine.Mapas;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.machine.Main;

public class BaseScreen implements Screen {
    protected TiledMap map;
    protected Main main;
    protected AssetManager manager;
    protected int tileWidth, tileHeight,
            mapWidthInTiles, mapHeightInTiles,
            mapWidthInPixels, mapHeightInPixels;
    protected OrthographicCamera camera;
    protected OrthogonalTiledMapRenderer renderer;
    protected TiledMapTileLayer terrainLayer;
    protected TiledMapTileLayer terrainLayer2;
    protected Rectangle rect1;
    protected int[] decorationLayersIndices;
    protected float w,h;
    protected Stage stage;

    public BaseScreen(Main main){
        this.main=main;

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        manager.dispose();
        renderer.dispose();
        stage.dispose();
    }
}
