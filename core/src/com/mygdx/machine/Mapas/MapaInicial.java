package com.mygdx.machine.Mapas;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MapaInicial {
    private TiledMap map;
    private AssetManager manager;
    private int tileWidth, tileHeight,
            mapWidthInTiles, mapHeightInTiles,
            mapWidthInPixels, mapHeightInPixels;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer renderer;
    private TiledMapTileLayer terrainLayer;
    private TiledMapTileLayer terrainLayer2;
    private int[] decorationLayersIndices;

    public MapaInicial() {
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
        camera = new OrthographicCamera(mapWidthInPixels, mapHeightInPixels);
        camera.position.x = mapWidthInPixels/2;
        camera.position.y = mapHeightInPixels/2;
        renderer = new OrthogonalTiledMapRenderer(map);
        MapLayers mapLayers = map.getLayers();
        terrainLayer = (TiledMapTileLayer) mapLayers.get("suelo");
        terrainLayer2 = (TiledMapTileLayer) mapLayers.get("muebles");
        decorationLayersIndices = new int[]{
                mapLayers.getIndex("zonaAtras")
        };
    }
    public void renderSuelos() {
        renderer.setView(camera);
        renderer.getBatch().begin();
        renderer.renderTileLayer(terrainLayer);
        renderer.renderTileLayer(terrainLayer2);
        renderer.getBatch().end();
    }
    public void renderizarObjetos() {
        renderer.render(decorationLayersIndices);
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
    }

    public TiledMap getMap() {
        return map;
    }
}
