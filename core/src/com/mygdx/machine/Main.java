package com.mygdx.machine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.machine.Mapas.BaseScreen;
import com.mygdx.machine.Mapas.PantallaClick;
import com.mygdx.machine.basedatos.BaseDatosJuego;

public class Main extends Game {
    private BaseScreen screen;
    private BaseDatosJuego baseDatosJuego;

    public Main(BaseDatosJuego bd){
        this.baseDatosJuego=bd;
    }

	@Override
	public void create () {
		setPantallaActual(new PantallaClick(this));
        System.out.println("illo");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        screen.render(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void dispose () {
		screen.dispose();
	}

	@Override
	public BaseScreen getScreen() {
		return screen;
	}

	public void setPantallaActual(BaseScreen screen){
	    this.screen=screen;
	    setScreen(this.screen);
    }

    public BaseDatosJuego getBaseDatosJuego() {
        return baseDatosJuego;
    }
}
