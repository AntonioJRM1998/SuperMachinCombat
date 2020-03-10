package com.mygdx.machine.Mapas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
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
import com.mygdx.machine.Personaje.Jugador;

public class MapaPlantaBaja extends BaseScreen {
    private Jugador player;
    private SpriteBatch batch;
    private Boolean pulsarSolo=true;
    public MapaPlantaBaja(Main main,float x,float y){
        super(main);
        this.bd=main.getBaseDatosJuego();
        puntuacion=new BitmapFont(Gdx.files.internal("recursos/score.ttf"));
        bichoMatado=bd.cargar();
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
        player=new Jugador(x*w,y*h,colisiones,main,w,h);
        MapLayers mapLayers = map.getLayers();
        terrainLayer = (TiledMapTileLayer) mapLayers.get("suelo");
        terrainLayer2=(TiledMapTileLayer)mapLayers.get("objetos");
        decorationLayersIndices = new int[]{
                mapLayers.getIndex("objetosAtras")
        };
        stage=new Stage();
        stage.addActor(player);
        stage.addActor(botonesMover(player));
        stage.setDebugAll(true);
        for(int c=0;c<colisiones.getRect().length;c++){
            stage.addActor(colisiones.getActores()[c]);
        }
        InputMultiplexer multiplexer=new InputMultiplexer();
        multiplexer.addProcessor(new EscucharTeclado(player));
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        renderer.setView(camera);
        camera.update();
        if(Gdx.input.isButtonPressed(0)&&!getaBoolean()){
            player.moverJugador(getLetra());
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
        batch.begin();
        GlyphLayout muerteBicho=new GlyphLayout(puntuacion,"Puntuacion:"+bichoMatado);
        puntuacion.draw(batch,muerteBicho,Gdx.graphics.getWidth()-600,Gdx.graphics.getHeight()-10);
        puntuacion.setColor(Color.BLACK);
        batch.end();

    }
    public void dispose() {
        manager.dispose();
        stage.dispose();
        renderer.dispose();
    }

}
