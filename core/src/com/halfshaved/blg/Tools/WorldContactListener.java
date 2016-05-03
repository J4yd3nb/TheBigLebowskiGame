package com.halfshaved.blg.Tools;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.halfshaved.blg.Sprites.InteractiveTileObject;

/**
 * "INITIALIZE, CONSTRUCTOR, LOGIC, UPDATE" - Jayden 2k16
 */
public class WorldContactListener implements ContactListener
                                            // Is called when 2 fixtures collide in Box2D
{
    @Override
    public void beginContact(Contact contact)
    {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB(); //when two objects begin collide we need to know which is which
        //Gdx.app.log("Begin Contact:" , ""); //for base console log

        if(fixA.getUserData() == "head" || fixB. getUserData() == "head")
        {
            Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
            Fixture object = head == fixA ? fixB : fixA; //head is fixA then fixB is the object, otherwise fixA must be the object

            //is that object a pipe or ground etc. ?
            if(object.getUserData() instanceof InteractiveTileObject) //InteractiveTileObject is an extension for the Coin and Brick class(s) ("InteractiveTileObject.class")
            {
                ((InteractiveTileObject) object.getUserData()).onHeadHit(); //will execute the onHeadHit() method (for whatever TileObject was interactive with (each will be different (e.g. coin and brick)))
                //Read into the access of 'static' and 'non static' access (the above(the actual logic in the code))
                //*UPDATE* to do with the "fixture.setUserData(this);" in the coin(& brick) further then accessing the 'onHeadHit()' method
            }
        }
    }

    @Override
    public void endContact(Contact contact)
    {
        //Gdx.app.log("End Contact:" , ""); //for base console log
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
