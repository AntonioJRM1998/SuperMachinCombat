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
import com.mygdx.machine.Mapas.MapaInicial;
import com.mygdx.machine.Mapas.MapaPlantaBaja;

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
    public Jugador(float x, float y, float AnchoJugador, float lagoJugador, Colisiones colision, Main main) {
        this.x = x;
        this.y = y;
        this.colisiones=colision;
        this.main=main;
        this.setSize(Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()/10);
        this.AnchoJugador=AnchoJugador;
        this.largoJugador=lagoJugador;
        rectangles=colisiones.getRect();
        texture = new Texture(Gdx.files.internal("recursos/character.png"));
        jugadorVista = "";
        rectangle=new Rectangle(x,y,texture.getWidth(),texture.getHeight());
        tmp = TextureRegion.split(texture, texture.getWidth() / 17, texture.getHeight() / 8);
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
        setBounds(x,y,AnchoJugador-1,largoJugador-10);
        batch.draw(sprite, x, y,AnchoJugador,largoJugador);
    }

    public void moverJugador(char letra) {
        switch (letra) {
            case 'w':
                for(int b=0;b<rectangles.length-1;b++){
                    if(rectangles[b].overlaps(rectangle.set(x,y+9,AnchoJugador,largoJugador))){
                        olision=true;
                        break;
                    }else{
                        olision=false;
                    }
                }
                if(olision==false){
                    for(int b=0;b<colisiones.getSalida().length;b++){
                        if(colisiones.getSalida()[b].overlaps(rectangle.set(x,y+9,AnchoJugador,largoJugador))){
                            switch (colisiones.getObj2()[b].getName()){
                                case "salidaBajo":
                                    main.dispose();
                                    main.setPantallaActual(new MapaPlantaBaja(main,100,100));
                                    break;
                                case "salida1":
                                    main.dispose();
                                    main.setPantallaActual(new MapaInicial(main,347,298));
                                    break;
                            }
                        }
                    }
                    y=y+9;
                }else{

                }

                break;
            case 's':
                for(int b=0;b<rectangles.length-1;b++){
                    if(rectangles[b].overlaps(rectangle.set(x,y-9,AnchoJugador,largoJugador))){
                        olision=true;
                        break;
                    }else{
                        olision=false;
                    }
                }
                if(olision==false){

                    y=y-9;
                }
                break;
            case 'd':
                for(int b=0;b<rectangles.length-1;b++){
                    if(rectangles[b].overlaps(rectangle.set(x+9,y,AnchoJugador,largoJugador))){
                        olision=true;
                        break;
                    }else{
                        olision=false;
                    }
                }
                if(olision==false){
                    x=x+9;
                }
                break;
            case 'a':
                for(int b=0;b<rectangles.length-1;b++){
                    if(rectangles[b].overlaps(rectangle.set(x-9,y,AnchoJugador,largoJugador))){
                        olision=true;
                        break;
                    }else{
                        olision=false;
                    }
                }
                if(olision==false){
                    x=x-9;
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
        tmp = TextureRegion.split(texture, texture.getWidth() / 4, texture.getHeight() / 4);
        regions = new TextureRegion[4];
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
        tmp = TextureRegion.split(texture, texture.getWidth() / 17, texture.getHeight() / 8);
        regions = new TextureRegion[4];
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
