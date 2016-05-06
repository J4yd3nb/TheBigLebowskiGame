package com.halfshaved.blg.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.halfshaved.blg.biglebowski;

/**
 * Created by Jayden on 27/04/2016.
 */
public class Coin extends InteractiveTileObject
{
    public Coin(World world, TiledMap level, Rectangle bounds)
    {
        super(world, level, bounds);
        fixture.setUserData(this); //"this" setting the data to the object itself
                                //also enables access to this particular object (coin for example)
        setCategoryFilter(biglebowski.COIN_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Coin", "Collision"); //auto adds the ':' between the two strings in the console
    }
}
