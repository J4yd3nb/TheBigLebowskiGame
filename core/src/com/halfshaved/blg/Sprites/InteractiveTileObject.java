package com.halfshaved.blg.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.halfshaved.blg.biglebowski;

/**
 * Created by Jayden on 27/04/2016.
 */
public abstract class InteractiveTileObject
{
    protected World world;
    protected TiledMap level;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;

    public InteractiveTileObject(World world, TiledMap level, Rectangle bounds)
    {
        this.world = world;
        this.level = level;
        this.bounds = bounds;

        //this code can go into the coin class separately but because both the brick and coin are going to be interactive we can put it all here
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / biglebowski.PPM, (bounds.getY() + bounds.getHeight() / 2) / biglebowski.PPM);
        //bdef.angle = 45; //** Delete this line

        body = world.createBody(bdef); //adding the body to the world

        shape.setAsBox(bounds.getWidth() / 2 / biglebowski.PPM, bounds.getHeight() / 2 / biglebowski.PPM); //adding the fixture to the world
        fdef.shape = shape; //Look into the FixtureDef class (Shape shape, 'Shape' class is abstract)
        fixture = body.createFixture(fdef); //adding the fixture to the body
        //when creating a fixture around a coin or brick this ("fixture =" will capture it)
    }

    public abstract void onHeadHit();
    public void setCategoryFilter(short filterBit)
    {
        Filter filter = new Filter(); //"can use this if you don't have access to the fixture definition itself"
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    public TiledMapTileLayer.Cell getCell()
    {
        TiledMapTileLayer layer = (TiledMapTileLayer) level.getLayers().get(0); //type casting *IN LEVEL1.TMX THE LAYERS ARE STUFF UP
        return layer.getCell((int)(body.getPosition().x * biglebowski.PPM / 16),(int)(body.getPosition().y * biglebowski.PPM / 16)); //type casting as-well
                                                        //scaled up so it will look identical to the Tiled Map
                                                                        //then divided by 16 to get it at the correct tile location
    }
}
