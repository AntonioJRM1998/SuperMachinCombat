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
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(pantallaInicio==false) {
            if (pantallaClick.getRectangle().x <= screenX) {
                pantallaInicio=true;
                main.pararMusica();
                main.getScreen().ponerMuscia("musica/musicaCasa.mp3");
                main.setPantallaActual(new MapaInicial(main, 100, 100));
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
