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

import java.util.Random;

public class Enemigo extends Actor {
    public float x, y;
    private Animation animation;
    private float tiempo;
    private Rectangle rectangle;
    private TextureRegion[] regions;
    private Texture texture;
    private TextureRegion textureRegion;
    private TextureRegion[][] tmp;
    private Rectangle[] rectangles;
    private Sprite sprite;
    private String jugadorVista;
    private float AnchoJugador, largoJugador;
    private Boolean olision;
    private int vida;
    private int daño;
    private Colisiones colisiones;
    private Main main;
    private char[]array=new char[4];
    private float wReescalado, hReescalado;
    private Random ran;

    public Enemigo(float x, float y, Colisiones colision, Main main, float wReescalado, float hReescalado) {
        this.x = x;
        this.y = y;
        this.colisiones = colision;
        this.main = main;
        this.wReescalado = wReescalado;
        this.hReescalado = hReescalado;
        this.setSize(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);
        ran=new Random(3);
        array[0]='w';
        array[1]='s';
        array[2]='d';
        array[3]='a';
        rectangles = colisiones.getRect();
        texture = new Texture(Gdx.files.internal("recursos/character.png"));
        jugadorVista = "";
        rectangle = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
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
        sprite = new Sprite(textureRegion);
        AnchoJugador = sprite.getWidth() * wReescalado;
        largoJugador = sprite.getHeight() * hReescalado - 10;
        setBounds(x, y, sprite.getWidth() * wReescalado, sprite.getHeight() * hReescalado - 15);
        batch.draw(sprite, x, y, sprite.getWidth() * wReescalado, sprite.getHeight() * hReescalado);
    }

    public void moverJugador(char letra) {
        switch (letra) {
            case 'w':
                for (int b = 0; b < rectangles.length; b++) {
                    if (rectangles[b].overlaps(rectangle.set(x, y + 3, AnchoJugador, largoJugador))) {
                        olision = true;
                        break;
                    } else {
                        olision = false;
                    }
                }
                if (olision == false) {
                    y = y + 3;
                } else {

                }

                break;
            case 's':
                for (int b = 0; b < rectangles.length; b++) {
                    if (rectangles[b].overlaps(rectangle.set(x, y - 3, AnchoJugador, largoJugador))) {
                        olision = true;
                        break;
                    } else {
                        olision = false;
                    }
                }
                if (olision == false) {
                    y = y - 3;
                }
                break;
            case 'd':
                for (int b = 0; b < rectangles.length; b++) {
                    if (rectangles[b].overlaps(rectangle.set(x + 3, y, AnchoJugador, largoJugador))) {
                        olision = true;
                        break;
                    } else {
                        olision = false;
                    }
                }
                if (olision == false) {
                    x = x + 3;
                }
                break;
            case 'a':
                for (int b = 0; b < rectangles.length; b++) {
                    if (rectangles[b].overlaps(rectangle.set(x - 3, y, AnchoJugador, largoJugador))) {
                        olision = true;
                        break;
                    } else {
                        olision = false;
                    }
                }
                if (olision == false) {
                    x = x - 3;
                }
                break;
        }
    }
}
