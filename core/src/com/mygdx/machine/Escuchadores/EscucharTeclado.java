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
    private Colisiones colisiones;
    private Actor[]actors;
    private Rectangle[]rectangles;
    private Boolean olision;
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
        switch (keycode){
            case Input.Keys.D:
                personaje.hacerAnimaciones('d');
                break;
            case Input.Keys.S:
                personaje.hacerAnimaciones('s');
                break;
            case Input.Keys.A:
                personaje.hacerAnimaciones('a');
                break;
            case Input.Keys.W:
                personaje.hacerAnimaciones('w');
                break;
            case Input.Keys.SPACE:
                personaje.atacar();
                break;

        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.D:
                personaje.pararPersonaje('d');
                break;
            case Input.Keys.S:
                personaje.pararPersonaje('s');
                break;
            case Input.Keys.A:
                personaje.pararPersonaje('a');
                break;
            case Input.Keys.W:
                personaje.pararPersonaje('w');
                break;
            case 'f':
                personaje.moverJugador('f');
                break;
            case Input.Keys.SPACE:
                personaje.pararJugador();
                break;

        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        String letra=String.valueOf(character);
        switch (letra.toLowerCase()){
            case "w":
                    personaje.moverJugador('w');
                break;
            case "s":
                    personaje.moverJugador('s');
                    break;
            case "a":
                personaje.moverJugador('a');
                break;
            case "d":
                    personaje.moverJugador('d');
                break;
            case "f":
                personaje.moverJugador('f');
                break;

        }        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(pantallaInicio==false) {
            if (pantallaClick.getRectangle().x <= screenX) {
                pantallaInicio=true;
                main.setPantallaActual(new MapaInicial(main, 100, 100));
            }
        }
        if (screenX < Gdx.graphics.getWidth() / 2) {
            keyDown('a');
        }else if(screenX>Gdx.graphics.getWidth() / 2){
            keyDown('d');
        }

       return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (screenX < Gdx.graphics.getWidth() / 2) {
            keyUp('a');
        }else if(screenX>Gdx.graphics.getWidth() / 2){
            keyUp('d');
        }
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
