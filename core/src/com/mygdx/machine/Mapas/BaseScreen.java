package com.mygdx.machine.Mapas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.machine.Main;
import com.mygdx.machine.Personaje.Jugador;
import com.mygdx.machine.basedatos.BaseDatosJuego;

public class BaseScreen implements Screen {
    protected TiledMap map;
    protected Main main;
    protected char letra;//Variable que guarda una letra que uso despues en el movimiento
    protected AssetManager manager;//Con esto cargo el mapa
    protected int tileWidth, tileHeight,//Variable de anchura y altura del mapa en tiles y en pixeles
            mapWidthInTiles, mapHeightInTiles,
            mapWidthInPixels, mapHeightInPixels;
    protected OrthographicCamera camera;//Camara del mapa
    protected OrthogonalTiledMapRenderer renderer;
    protected TiledMapTileLayer terrainLayer;//Carga capas del mapa
    protected TiledMapTileLayer terrainLayer2;//Carga capas del mapa
    protected int[] decorationLayersIndices;//Array que carga tambien capas
    protected float w,h;//Lo uso para guardar el reescalado que consiguo dividiendo el tamaño de la pantala entre el tamaño del mapa
    protected Stage stage;//Cargo mis actores
    protected boolean aBoolean;
    protected BitmapFont puntuacion;
    protected int bichoMatado;
    protected int unaVez;
    protected BaseDatosJuego bd;
    protected Music musicaMapa1;//Cargo la musica de mi mapa
    protected boolean quitarVida;

    /**
     *
     * @param main paso un main a todas mis clases heredadas de esta
     */
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

    /**
     *
     * @param player le paso un jugador para hacer sus movimientos
     * @return
     * Esta funcion se encarga de guardar el movimiento que queremos hacer al pulsar uno de los botones
     */
    public Actor botonesMover(final Jugador player){
        TextureAtlas buttonAtlas = new TextureAtlas("recursos/buttons.pack");
        Skin buttonSkin=new Skin();
        buttonSkin.addRegions(buttonAtlas);
        ImageButton.ImageButtonStyle miraArriba=new ImageButton.ImageButtonStyle();
        ImageButton.ImageButtonStyle miraAbajo=new ImageButton.ImageButtonStyle();
        ImageButton.ImageButtonStyle miraIzquierda=new ImageButton.ImageButtonStyle();
        ImageButton.ImageButtonStyle miraDerecha=new ImageButton.ImageButtonStyle();
        ImageButton.ImageButtonStyle Atacar=new ImageButton.ImageButtonStyle();
        miraArriba.up=buttonSkin.getDrawable("upRemastered");
        miraAbajo.up=buttonSkin.getDrawable("downRemastered");
        miraDerecha.up=buttonSkin.getDrawable("rightRemastered");
        miraIzquierda.up=buttonSkin.getDrawable("leftRemastered");
        Atacar.up=buttonSkin.getDrawable("leftRemastered");
        ImageButton botonArriba = new ImageButton(miraArriba);
        ImageButton botonAbajo = new ImageButton(miraAbajo);
        ImageButton botonIzquierda = new ImageButton(miraIzquierda);
        ImageButton botonDerecha = new ImageButton(miraDerecha);
        ImageButton botonAtacar=new ImageButton(Atacar);
        botonArriba.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    setaBoolean(false);
                    letra = hacerMovimiento('w');
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
        botonAtacar.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                quitarVida=true;
                player.atacar();
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                quitarVida=false;
                player.pararJugador();
            }
        });


        Table tableBotones = new Table();
        tableBotones.setPosition(0,0);
        tableBotones.bottom();
        tableBotones.debug();
        tableBotones.setFillParent(true);
        tableBotones.add(botonArriba).height(Gdx.graphics.getHeight() / 7f).width(Gdx.graphics.getWidth() / 15f);
        tableBotones.add(botonAbajo).height(Gdx.graphics.getHeight() / 7f).width(Gdx.graphics.getWidth() / 15f);
        tableBotones.add(botonIzquierda).height(Gdx.graphics.getHeight() / 7f).width(Gdx.graphics.getWidth() / 15f);
        tableBotones.add(botonDerecha).height(Gdx.graphics.getHeight() / 7f).width(Gdx.graphics.getWidth() / 15f);
        tableBotones.add(botonAtacar).height(Gdx.graphics.getHeight() / 7f).width(Gdx.graphics.getWidth() / 15f);
        return tableBotones;
    }

    /**
     *
     * @param letra define el movimiento que vamos a hacer
     * @return
     * Guarda en la variable letra la letra que se le pase la uso en la funcion botonesMover
     */
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

    public BaseDatosJuego getBd() {
        return bd;
    }
}
