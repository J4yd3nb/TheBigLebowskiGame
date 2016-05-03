package com.halfshaved.blg.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Jayden on 27/04/2016.
 */
public class Brick extends InteractiveTileObject
{
    public Brick(World world, TiledMap level, Rectangle bounds)
    {
        super(world, level, bounds);
        fixture.setUserData(this);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Brick", "Collision");
    }
}
