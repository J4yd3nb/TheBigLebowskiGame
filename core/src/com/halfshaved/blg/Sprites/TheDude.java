package com.halfshaved.blg.Sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.halfshaved.blg.Screens.GamePlayScreen;
import com.halfshaved.blg.biglebowski;

/**
 * Created by Jayden on 25/04/2016.
 */
public class TheDude extends Sprite //P.S. NO 'draw' METHOD IS NEEDED IN OUR 'TheDude' CLASS AS THE 'Sprite' class already knows how to draw (inside the 'Sprite' class): "extends Sprite" for a reason
{ //TODO: Make him not dip between blocks (body is getting upwards velocity (bouncing inbetween bricks with a velocity) which changes the sprite)
    public enum State { FALLING, JUMPING, STANDING, RUNNING} //An enum type is a special data type that enables for a variable to be a set of predefined constants. The variable must be equal to one of the values that have been predefined for it
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body; //A rigid body is created World.CreateBody
    private TextureRegion thedudeStand;
    private Animation thedudeRun;
    private Animation thedudeJump;
    private float stateTimer; //to track the time in any given state (run, jump etc.)
    private boolean runningRight;

    public TheDude(World world, GamePlayScreen screen)
    {           //BELOW: calling through our GamePlayScreen('screen') to our method getAtlas(), which is a TextureAtlas java class (has getRegion() method in it(which has its own parameters))
        super(screen.getAtlas().findRegion("little_mario")); //super goes to the superclass at the moment which is the 'Sprites' class (an extension to TheDude)
        this.world = world;         //super is used to call the constructor, methods and properties of parent class
            //super() calls the parent constructor with no arguments.
            //It can be used also with arguments. I.e. super(argument1) and it will call the constructor that accepts 1 parameter of the type of argument1 (if exists).
            //Also it can be used to call methods from the parent. I.e. super.aMethod()
        defineTheDude(); //it's not possible the dude is unpredictable
                                        //Found from above super statement

        Array<TextureRegion> frames = new Array<TextureRegion>(); //This 'Array<>' is a badlogic class and we filling it with TextureRegion()'s (get passed in the constructor) *Need this Array to initialize animations
        for (int i = 1; i < 4; i++)
        {
            frames.add(new TextureRegion(getTexture(), i * 16 + 384, 0, 16, 16));
            thedudeRun = new Animation(0.1f, frames); // "public Animation (float frameDuration, Array<? extends TextureRegion> keyFrames)"
            //frames.clear();
        }
        for (int i = 4; i < 6; i++)
        {
            frames.add(new TextureRegion(getTexture(), i * 16 + 384, 0, 16, 16));
            thedudeJump = new Animation(0.1f, frames);
            frames.clear();
        }

        thedudeStand = new TextureRegion(getTexture(), 384, 0, 16, 16); //getTexture() returns 'texture' which is a 'Texture' WHICH is never declared in our class only TextureRegion()
        setBounds(384, 0, 16 / biglebowski.PPM, 16 / biglebowski.PPM); //how large to render our sprite on screen
        //part of Sprites class                    //remember everything has to be scaled
        setRegion(thedudeStand);//the TextureRegion (thedudeStands) is now connected to the correct sprite
        //^ 'Sprite''s draw method need vertices (an area or bounds to know what to draw from) and 'setRegion()' is all 'Sprite' needs to use its inbuilt draw method (will set the vertices) "getVertices();"
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

    }

    public void update(float dt)
    {                                       //the sprite of the dude
        setPosition(b2body.getPosition().x - getWidth()/2 ,b2body.getPosition().y - getHeight()/2); //setPosition: in 'Sprite' class *The position of the fixture is always changing
                    //the fixture/body of the 'TheDude'
        setRegion(getFrame(dt)); //have to create the getFrame() method (setRegion() is of TextureRegion, but creating getFrame() for setRegion() below)
    }

    public TextureRegion getFrame(float dt) //creating the getFrame() method, but a method for TextureRegion
    {
        currentState = getState();

        TextureRegion region;
        switch(currentState)
        {
            case JUMPING:
                region = thedudeJump.getKeyFrame(stateTimer); //0.1f frameDuration
                break;
            case RUNNING:
                region = thedudeRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING: case STANDING: default: region = thedudeStand;
        }

        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) //running left or right (checking and remembering which way the dude was running)
        {                                                           //is not already flipped then do...
            region.flip(true, false);
            runningRight = false;
        }
        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX())
        {
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0; //Basic Form: "boolean statement ? true result : false result;"
                                                                    // If the 'currentState' != 'previousState' then stateTimer is reset to 0 (State(s) might be out of wacko)
        //the code will run through and when it reaches here (line below) the currentState will be passed to previousState
        previousState = currentState;
        return region;
    }

    public State getState()
    {
        if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING)) //as he jumps and then falls he will have the jumping animation
            return State.JUMPING;
        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    public void defineTheDude()
    {
        //defining the Dude's characteristics
        BodyDef bdef = new BodyDef();
        bdef.position.set(32/ biglebowski.PPM,32 / biglebowski.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef); //creates our 'body' in the world

        FixtureDef fdef = new FixtureDef();
        CircleShape cshape = new CircleShape();
        cshape.setRadius(6/ biglebowski.PPM);

        fdef.shape = cshape;
        b2body.createFixture(fdef);

        EdgeShape head = new EdgeShape(); //~a line between 2 different points
        head.set(new Vector2(-2 / biglebowski.PPM, 6 / biglebowski.PPM), new Vector2(2 / biglebowski.PPM, 6 / biglebowski.PPM));
        fdef.shape = head;
        fdef.isSensor = true; //*

        b2body.createFixture(fdef).setUserData("head");
        b2body.createFixture(fdef).setRestitution(10 / biglebowski.PPM); //*

        //For the lolz!
        //JointDef joint = new JointDef();
        //world.createJoint(joint);

    }
}
