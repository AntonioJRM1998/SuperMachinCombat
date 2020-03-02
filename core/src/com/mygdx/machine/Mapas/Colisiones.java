package com.mygdx.machine.Mapas;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.machine.Personaje.Jugador;

public class Colisiones {
    private Actor[]actores;
    private Rectangle[]rect;
    private Rectangle jugador;
    public void checkCollision(TiledMap map, Jugador personaje) {

        jugador=new Rectangle();
        jugador.set(personaje.getX(),personaje.getY(),personaje.getAnchoJugador(),personaje.getLargoJugador());
        MapObjects mons = map.getLayers().get("colisiones").getObjects();
        actores=new Actor[mons.getCount()];
        rect=new Rectangle[mons.getCount()];
        for (int i = 0;i < mons.getCount(); i++) {
            RectangleMapObject obj1 = (RectangleMapObject) mons.get(i);
            Rectangle rect1 = obj1.getRectangle();
            rect[i]=rect1;
            rect[i].set(rect1.x*4,rect1.y*4,rect1.width*4,rect1.height*4);
            actores[i]=new Actor();
            actores[i].setBounds(rect1.x,rect1.y,rect1.width,rect1.height);
        }
    }

    public Actor[] getActores() {
        return actores;
    }

    public Rectangle[] getRect() {
        return rect;
    }
}
