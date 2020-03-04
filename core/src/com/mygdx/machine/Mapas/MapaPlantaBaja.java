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
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.machine.Escuchadores.EscucharTeclado;
import com.mygdx.machine.Main;
import com.mygdx.machine.Personaje.Jugador;

public class MapaPlantaBaja extends BaseScreen {
    private Main main;
    private Jugador player;
    private SpriteBatch batch;
    public MapaPlantaBaja(Main main){
        super(main);
        this.main=main;
        w= Gdx.graphics.getWidth();
        h=Gdx.graphics.getHeight();
        manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader());
        manager.load("mapas/MapaPlantaBaja.tmx", TiledMap.class);
        manager.finishLoading();
        map = manager.get("mapas/MapaPlantaBaja.tmx", TiledMap.class);
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
        h=h/mapHeightInPixels;
        batch=new SpriteBatch();
        Colisiones colisiones=new Colisiones();
        colisiones.checkCollision(map,w,h);
        player=new Jugador(100,100,50,50,colisiones,main);
        MapLayers mapLayers = map.getLayers();
        terrainLayer = (TiledMapTileLayer) mapLayers.get("suelo");
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
        renderer.getBatch().end();
        batch.begin();
        player.render(batch);
        batch.end();
    }

}
