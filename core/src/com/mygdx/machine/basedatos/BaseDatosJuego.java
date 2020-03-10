package com.mygdx.machine.basedatos;

public interface BaseDatosJuego {
    public int cargar();//Cargo el valor de los puntos de mi base de datos
    public void guardar(int nuevaPuntuacion);//Guardo el valor de los puntos en la base de datos
}
