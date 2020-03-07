package com.mygdx.machine.Mapas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.machine.Escuchadores.EscucharTeclado;
import com.mygdx.machine.Main;
import com.mygdx.machine.Personaje.Jugador;

public class MapaInicial extends BaseScreen {
        private Main main;
        private Jugador player;
        private SpriteBatch batch;
        private int button1=0;
        private char letra;
        private EscucharTeclado escucharTeclado;
    public MapaInicial(Main main,float x,float y) {
        super(main);
        this.main=main;
        w= Gdx.graphics.getWidth();
        h=Gdx.graphics.getHeight();
        manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader());
        manager.load("mapas/MapaInicio.tmx", TiledMap.class);
        manager.finishLoading();
        map = manager.get("mapas/MapaInicio.tmx", TiledMap.class);
        MapProperties properties = map.getProperties();
        tileWidth = properties.get("tilewidth", Integer.class);
        tileHeight = properties.get("tileheight", Integer.class);
        mapWidthInTiles = properties.get("width", Integer.class);
        mapHeightInTiles = properties.get("height", Integer.class);
        mapWidthInPixels = mapWidthInTiles * tileWidth;
        mapHeightInPixels = mapHeightInTiles * tileHeight;
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera(mapWidthInPixels,mapHeightInPixels);
        camera.position.x = mapWidthInPixels/2;
        camera.position.y = mapHeightInPixels/2;
        w=w/mapWidthInPixels;
        System.out.println(w);
        h=h/mapHeightInPixels;
        System.out.println(h);
        batch=new SpriteBatch();
        Colisiones colisiones=new Colisiones();
        colisiones.checkCollision(map,w,h);
        player=new Jugador(x*w,y/h,colisiones,main,w,h);
        escucharTeclado=new EscucharTeclado(player);
        MapLayers mapLayers = map.getLayers();
        terrainLayer = (TiledMapTileLayer) mapLayers.get("suelo");
        terrainLayer2 = (TiledMapTileLayer) mapLayers.get("muebles");
        decorationLayersIndices = new int[]{
                mapLayers.getIndex("zonaAtras")
        };
        stage=new Stage();
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
        final ImageButton botonArriba = new ImageButton(miraArriba);
        final ImageButton botonAbajo = new ImageButton(miraAbajo);
        ImageButton botonIzquierda = new ImageButton(miraIzquierda);
        ImageButton botonDerecha = new ImageButton(miraDerecha);
        botonArriba.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                hacerMovimiento('w');
                player.hacerAnimaciones('w');
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                player.pararPersonaje('w');
            }
        });
        botonAbajo.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                hacerMovimiento('s');
                player.hacerAnimaciones('s');
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                player.pararPersonaje('s');
            }
        });
        botonDerecha.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                hacerMovimiento('d');
                player.hacerAnimaciones('d');
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                player.pararPersonaje('d');
            }
        });
        botonIzquierda.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                hacerMovimiento('a');
                player.hacerAnimaciones('a');
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
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
        stage.addActor(player);
        stage.addActor(tableBotones);
        stage.setDebugAll(true);
        for(int c=0;c<colisiones.getRect().length;c++){
            stage.addActor(colisiones.getActores()[c]);
        }
        InputMultiplexer multiplexer=new InputMultiplexer();
        multiplexer.addProcessor(new EscucharTeclado(player));
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(stage);
        //Gdx.input.setInputProcessor(multiplexer);
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        renderer.setView(camera);
        camera.update();
        if(Gdx.input.isButtonPressed(0)){
            player.moverJugador(letra);
        }
        renderer.getBatch().begin();
        renderer.renderTileLayer(terrainLayer);
        renderer.renderTileLayer(terrainLayer2);
        renderer.getBatch().end();
        batch.begin();
        player.render(batch);
        batch.end();
        renderer.render(decorationLayersIndices);
        stage.draw();
    }


    public OrthographicCamera getCamera() {
        return camera;
    }

    public int getMapWidthInPixels() {
        return mapWidthInPixels;
    }

    public int getMapHeightInPixels() {
        return mapHeightInPixels;
    }
    public void hacerMovimiento(char letra){
        switch (letra){
            case 'w':
                this.letra='w';
                break;
            case 'd':
                this.letra='d';
            break;
            case 's':
                this.letra='s';
            break;
            case 'a':
                this.letra='a';
            break;
        }
    }
    public void dispose() {
        manager.dispose();
        stage.dispose();
        renderer.dispose();
    }

    public TiledMap getMap() {
        return map;
    }

    public float getW() {
        return w;
    }

    public float getH() {
        return h;
    }

    public Rectangle getRect1() {
        return rect1;
    }
}
