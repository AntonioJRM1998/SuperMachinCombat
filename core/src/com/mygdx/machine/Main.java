package com.mygdx.machine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.machine.Mapas.BaseScreen;
import com.mygdx.machine.Mapas.MapaInicial;
import com.mygdx.machine.Mapas.PantallaClick;

public class Main extends Game {
    private BaseScreen screen;

	@Override
	public void create () {
		setPantallaActual(new PantallaClick(this));
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
	public void setPantallaActual(BaseScreen screen){
	    this.screen=screen;
	    setScreen(this.screen);
    }
}
