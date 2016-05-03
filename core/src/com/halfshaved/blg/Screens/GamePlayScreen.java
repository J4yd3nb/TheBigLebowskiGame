package com.halfshaved.blg.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.halfshaved.blg.Sprites.TheDude;
import com.halfshaved.blg.Tools.B2WorldCreator;
import com.halfshaved.blg.Tools.WorldContactListener;
import com.halfshaved.blg.biglebowski;

/**
 * "INITIALIZE, CONSTRUCTOR, LOGIC, UPDATE" - Jayden 2k16
 */
public class GamePlayScreen implements Screen
{ //look into using LibGDX's asset's manager (Google, use: for more graphics)
    private biglebowski game; //Game game
    private TextureAtlas atlas;

    private OrthographicCamera gameplaycam;
    private Viewport gamePort;

    private TmxMapLoader levelLoader;
    private TiledMap level;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;

    private TheDude thedude;

    public GamePlayScreen(biglebowski game) //Game game
    { //TODO: create HUD object
        atlas = new TextureAtlas("Sprites/Mario_and_Enemies.txt");

        this.game = game;
        gameplaycam = new OrthographicCamera();
        gamePort = new FitViewport(biglebowski.V_WIDTH/ biglebowski.PPM, biglebowski.V_HEIGHT/ biglebowski.PPM, gameplaycam);

        levelLoader = new TmxMapLoader();
        level = new TiledMap(); //*
        level = levelLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(level, 1 / biglebowski.PPM); //can have 'level' as only parameter but the Renderer accepts a unitScale (scaling unit, aka PPM)
        gameplaycam.position.set(gamePort.getWorldWidth() / 2,gamePort.getWorldHeight() / 2 ,0);

        world = new World(new Vector2(0,-10), true);
        b2dr = new Box2DDebugRenderer();

        thedude = new TheDude(world, this); //this would refer to passing in the 'GamePlayScreen' (look at the Dude constructor parameters), 'this' looks to the 'upper'(?) calling of the GamePlayScreen therefore this class where this code lives
                //calling the TheDude method and passing it the 'world' information
        new B2WorldCreator(world, level);

        world.setContactListener(new WorldContactListener());
    }

    public void update(float dt)
    {
        handleInput(dt);

        thedude.update(dt); //*The position of the fixture is always changing THEREFORE needs the update() treatment

        world.step(1 / 60f, 6, 2); //the f is required so the compiler knows its a float and not a double(default)

        gameplaycam.position.x = thedude.b2body.getPosition().x;
        //gameplaycam.position.y = thedude.b2body.getPosition().y;

        gameplaycam.update(); //keeps the camera updating
        renderer.setView(gameplaycam);
    }

    public TextureAtlas getAtlas() //making a method to return the 'TextureAtlas' 's 'atlas' (created above)
    {
        return atlas;
    }

    public void handleInput(float dt)
    {
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
        { //can use a 'force' instead of 'impulse' to make it immediate change in movement ('force' way could be used for car accelerating and negative value for a drift?)
            thedude.b2body.applyLinearImpulse(new Vector2(0,4), thedude.b2body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)&& thedude.b2body.getLinearVelocity().x <= 2)
        {
            thedude.b2body.applyLinearImpulse(new Vector2(0.1f,0), thedude.b2body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)&& thedude.b2body.getLinearVelocity().x >= -2)
        {
            thedude.b2body.applyLinearImpulse(new Vector2(-0.1f,0), thedude.b2body.getWorldCenter(), true);
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) //used to have to make delta yourself (UPDATED) "The time in seconds since the last render"
    {
        update(delta); //'delta'time is the time between the start of the previous and the start of the current call to 'render()'

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //game.batch.setProjectionMatrix(gameplaycam.combined);
       // game.batch.begin();
       // game.batch.draw(texture, 0, 0);
       // game.batch.end();
        renderer.render();

        b2dr.render(world, gameplaycam.combined); //'gameplaycam.combined' is it's projection matrix

        game.batch.setProjectionMatrix(gameplaycam.combined); //setting what our camera can see
        game.batch.begin(); //start putting the textures inside a box
        thedude.draw(game.batch); //giving the sprite to be drawn onto our SpriteBatch (game.batch)
        game.batch.end(); //end putting the textures inside the box

        //HUD code below:
    }

    @Override
    public void resize(int width, int height) //Does this method ever get called? I don't think so :(
    {
        gamePort.update(width,height); //***
        //public final void update (int screenWidth, int screenHeight) {
		//update(screenWidth, screenHeight, false);
	}

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() //anything that implements the disposable interface should be disposed
    {
        level.dispose();
        world.dispose();
        renderer.dispose();
        b2dr.dispose();
        //TODO: make the HUD disposed (need to extend the HUD class with Disposable and get rid of the stage) PART 9 3:20
    }
}
