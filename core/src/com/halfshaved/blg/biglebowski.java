package com.halfshaved.blg;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.halfshaved.blg.Screens.GamePlayScreen;

public class biglebowski extends Game
{
    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;
	public static final float PPM = 100; //PIXELS PER METRE **have to be at least a static variable
						//has to be a float for 'divisional' reasons
	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new GamePlayScreen(this)); //'setScreen' is of the 'Game' class (badlogic)

	}

	@Override
	public void render () {
        super.render();

	}
}
