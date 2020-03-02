package com.mygdx.machine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.machine.Escuchadores.EscucharTeclado;
import com.mygdx.machine.Mapas.Colisiones;
import com.mygdx.machine.Mapas.MapaInicial;
import com.mygdx.machine.Personaje.Jugador;

import java.awt.DisplayMode;
import java.awt.Point;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Jugador personaje;
    private MapaInicial mapaIncial;
    private Stage stage;
    private Colisiones colisiones;
	
	@Override
	public void create () {
        mapaIncial=new MapaInicial();
        personaje=new Jugador(100,100,
                mapaIncial.getMapWidthInPixels()/7f,mapaIncial.getMapHeightInPixels()/3f);
        colisiones=new Colisiones();
        colisiones.checkCollision(mapaIncial.getMap(),personaje);
        batch = new SpriteBatch();
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new EscucharTeclado(personaje));
        Gdx.input.setInputProcessor(multiplexer);
        stage=new Stage();
        stage.setDebugAll(true);
        stage.addActor(personaje);
        for(int b=0;b<colisiones.getActores().length-1;b++){
            stage.addActor(colisiones.getActores()[b]);
        }
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mapaIncial.getCamera().update();
        mapaIncial.renderSuelos();
        batch.begin();
        personaje.render(batch);
        batch.end();
        mapaIncial.renderizarObjetos();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
	}
	
	@Override
	public void dispose () {
		mapaIncial.dispose();
	}
}
