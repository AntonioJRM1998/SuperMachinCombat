package com.mygdx.machine.Escuchadores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.mygdx.machine.Main;
import com.mygdx.machine.Mapas.Colisiones;
import com.mygdx.machine.Mapas.MapaInicial;
import com.mygdx.machine.Mapas.PantallaClick;
import com.mygdx.machine.Personaje.Jugador;
import com.mygdx.machine.basedatos.BaseDatosJuego;
import com.sun.org.apache.bcel.internal.generic.BREAKPOINT;

public class EscucharTeclado implements InputProcessor {
    private Jugador personaje;
    private PantallaClick pantallaClick;
    private Main main;
    private Boolean pantallaInicio;

    public EscucharTeclado(Jugador personaje){
        super();
        this.personaje=personaje;
        pantallaInicio=true;

    }

    /**
     * Este constructor solo lo uso para la pantalla inical
     * @param pantallaClick le paso la pantalla inicial
     * @param main le paso el main
     */
    public EscucharTeclado(PantallaClick pantallaClick,Main main){
        super();
        this.pantallaClick=pantallaClick;
        this.main=main;
        pantallaInicio=false;
    }
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
     return false;
    }

    @Override
    /**
     * Esta funcion se encarga de pasar de pantalla al hacerle click
     */
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(pantallaInicio==false) {
            if (pantallaClick.getRectangle().x <= screenX) {
                pantallaInicio=true;//La convierto en true para no poder volver a entrar a este if
                main.setPantallaActual(new MapaInicial(main, 100, 100,main.getScreen().getBd()));//Inicio el mapa Principal
            }
        }

       return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
