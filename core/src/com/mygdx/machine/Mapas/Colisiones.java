package com.mygdx.machine.Mapas;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class Colisiones {
    private Actor[] actores;//Variable que me guarda un array de actores
    private Rectangle[] rect;//Variable que me guarda un array de rectangulos de las colisiones
    private Rectangle[] salida;//Variable que me guarda un array de rectangulo para las salidas dle mapa
    private RectangleMapObject[] obj2;//Variable que me guarda un array de actores

    /**
     *
     * @param map Paso un mapa por parametros para conseguir las capas
     * @param w Float que nos sirve para el reescalado del mapa en este caso la anchura(Ancho de la pantalla/Ancho del mapa)
     * @param h Float que nos sirve para el reescalado del mapa en este caso la Altura(Altura de la pantalla/Altura del mapa)
     */
    public void checkCollision(TiledMap map, float w, float h) {
        MapObjects mons = map.getLayers().get("colisiones").getObjects();//Saco la capa de objetos con el nombre de colisiones
        actores = new Actor[mons.getCount()];//Inicializo el array de actor con la longitud de elementos de la capa colisiones
        rect = new Rectangle[mons.getCount()]; //Inicializo el array de rectangulos con la longitud de elementos de la capa colisiones
        for (int i = 0; i < mons.getCount(); i++) {
            RectangleMapObject obj1 = (RectangleMapObject) mons.get(i);//Saco la primera colsion
            Rectangle rect1 = obj1.getRectangle();//La convierto en un rectangulo
            rect[i] = rect1;//La introduzco en el array
            rect[i].set(rect1.x * w, rect1.y * h, rect1.width * w, rect1.height * h);//Le asigno los valores del rectangulo y las reescalo
            actores[i] = new Actor();//Le inicializo un actor
            actores[i].setBounds(rect1.x, rect1.y, rect1.width, rect1.height);//Le paso los datos del rectangulo para convertirlo en actor
        }
        MapObjects mons1 = map.getLayers().get("salidas").getObjects();//Saco los objetos de mi capa salida
        obj2=new RectangleMapObject[mons1.getCount()];//Inicializo el array de RectangleObjet con la longitud de elementos de la capa colisiones
        salida=new Rectangle[mons1.getCount()];//Inicializo el array de rectangulos con la longitud de elementos de la capa colisiones
        for (int i = 0; i < mons1.getCount(); i++) {
            RectangleMapObject obj1 = (RectangleMapObject) mons1.get(i);//Saco la primera salida
            obj2[i]=obj1;//La añado al array de RectangleMapObject
            Rectangle rect1 = obj1.getRectangle();//La convierto en un rectangulo
            salida[i] = rect1;//La introduzco en el array
            salida[i].set(rect1.x * w, rect1.y * h, rect1.width * w, rect1.height * h);//Le asigno los valores del rectangulo y las reescalo
        }
    }

    public Actor[] getActores() {
        return actores;
    }

    public Rectangle[] getRect() {
        return rect;
    }
    public RectangleMapObject[] getObj2() {
        return obj2;
    }

    public Rectangle[] getSalida() {
        return salida;
    }

}
