package com.mygdx.machine.Personaje;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.machine.Main;
import com.mygdx.machine.Mapas.Colisiones;
import com.mygdx.machine.Mapas.MapaBichos;
import com.mygdx.machine.Mapas.MapaInicial;
import com.mygdx.machine.Mapas.MapaPlantaBaja;
import com.mygdx.machine.Mapas.MapaPueblo;

public class Jugador extends Actor {
    public float x, y;//Posicion del jugador en x e y
    private Animation animation;//Variable tipo Animacion
    private float tiempo;//Variable que guarda el tiempo
    private Rectangle rectangle;//Variable tipo Rectangle
    private TextureRegion[] regions;//Array de TextureRegion
    private Texture texture;//Variable de Texture
    private TextureRegion textureRegion;//Variable de textureRegion
    private TextureRegion[][] tmp;//Matriz de textureRegion
    private Rectangle[]rectangles;//Array de Rectangles
    private Sprite sprite;//Variable tipo Srite
    private String jugadorVista;//Variable tipo String que guarda a donde mira el jugador
    private float AnchoJugador,largoJugador;//Variable tipo float que guarda las dimensiones del personaje
    private Boolean olision;//Variable tipo boolean que uso para saber si colisiona
    private int vida;//Variable que guarda la vida de mi jugador
    private int daño;//Variable que guarda el daño de mi jugador
    private Colisiones colisiones;//Variable tipo colisiones
    private Main main;//Variable tipo main
    private float wReescalado,hReescalado;//Variables tipo float en la que asigno el reescalado

    /**
     * Este constructor se encarga de crear a mi personaje
     * Esta clase y la de enemigo son las mismas por lo que esta documentacion es tambien valida para la clase Enemigo
     * @param x posicion del personaje en el mapa en x
     * @param y posicion del personaje en el mapa en y
     * @param colision variable tipo colisiones para saber si choca con algo
     * @param main paso el main para cargar otros mapas
     * @param wReescalado float de reescalado en ancho para el jugador
     * @param hReescalado float de reescalado en alto para el jugador
     */
    public Jugador(float x, float y, Colisiones colision, Main main,float wReescalado,float hReescalado) {
        this.x = x;
        this.y = y;
        this.colisiones=colision;
        this.main=main;
        this.wReescalado=wReescalado;
        this.hReescalado=hReescalado;
        rectangles=colisiones.getRect();//Obtengo el array de rectangulo en colisiones
        texture = new Texture(Gdx.files.internal("recursos/character.png"));//Asigno a texture el archivo png de mi personaje
        jugadorVista = "";//Agigno comillas a jugador vista
        rectangle=new Rectangle(x,y,texture.getWidth(),texture.getHeight());//Creo el rectangulo de mi jugador con las propiedades de texture
        tmp = TextureRegion.split(texture, texture.getWidth() / 4, texture.getHeight() / 4);//Divido mi texture region en las partes indicadas para asignarlas a la matria
        //Los valores de la division de tpm equivalen al numero de filas y numero de columnas de mi png(En mi caso tengo 4 filas que son los diferentes movimientos y 4 columnas una para cada movimiento)
        //Ejemplo:Fila 1-->Movimiento a la derecha: Columna 1-->Quieto Columna -->Mueve un pie: Columna 3 --->Muevo el otro pie: Columna 4-->Vuelvo a estar quieto
        regions = new TextureRegion[4];//Asigno al array regions la longuitud por la que haya dividido en ancho my imagen
        for (int b = 0; b < regions.length; b++) {//Con este bucle voy cargando las imagenes y da la ilusion de que anda
            regions[b] = tmp[0][0];
            animation = new Animation((float) 0.2, regions);//Inicializamos el constructor pasandole por parametros el tiempo que dura y el array
            tiempo = 0f;
        }
    }

    /**
     * La funcion de render se encarga de dibujar nuestro personaje y su colision
     * @param batch
     */
    public void render(final SpriteBatch batch) {
        tiempo += Gdx.graphics.getDeltaTime();
        textureRegion = (TextureRegion) animation.getKeyFrame(tiempo, true);
        sprite=new Sprite(textureRegion);
        AnchoJugador=sprite.getWidth()*wReescalado;
        largoJugador=sprite.getHeight()*hReescalado-10;
        setBounds(x,y,sprite.getWidth()*wReescalado,sprite.getHeight()*hReescalado-15);
        batch.draw(sprite, x, y,sprite.getWidth()*wReescalado,sprite.getHeight()*hReescalado);
    }

    /**
     * Mover jugador se encarga de hacer el movimiento, pasandole una letras y comprobara si hace colision con algun objeto en caso de no hacerla avanza
     * Todos los movimiento usan los mismos metodos por lo que la documentacion de w sirve para las demas letras
     * @param letra determina hacia donde se mueve
     */
    public void moverJugador(char letra) {
        switch (letra) {
            case 'w':
                for(int b=0;b<rectangles.length;b++){
                    if(rectangles[b].overlaps(rectangle.set(x,y+3,AnchoJugador,largoJugador))){//Comprobamos si en el array de rectangles hacer overlaps con nuestro jugador
                        olision=true;//Si lo hace olision se vuelve true y no se movera
                        break;
                    }else{
                        olision=false;//Si no lo hace olision de volvera false y se movera
                    }
                }
                if(olision==false){
                    for(int b=0;b<colisiones.getSalida().length;b++){
                        if(colisiones.getSalida()[b].overlaps(rectangle.set(x,y,AnchoJugador,largoJugador))){//Comprobamos si nuestro personaje hace overlaps con alguna salida
                            switch (colisiones.getObj2()[b].getName()){//Si colisiona este switch se encargara de ver con que salida estamos chocando mediante el nombre de los rectangulos(Esto se pone en el tiled)
                                case "salidaBajo":
                                    main.dispose();
                                    main.setPantallaActual(new MapaPlantaBaja(main,358,10));
                                    break;
                                case "salida1":
                                    main.dispose();
                                    main.setPantallaActual(new MapaInicial(main,347,298,main.getScreen().getBd()));
                                    break;
                                case "salida2":
                                    main.dispose();
                                    main.setPantallaActual(new MapaPueblo(main,80,140));
                                    break;
                                case "miCasa":
                                    main.dispose();
                                    main.setPantallaActual(new MapaPlantaBaja(main,194,10));
                                    break;
                                case "bichos":
                                    main.dispose();
                                    main.setPantallaActual(new MapaBichos(main,100,100));
                                    break;
                                case "salidaPuebloBichos":
                                    main.dispose();
                                    main.setPantallaActual(new MapaPueblo(main,196,22));
                                    break;
                            }
                        }
                    }
                    y=y+3;
                }else{

                }

                break;
            case 's':
                for(int b=0;b<rectangles.length;b++){
                    if(rectangles[b].overlaps(rectangle.set(x,y-3,AnchoJugador,largoJugador))){
                        olision=true;
                        break;
                    }else{
                        olision=false;
                    }
                }
                if(olision==false){
                    for(int b=0;b<colisiones.getSalida().length;b++){
                        if(colisiones.getSalida()[b].overlaps(rectangle.set(x,y,AnchoJugador,largoJugador))){
                            System.out.println("colisionando");
                            switch (colisiones.getObj2()[b].getName()){
                                case "salidaBajo":
                                    main.dispose();
                                    main.setPantallaActual(new MapaPlantaBaja(main,358,140));
                                    break;
                                case "salida1":
                                    main.dispose();
                                    main.setPantallaActual(new MapaInicial(main,347,298,main.getScreen().getBd()));
                                    break;
                                case "salida2":
                                    main.dispose();
                                    main.setPantallaActual(new MapaPueblo(main,80,140));
                                    break;
                                case "miCasa":
                                    main.dispose();
                                    main.setPantallaActual(new MapaPlantaBaja(main,194,10));
                                    break;
                                case "bichos":
                                    main.dispose();
                                    main.setPantallaActual(new MapaBichos(main,100,100));
                                    break;
                                case "salidaPuebloBichos":
                                    main.dispose();
                                    main.setPantallaActual(new MapaPueblo(main,196,22));
                                    break;
                            }
                        }
                    }

                    y=y-3;
                }
                break;
            case 'd':
                for(int b=0;b<rectangles.length;b++){
                    if(rectangles[b].overlaps(rectangle.set(x+3,y,AnchoJugador,largoJugador))){
                        olision=true;
                        break;
                    }else{
                        olision=false;
                    }
                }
                if(olision==false){
                    for(int b=0;b<colisiones.getSalida().length;b++){
                        if(colisiones.getSalida()[b].overlaps(rectangle.set(x,y,AnchoJugador,largoJugador))){
                            System.out.println("colisionando");
                            switch (colisiones.getObj2()[b].getName()){
                                case "salidaBajo":
                                    main.dispose();
                                    main.setPantallaActual(new MapaPlantaBaja(main,358,140));
                                    break;
                                case "salida1":
                                    main.dispose();
                                    main.setPantallaActual(new MapaInicial(main,347,298,main.getScreen().getBd()));
                                    break;
                                case "salida2":
                                    main.dispose();
                                    main.getScreen().dispose();
                                    main.setPantallaActual(new MapaPueblo(main,80,140));
                                    break;
                                case "miCasa":
                                    main.dispose();
                                    main.setPantallaActual(new MapaPlantaBaja(main,194,10));
                                    break;
                                case "bichos":
                                    main.dispose();
                                    main.setPantallaActual(new MapaBichos(main,100,100));
                                case "salidaPuebloBichos":
                                    main.dispose();
                                    main.setPantallaActual(new MapaPueblo(main,196,22));
                                    break;
                            }
                        }
                    }
                    x=x+3;
                }
                break;
            case 'a':
                for(int b=0;b<rectangles.length;b++){
                    if(rectangles[b].overlaps(rectangle.set(x-3,y,AnchoJugador,largoJugador))){
                        olision=true;
                        break;
                    }else{
                        olision=false;
                    }
                }
                if(olision==false){
                    for(int b=0;b<colisiones.getSalida().length;b++){
                        if(colisiones.getSalida()[b].overlaps(rectangle.set(x,y,AnchoJugador,largoJugador))){
                            System.out.println("colisionando"+colisiones.getObj2()[b].getName());
                            switch (colisiones.getObj2()[b].getName()){
                                case "salidaBajo":
                                    main.dispose();
                                    main.setPantallaActual(new MapaPlantaBaja(main,358,140));
                                    break;
                                case "salida1":
                                    main.dispose();
                                    main.setPantallaActual(new MapaInicial(main,347,298,main.getScreen().getBd()));
                                    break;
                                case "salida2":
                                    main.dispose();
                                    main.setPantallaActual(new MapaPueblo(main,80,140));
                                    break;
                                case "miCasa":
                                    main.dispose();
                                    main.setPantallaActual(new MapaPlantaBaja(main,194,10));
                                    break;
                                case "bichos":
                                    main.dispose();
                                    main.setPantallaActual(new MapaBichos(main,100,100));
                                case "salidaPuebloBichos":
                                    main.dispose();
                                    main.setPantallaActual(new MapaPueblo(main,196,22));
                                    break;
                            }
                        }
                    }
                    x=x-3;
                }
                break;
        }
    }

    /**
     * Esta funcion se encarga de hacer la animaciones al movernos
     * @param letra determina que animacion debe hacer
     */
    public void hacerAnimaciones(char letra) {
        switch (letra) {
            case 'd':
                for (int b = 0; b < regions.length; b++) {
                    regions[b] = tmp[1][b];
                    animation = new Animation((float) 0.2, regions);
                    tiempo = 0f;
                }
                break;
            case 's':
                for (int b = 0; b < regions.length; b++) {
                    regions[b] = tmp[0][b];
                    animation = new Animation((float) 0.2, regions);
                    tiempo = 0f;
                }
                break;
            case 'a':
                for (int b = 0; b < regions.length; b++) {
                    regions[b] = tmp[3][b];
                    animation = new Animation((float) 0.2, regions);
                    tiempo = 0f;
                }
                break;
            case 'w':
                for (int b = 0; b < regions.length; b++) {
                    regions[b] = tmp[2][b];
                    animation = new Animation((float) 0.2, regions);
                    tiempo = 0f;
                }
                break;
            case 'º':
                for (int b = 0; b < regions.length; b++) {
                    regions[b] = tmp[5][b];
                    animation = new Animation((float) 0.2, regions);
                    tiempo = 0f;
                }
                break;
        }


    }

    /**
     * Esta funcion establece a la imagen de nuestro personaje quiero dependiendo de a donde mirasemos cuando paramos de camina
     * @param letra establece que imagen poner cuando paramos
     */
    public void pararPersonaje(char letra) {
        switch (letra) {
            case 'd':
                for (int b = 0; b < regions.length; b++) {
                    regions[b] = tmp[1][0];
                    animation = new Animation((float) 0.2, regions);
                    tiempo = 0f;
                    jugadorVista = "Derecha";//Guarda en la variable jugadorVista hacia donde miramos
                }
                break;
            case 's':
                for (int b = 0; b < regions.length; b++) {
                    regions[b] = tmp[0][0];
                    animation = new Animation((float) 0.2, regions);
                    tiempo = 0f;
                    jugadorVista = "Abajo";
                }
                break;
            case 'a':
                for (int b = 0; b < regions.length; b++) {
                    regions[b] = tmp[3][0];
                    animation = new Animation((float) 0.2, regions);
                    tiempo = 0f;
                    jugadorVista = "Izquierda";
                }
                break;
            case 'w':
                for (int b = 0; b < regions.length; b++) {
                    regions[b] = tmp[2][0];
                    animation = new Animation((float) 0.2, regions);
                    tiempo = 0f;
                    jugadorVista = "Arriba";
                }
                break;
        }

    }

    /**
     * Esta funcion se encarga del ataque del pesonaje, haciendo uso de la variable jugadorVista atacara hacia la direccion que estemos mirando
     */
    public void atacar() {
        texture = new Texture(Gdx.files.internal("recursos/ataques.png"));
        rectangle=new Rectangle(x,y,texture.getWidth(),texture.getHeight());
        tmp = TextureRegion.split(texture, texture.getWidth() / 4, texture.getHeight() / 4);
        switch (jugadorVista) {
            case "Derecha":
                for (int b = 0; b < regions.length; b++) {
                    regions[b] = tmp[2][b];
                    animation = new Animation((float) 0.1, regions);
                    tiempo = 0f;
                }
                break;
            case "Izquierda":
                for (int b = 0; b < regions.length; b++) {
                    regions[b] = tmp[3][b];
                    animation = new Animation((float) 0.1, regions);
                    tiempo = 0f;
                }
                break;
            case "Abajo":
                for (int b = 0; b < regions.length; b++) {
                    regions[b] = tmp[0][b];
                    animation = new Animation((float) 0.1, regions);
                    tiempo = 0f;
                }
                break;
            case "Arriba":
                for (int b = 0; b < regions.length; b++) {
                    regions[b] = tmp[1][b];
                    animation = new Animation((float) 0.1, regions);
                    tiempo = 0f;
                }
                break;
        }
    }

    /**
     * Funcion que para al jugador cuando paramos de atacar
     */
    public void pararJugador() {
        texture = new Texture(Gdx.files.internal("recursos/character.png"));
        rectangle=new Rectangle(x,y,texture.getWidth(),texture.getHeight());
        tmp = TextureRegion.split(texture, texture.getWidth() / 4, texture.getHeight() / 4);
        switch (jugadorVista) {
            case "Derecha":
                for (int b = 0; b < regions.length; b++) {
                    regions[b] = tmp[1][0];
                    animation = new Animation((float) 0.2, regions);
                    tiempo = 0f;
                }
                break;
            case "Izquierda":
                for (int b = 0; b < regions.length; b++) {
                    regions[b] = tmp[3][0];
                    animation = new Animation((float) 0.2, regions);
                    tiempo = 0f;
                }
                break;
            case "Abajo":
                for (int b = 0; b < regions.length; b++) {
                    regions[b] = tmp[0][0];
                    animation = new Animation((float) 0.2, regions);
                    tiempo = 0f;
                }
                break;
            case "Arriba":
                for (int b = 0; b < regions.length; b++) {
                    regions[b] = tmp[2][0];
                    animation = new Animation((float) 0.2, regions);
                    tiempo = 0f;
                }
                break;
        }
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
