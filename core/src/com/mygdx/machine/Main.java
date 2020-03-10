package com.mygdx.machine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.machine.Mapas.BaseScreen;
import com.mygdx.machine.Mapas.PantallaClick;
import com.mygdx.machine.basedatos.BaseDatosJuego;

public class Main extends Game {
    private BaseScreen screen;//Variable tipo scree
    private BaseDatosJuego baseDatosJuego;//Variable base de datos

	/**
	 *
	 * @param bd le pasamos una base de datos
	 */
    public Main(BaseDatosJuego bd){
        this.baseDatosJuego=bd;
    }

	@Override
	public void create () {
		setPantallaActual(new PantallaClick(this));//Establece la screen
	}

	@Override
	/**
	 * Renderiza la pantalla
	 */
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);//Sirve para cambiar el color del fondo
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        screen.render(Gdx.graphics.getDeltaTime());
	}

	/**
	 * Quitamos de memoria la screen
	 */
	@Override
	public void dispose () {
		screen.dispose();
	}

	/**
	 * Obtengo el screen que hay en el main
	 * @return
	 */
	@Override
	public BaseScreen getScreen() {
		return screen;
	}

	/**
	 * Uso esa funcion para establecer la screen que quiera
	 * @param screen le paso una variable tipo screen y me la asigna a la de main
	 */
	public void setPantallaActual(BaseScreen screen){
	    this.screen=screen;
	    setScreen(this.screen);
    }

	/**
	 * La uso para recuperar la base de datos
	 * @return
	 */
    public BaseDatosJuego getBaseDatosJuego() {
        return baseDatosJuego;
    }
}
