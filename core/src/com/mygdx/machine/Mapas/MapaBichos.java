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
import com.mygdx.machine.Personaje.Enemigo;
import com.mygdx.machine.Personaje.Jugador;

import java.util.Random;

public class MapaBichos extends BaseScreen {
    private Jugador player;//Variable tipo Jugador
    private Enemigo enemigo;//Variable tipo Enemigo
    private SpriteBatch batch;//Variable tipo SpriteBatch

    /**
     * Constructor que carga el mapa(Todos los mapas funcionan de la misma manera asi que con la documentacion de este sirve para todos)
     * @param main
     * @param x posicion del jugador en x
     * @param y posicion del jugador en y
     */
    public MapaBichos(Main main,float x,float y) {
        super(main);
        this.bd=main.getBaseDatosJuego();
        puntuacion=new BitmapFont(Gdx.files.internal("recursos/score.ttf"));//Le asigno una fuente
        bichoMatado=bd.cargar();//Cargo la base de datos
        w = Gdx.graphics.getWidth();//Guarda la anchura de la pantalla
        h = Gdx.graphics.getHeight();//Guarda la Altura de la pantalla
        manager = new AssetManager();//Inicializo el manager
        manager.setLoader(TiledMap.class, new TmxMapLoader());//Le digo que quiero una clase tipo TileMap
        manager.load("mapas/MapaEntrenamiento.tmx", TiledMap.class);//Le asigno la ruta de mi mapa
        manager.finishLoading();//Termindo del manager(No se libera de memoria)
        map = manager.get("mapas/MapaEntrenamiento.tmx", TiledMap.class);//Le asigno el mapa cargado con manager a la variable map
        MapProperties properties = map.getProperties();//Saco las propiedades del mapa
        tileWidth = properties.get("tilewidth", Integer.class);//Saco la anchura del patron en tiles
        tileHeight = properties.get("tileheight", Integer.class);//Saco la altura del patron en tiles
        mapWidthInTiles = properties.get("width", Integer.class);//Saco la anchura del mapa en tiles
        mapHeightInTiles = properties.get("height", Integer.class);//Saco la altura del mapa en tiles
        mapWidthInPixels = mapWidthInTiles * tileWidth;//Obtengo el ancho del mapa mediante la multiplicacion del patron(tileWidth) y el mapa(mapWidthInTiles)
        mapHeightInPixels = mapHeightInTiles * tileHeight;//Obtengo el alto del mapa mediante la multiplicacion del patron(tileHeight) y el mapa(mapHeightInTiles)
        renderer = new OrthogonalTiledMapRenderer(map);//Inicializo la variable renderer y le paso al constructor el mapa que quiero
        camera = new OrthographicCamera(mapWidthInPixels, mapHeightInPixels);//Inicializo la camara y le paso por constructor el
        camera.position.x = mapWidthInPixels / 2;//Le asigno la posicion de la camara en x
        camera.position.y = mapHeightInPixels / 2;//Le asigno la posicion de la camara en y
        w = w / mapWidthInPixels;//Anchura del mapa en pixeles
        h = h / mapHeightInPixels;//Altura del mapa en pixeles
        batch = new SpriteBatch();//Inicializo el batch
        Colisiones colisiones = new Colisiones();//Inicializo la variable colisiones
        colisiones.checkCollision(map, w, h);//Uso la funcion de checkColosion de colisisones
        player = new Jugador(x * w,y*h,colisiones, main,w,h);//Inicializo a mi jugador
        enemigo = new Enemigo(100 * w,100*h,colisiones, main,w,h);//Inicializo al enemigo
        MapLayers mapLayers = map.getLayers();//Inicializamos el maplayers
        terrainLayer = (TiledMapTileLayer) mapLayers.get("suelo");//Obtenemos una de las capas del mapa
        terrainLayer2 = (TiledMapTileLayer) mapLayers.get("objetos");//Obtenemos una de las capas del mapa
        decorationLayersIndices = new int[]{
                mapLayers.getIndex("zonaAtras")//Este array sirve para ir cargando todas las demas si tener que hacer un terrainLayer nuevo por capa
        };
        stage = new Stage();//Inicializacion del stage
        stage.addActor(player);//Añadimos el actor
        stage.addActor(enemigo);//Añadimos el actor
        stage.addActor(botonesMover(player));//Añadimos el actor
        stage.setDebugAll(true);//Nos permite ver los margenes de los actores
        for (int c = 0; c < colisiones.getRect().length; c++) {
            stage.addActor(colisiones.getActores()[c]);//Utilizo el getActores de la clase colsision introducir todos los actores al stage y sacar todas las colisiones de mi mapa
        }
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new EscucharTeclado(player));
        Gdx.input.setInputProcessor(stage);
    }
    public void render ( float delta){
        super.render(delta);
        renderer.setView(camera);//Renderizamos la camara
        camera.update();//Actualizamos la camara
        if(Gdx.input.isButtonPressed(0)&&!getaBoolean()){//Detecta si un boton ha sido pulsado y con el get boolean lo dejamos que entre siempre en el if hasta que deje de pulsarlo
            player.moverJugador(getLetra());//Movemos al jugador con su funcion
        }
        if(player.getRectangle().overlaps(enemigo.getRectangle())&&quitarVida==true) {//Los mismo que el movimiento pero con el ataque solo si el jugador y el enemigo colisionas gana putos
                bichoMatado = bichoMatado + 1;
                bd.guardar(bichoMatado);//Lo guardo en la base de datos
        }
        renderer.getBatch().begin();//Inicio el batch de rendere
        renderer.renderTileLayer(terrainLayer);//Renderizo la primera capa, el orden es importante ya que de esto dependera que podamos pasar por detras de ciertas capas
        renderer.renderTileLayer(terrainLayer2);//Renderizo la primera capa, el orden es importante ya que de esto dependera que podamos pasar por detras de ciertas capas
        renderer.getBatch().end();//Finalizo el batch
        batch.begin();//Inicio el batch de rendere
        player.render(batch);//Paso el batch al jugador y uso su funcion render
        enemigo.render(batch);//Paso el batch al enemigo y uso su funcion render
        batch.end();//Finalizo el batch
        renderer.render(decorationLayersIndices);//Renderizo el array de capas
        stage.draw();//Dibujo el stage
        batch.begin();//Inicio el batch
        GlyphLayout muerteBicho=new GlyphLayout(puntuacion,"Puntuacion:"+bichoMatado);//Asigno al GlyphLayout un BitmapFont un el nombre y la cantidas
        puntuacion.draw(batch,muerteBicho,Gdx.graphics.getWidth()-600,Gdx.graphics.getHeight()-10);//La dibujo en el sitio indicado
        puntuacion.setColor(Color.BLACK);//Establezco su color
        batch.end();//Finalizo el batch
    }

}
