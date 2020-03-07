package com.mygdx.machine.Mapas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.machine.Main;
import com.mygdx.machine.Personaje.Jugador;

public class BaseScreen implements Screen {
    protected TiledMap map;
    protected Main main;
    private char letra;
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
    private boolean aBoolean;

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
    public Actor botonesMover(final Jugador player){
        TextureAtlas buttonAtlas = new TextureAtlas("recursos/buttons.pack");
        Skin buttonSkin=new Skin();
        buttonSkin.addRegions(buttonAtlas);
        ImageButton.ImageButtonStyle miraArriba=new ImageButton.ImageButtonStyle();
        ImageButton.ImageButtonStyle miraAbajo=new ImageButton.ImageButtonStyle();
        ImageButton.ImageButtonStyle miraIzquierda=new ImageButton.ImageButtonStyle();
        ImageButton.ImageButtonStyle miraDerecha=new ImageButton.ImageButtonStyle();
        miraArriba.up=buttonSkin.getDrawable("upRemastered");
        miraAbajo.up=buttonSkin.getDrawable("downRemastered");
        miraDerecha.up=buttonSkin.getDrawable("rightRemastered");
        miraIzquierda.up=buttonSkin.getDrawable("leftRemastered");
        ImageButton botonArriba = new ImageButton(miraArriba);
        ImageButton botonAbajo = new ImageButton(miraAbajo);
        ImageButton botonIzquierda = new ImageButton(miraIzquierda);
        ImageButton botonDerecha = new ImageButton(miraDerecha);
        botonArriba.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                setaBoolean(false);
                letra=hacerMovimiento('w');
                player.hacerAnimaciones('w');
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                setaBoolean(true);
                player.pararPersonaje('w');
            }
        });
        botonAbajo.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                setaBoolean(false);
                letra=hacerMovimiento('s');
                player.hacerAnimaciones('s');
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                setaBoolean(true);
                super.touchUp(event, x, y, pointer, button);
                player.pararPersonaje('s');
            }
        });
        botonDerecha.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                setaBoolean(false);
                letra=hacerMovimiento('d');
                player.hacerAnimaciones('d');
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                setaBoolean(true);
                super.touchUp(event, x, y, pointer, button);
                player.pararPersonaje('d');
            }
        });
        botonIzquierda.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                setaBoolean(false);
                letra=hacerMovimiento('a');
                player.hacerAnimaciones('a');
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                setaBoolean(true);
                super.touchUp(event, x, y, pointer, button);
                player.pararPersonaje('a');
            }
        });

        Table tableBotones = new Table();
        tableBotones.bottom();
        tableBotones.debug();
        tableBotones.setFillParent(true);
        tableBotones.add(botonArriba).height(Gdx.graphics.getHeight() / 6f).width(Gdx.graphics.getWidth() / 18.9666f);
        tableBotones.add(botonAbajo).height(Gdx.graphics.getHeight() / 6.4f).width(Gdx.graphics.getWidth() / 18.9666f);
        tableBotones.add(botonIzquierda).height(Gdx.graphics.getHeight() / 6.4f).width(Gdx.graphics.getWidth() / 18.9666f);
        tableBotones.add(botonDerecha).height(Gdx.graphics.getHeight() / 6.4f).width(Gdx.graphics.getWidth() / 18.9666f);
        return tableBotones;
    }
    public char hacerMovimiento(char letra){
        switch (letra){
            case 'w':
                letra='w';
                break;
            case 'd':
                letra='d';
                break;
            case 's':
                letra='s';
                break;
            case 'a':
                letra='a';
                break;
        }
        return letra;
    }
    public void setaBoolean(Boolean movimiento){
        this.aBoolean=movimiento;
    }
    public Boolean getaBoolean(){
        return aBoolean;
    }

    public char getLetra() {
        return letra;
    }
}
