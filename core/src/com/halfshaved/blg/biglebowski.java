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
	public static final short DEFAULT_BIT = 1; //***IMPORTANT TODO: look more into bitwise operators (table of values thing)
	public static final short DUDE_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short DESTROYED_BIT = 16;

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
