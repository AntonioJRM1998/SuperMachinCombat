package com.mygdx.machine.Escuchadores;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.machine.Mapas.Colisiones;
import com.mygdx.machine.Personaje.Jugador;

public class EscucharTeclado implements InputProcessor {
    private Jugador personaje;
    private Colisiones colisiones;
    private Actor[]actors;
    private Rectangle[]rectangles;
    private Boolean olision;

    public EscucharTeclado(Jugador personaje){
        super();
        this.personaje=personaje;

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
        System.out.println(screenX+" y:"+screenY);
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
