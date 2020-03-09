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


public class PantallaClick extends BaseScreen {
    private SpriteBatch batch;
    private Texture ttrSplash;
    private Rectangle rectangle;
    private Actor actor;

    public PantallaClick(Main main) {
        super(main);
        batch=new SpriteBatch();
        musicaMapa1=Gdx.audio.newMusic(Gdx.files.internal("musica/musicaInicio.mp3"));
        musicaMapa1.setVolume(1.0f);
        musicaMapa1.setLooping(true);
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
        musicaMapa1=Gdx.audio.newMusic(Gdx.files.internal("musica/musicaInicio.mp3"));
        musicaMapa1.play();
        musicaMapa1.setLooping(true);
    }
    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(ttrSplash, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        stage.draw();
    }

    @Override
    public void show() {
        super.show();
        musicaMapa1.play();
    }

    @Override
    public void hide() {
        super.hide();
        musicaMapa1.stop();
    }

    public Actor getActor() {
        return actor;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public void dispose() {
        super.dispose();
        musicaMapa1.dispose();
    }
}
