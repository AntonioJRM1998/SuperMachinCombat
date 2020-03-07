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
    public float x, y;
    private Animation animation;
    private float tiempo;
    private Rectangle rectangle;
    private TextureRegion[] regions;
    private Texture texture;
    private TextureRegion textureRegion;
    private TextureRegion[][] tmp;
    private Rectangle[]rectangles;
    private Sprite sprite;
    private String jugadorVista;
    private float AnchoJugador,largoJugador;
    private Boolean olision;
    private int vida;
    private int daño;
    private Colisiones colisiones;
    private Main main;
    private float wReescalado,hReescalado;
    public Jugador(float x, float y, Colisiones colision, Main main,float wReescalado,float hReescalado) {
        this.x = x;
        this.y = y;
        this.colisiones=colision;
        this.main=main;
        this.wReescalado=wReescalado;
        this.hReescalado=hReescalado;
        this.setSize(Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()/10);
        rectangles=colisiones.getRect();
        texture = new Texture(Gdx.files.internal("recursos/character.png"));
        jugadorVista = "";
        rectangle=new Rectangle(x,y,texture.getWidth(),texture.getHeight());
        tmp = TextureRegion.split(texture, texture.getWidth() / 4, texture.getHeight() / 4);
        regions = new TextureRegion[4];
        for (int b = 0; b < regions.length; b++) {
            regions[b] = tmp[0][0];
            animation = new Animation((float) 0.2, regions);
            tiempo = 0f;
        }
    }

    public void render(final SpriteBatch batch) {
        tiempo += Gdx.graphics.getDeltaTime();
        textureRegion = (TextureRegion) animation.getKeyFrame(tiempo, true);
        sprite=new Sprite(textureRegion);
        AnchoJugador=sprite.getWidth()*wReescalado;
        largoJugador=sprite.getHeight()*hReescalado-10;
        setBounds(x,y,sprite.getWidth()*wReescalado,sprite.getHeight()*hReescalado-15);
        batch.draw(sprite, x, y,sprite.getWidth()*wReescalado,sprite.getHeight()*hReescalado);
    }

    public void moverJugador(char letra) {
        switch (letra) {
            case 'w':
                for(int b=0;b<rectangles.length;b++){
                    if(rectangles[b].overlaps(rectangle.set(x,y+2,AnchoJugador,largoJugador))){
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
                                    main.setPantallaActual(new MapaPlantaBaja(main,358,10));
                                    break;
                                case "salida1":
                                    main.dispose();
                                    main.setPantallaActual(new MapaInicial(main,347,298));
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
                            }
                        }
                    }
                    y=y+2;
                }else{

                }

                break;
            case 's':
                for(int b=0;b<rectangles.length;b++){
                    if(rectangles[b].overlaps(rectangle.set(x,y-2,AnchoJugador,largoJugador))){
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
                                    main.setPantallaActual(new MapaInicial(main,347,298));
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
                            }
                        }
                    }

                    y=y-2;
                }
                break;
            case 'd':
                for(int b=0;b<rectangles.length;b++){
                    if(rectangles[b].overlaps(rectangle.set(x+2,y,AnchoJugador,largoJugador))){
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
                                    main.setPantallaActual(new MapaInicial(main,347,298));
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
                            }
                        }
                    }
                    x=x+2;
                }
                break;
            case 'a':
                for(int b=0;b<rectangles.length;b++){
                    if(rectangles[b].overlaps(rectangle.set(x-2,y,AnchoJugador,largoJugador))){
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
                                    main.setPantallaActual(new MapaInicial(main,347,298));
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
                            }
                        }
                    }
                    x=x-2;
                }
                break;
        }
    }
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

    public void pararPersonaje(char letra) {
        switch (letra) {
            case 'd':
                for (int b = 0; b < regions.length; b++) {
                    regions[b] = tmp[1][0];
                    animation = new Animation((float) 0.2, regions);
                    tiempo = 0f;
                    jugadorVista = "Derecha";
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

    public float getAnchoJugador() {
        return AnchoJugador;
    }

    public float getLargoJugador() {
        return largoJugador;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

}
