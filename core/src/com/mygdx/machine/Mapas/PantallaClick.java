package com.mygdx.machine.Mapas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.machine.Escuchadores.EscucharTeclado;
import com.mygdx.machine.Main;
import com.mygdx.machine.Personaje.Jugador;


public class PantallaClick extends BaseScreen {
    private SpriteBatch batch;
    private Texture ttrSplash;
    private Rectangle rectangle;
    private Actor actor;

    public PantallaClick(Main main) {
        super(main);
        batch=new SpriteBatch();
        ttrSplash = new Texture("recursos/titulo.png");
        rectangle=new Rectangle();
        rectangle.set(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        actor=new Actor();
        actor.setBounds(0,0,rectangle.getWidth(),rectangle.getHeight());
        stage=new Stage();
        stage.addActor(actor);
        stage.setDebugAll(true);
        InputMultiplexer multiplexer=new InputMultiplexer();
        multiplexer.addProcessor(new EscucharTeclado(this,main));
        Gdx.input.setInputProcessor(multiplexer);
    }
    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(ttrSplash, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        stage.draw();
    }
    public void pasaPantalla(){
        main.setPantallaActual(new MapaInicial(main,100,100));
    }

    public Actor getActor() {
        return actor;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
