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
        MapLayers mapLayers = map.getLayers();
        terrainLayer = (TiledMapTileLayer) mapLayers.get("suelo");
        terrainLayer2 = (TiledMapTileLayer) mapLayers.get("muebles");
        decorationLayersIndices = new int[]{
                mapLayers.getIndex("zonaAtras")
        };
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
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){

                player.moverJugador('w');
                player.hacerAnimaciones('w');


                System.out.println(player.getX());
                return true;
            }
        });
        botonAbajo.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){

                player.moverJugador('s');
                player.hacerAnimaciones('s');
                System.out.println(player.getX());
                return true;
            }
        });
        botonDerecha.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){

                player.moverJugador('d');
                player.hacerAnimaciones('d');
                System.out.println(player.getX());
                return true;
            }
        });
        botonIzquierda.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){

                player.moverJugador('a');
                player.hacerAnimaciones('a');
                System.out.println(player.getX());

                return true;
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
        stage=new Stage();
        stage.addActor(player);
        stage.addActor(tableBotones);
        stage.setDebugAll(true);
        for(int c=0;c<colisiones.getRect().length;c++){
            stage.addActor(colisiones.getActores()[c]);
        }
        InputMultiplexer multiplexer=new InputMultiplexer();
        multiplexer.addProcessor(new EscucharTeclado(player));
        Gdx.input.setInputProcessor(multiplexer);
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        renderer.setView(camera);
        camera.update();
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
