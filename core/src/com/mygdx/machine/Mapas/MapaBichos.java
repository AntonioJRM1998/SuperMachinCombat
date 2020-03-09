package com.mygdx.machine.Mapas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.machine.Escuchadores.EscucharTeclado;
import com.mygdx.machine.Main;
import com.mygdx.machine.Personaje.Enemigo;
import com.mygdx.machine.Personaje.Jugador;

import java.util.Random;

public class MapaBichos extends BaseScreen {
    private Jugador player;
    private Enemigo enemigo;
    private SpriteBatch batch;
    private char[]array=new char[4];
    private Random ran;
    public MapaBichos(Main main,float x,float y) {
        super(main);
        ran=new Random(3);
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        manager = new AssetManager();
        array[0]='w';
        array[1]='s';
        array[2]='d';
        array[3]='a';
        manager.setLoader(TiledMap.class, new TmxMapLoader());
        manager.load("mapas/MapaEntrenamiento.tmx", TiledMap.class);
        manager.finishLoading();
        map = manager.get("mapas/MapaEntrenamiento.tmx", TiledMap.class);
        MapProperties properties = map.getProperties();
        tileWidth = properties.get("tilewidth", Integer.class);
        tileHeight = properties.get("tileheight", Integer.class);
        mapWidthInTiles = properties.get("width", Integer.class);
        mapHeightInTiles = properties.get("height", Integer.class);
        mapWidthInPixels = mapWidthInTiles * tileWidth;
        mapHeightInPixels = mapHeightInTiles * tileHeight;
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera(mapWidthInPixels, mapHeightInPixels);
        camera.position.x = mapWidthInPixels / 2;
        camera.position.y = mapHeightInPixels / 2;
        w = w / mapWidthInPixels;
        System.out.println(w);
        h = h / mapHeightInPixels;
        System.out.println(h);
        batch = new SpriteBatch();
        Colisiones colisiones = new Colisiones();
        colisiones.checkCollision(map, w, h);
        player = new Jugador(x * w,y*h,colisiones, main,w,h);
        enemigo = new Enemigo(100 * w,100*h,colisiones, main,w,h);
        MapLayers mapLayers = map.getLayers();
        terrainLayer = (TiledMapTileLayer) mapLayers.get("suelo");
        terrainLayer2 = (TiledMapTileLayer) mapLayers.get("objetos");
        decorationLayersIndices = new int[]{
                mapLayers.getIndex("zonaAtras")
        };
        stage = new Stage();
        stage.addActor(player);
        stage.addActor(enemigo);
        stage.addActor(botonesMover(player));
        stage.setDebugAll(true);
        for (int c = 0; c < colisiones.getRect().length; c++) {
            stage.addActor(colisiones.getActores()[c]);
        }
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new EscucharTeclado(player));
        Gdx.input.setInputProcessor(stage);
    }
    public void render ( float delta){
        super.render(delta);
        renderer.setView(camera);
        camera.update();
        if(Gdx.input.isButtonPressed(0)&&!getaBoolean()){
            player.moverJugador(getLetra());
            enemigo.moverJugador(array[ran.nextInt()]);
        }
        renderer.getBatch().begin();
        renderer.renderTileLayer(terrainLayer);
        renderer.renderTileLayer(terrainLayer2);
        renderer.getBatch().end();
        batch.begin();
        player.render(batch);
        enemigo.render(batch);
        batch.end();
        renderer.render(decorationLayersIndices);
        stage.draw();
    }
}
