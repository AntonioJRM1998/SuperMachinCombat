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
    protected TiledMap map;//Variable que uso para el mapa
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
    protected boolean aBoolean;//Variable que uso para conseguir que el personaje se mueva cuando mantenga pulsado el boton
    protected BitmapFont puntuacion;//Variable tipo bitmap
    protected int bichoMatado;//Variable que uso para guardar la puntuacion
    protected BaseDatosJuego bd;//Variable tipo BaseDatosJuego
    protected Music musicaMapa1;//Variable de la musica de mi mapa
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
        TextureAtlas buttonAtlas = new TextureAtlas("recursos/buttons.pack");//
        Skin buttonSkin=new Skin();//Un subtipo de Skinnable que representa la Skin. Esto permite que la implementación de Skin acceda a la implementación de Skin, que generalmente es una Controlimplementación.
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
                    setaBoolean(false);//False=movimiento Continuio mientras pulsas
                    letra = hacerMovimiento('w');//LLamo a la funcion de hacer movimiento y guardo la letra correspondiente a esa accion
                    player.hacerAnimaciones('w');//Se encarga de hacer la animacion
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                setaBoolean(true);//Truer=para movimiento Continuio cuando dejas de pulsar
                player.pararPersonaje('w');//Se encarga de hacer la animacion de pararse hacia donde mira
            }
        });
        botonAbajo.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                setaBoolean(false);//False=movimiento Continuio mientras pulsas
                letra=hacerMovimiento('s');//LLamo a la funcion de hacer movimiento y guardo la letra correspondiente a esa accion
                player.hacerAnimaciones('s');//Se encarga de hacer la animacion
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                setaBoolean(true);//Truer=para movimiento Continuio cuando dejas de pulsar
                player.pararPersonaje('s');//Se encarga de hacer la animacion de pararse hacia donde mira
            }
        });
        botonDerecha.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                setaBoolean(false);//False=movimiento Continuio mientras pulsas
                letra=hacerMovimiento('d');//LLamo a la funcion de hacer movimiento y guardo la letra correspondiente a esa accion
                player.hacerAnimaciones('d');//Se encarga de hacer la animacion
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                setaBoolean(true);//Truer=para movimiento Continuio cuando dejas de pulsar
                player.pararPersonaje('d');//Se encarga de hacer la animacion de pararse hacia donde mira
            }
        });
        botonIzquierda.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                setaBoolean(false);//False=movimiento Continuio mientras pulsas
                letra=hacerMovimiento('a');//LLamo a la funcion de hacer movimiento y guardo la letra correspondiente a esa accion
                player.hacerAnimaciones('a');//Se encarga de hacer la animacion
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                setaBoolean(true);//Truer=para movimiento Continuio cuando dejas de pulsar
                player.pararPersonaje('a');//Se encarga de hacer la animacion de pararse hacia donde mira
            }
        });
        botonAtacar.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                quitarVida=true;//Uso este boolean para que me cuente puntos solo cuando le doy a este boton
                player.atacar();//Hace la animacion de atacar
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                quitarVida=false;//Uso este boolean para que deje de contar puntos cuando dejo de pulsarlo
                player.pararJugador();//Paro el jugador en la posicion en la que mira
            }
        });


        Table tableBotones = new Table();//Crea una tabla para los botones
        tableBotones.bottom();//Le asigno su posicion
        tableBotones.debug();//Sirve para ver los margenes
        tableBotones.setFillParent(true);
        tableBotones.add(botonArriba).height(Gdx.graphics.getHeight() / 7f).width(Gdx.graphics.getWidth() / 15f);//Añado el boton a la tabla y le asigno una anchura y altura
        tableBotones.add(botonAbajo).height(Gdx.graphics.getHeight() / 7f).width(Gdx.graphics.getWidth() / 15f);//Añado el boton a la tabla y le asigno una anchura y altura
        tableBotones.add(botonIzquierda).height(Gdx.graphics.getHeight() / 7f).width(Gdx.graphics.getWidth() / 15f);//Añado el boton a la tabla y le asigno una anchura y altura
        tableBotones.add(botonDerecha).height(Gdx.graphics.getHeight() / 7f).width(Gdx.graphics.getWidth() / 15f);//Añado el boton a la tabla y le asigno una anchura y altura
        tableBotones.add(botonAtacar).height(Gdx.graphics.getHeight() / 7f).width(Gdx.graphics.getWidth() / 15f);//Añado el boton a la tabla y le asigno una anchura y altura
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
