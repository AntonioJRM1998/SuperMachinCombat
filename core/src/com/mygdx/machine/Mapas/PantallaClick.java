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
    private SpriteBatch batch;//Variable tipo SpriteBatch
    private Texture ttrSplash;//Variable tipo texture
    private Rectangle rectangle;//Variable tipo Rectangle
    private Actor actor;//Variable tipo Actor

    public PantallaClick(Main main) {
        super(main);
        batch=new SpriteBatch();//La inicializo aqui
        musicaMapa1=Gdx.audio.newMusic(Gdx.files.internal("musica/musicaInicio.mp3"));//Le asigno un archivo mp3 para la musica
        musicaMapa1.setVolume(1.0f);//Sirve para establecer el volumen
        musicaMapa1.setLooping(true);//Sirve para que se reproduzca en bucle
        ttrSplash = new Texture("recursos/titulo.png");//Asigno una imagen a la textura
        rectangle=new Rectangle();//Inicializo la variable Rectangle
        rectangle.set(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());//Asigno los valores a mi variable rectangle
        actor=new Actor();//Inicializo Actor
        actor.setBounds(0,0,rectangle.getWidth(),rectangle.getHeight());//Le asigno los valores de rectangle a mi actor
        stage=new Stage();//Inicializo el stage
        stage.addActor(actor);//Añado el actor al stage
        stage.setDebugAll(true);//Se usa para ver las dimensiones de nuestro actor
        InputMultiplexer multiplexer=new InputMultiplexer();
        multiplexer.addProcessor(new EscucharTeclado(this,main));//Asigno mi clase de escuchador para que me detecte las aciones
        Gdx.input.setInputProcessor(multiplexer);//Lo añado al input
    }
    @Override
    /**
     * Rendezia todo lo de la clase
     */
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
        musicaMapa1.play();//Inicio la musica
    }

    @Override
    public void hide() {
        super.hide();
        musicaMapa1.stop();//Paro la musica
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
