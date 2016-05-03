package com.halfshaved.blg.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.halfshaved.blg.Sprites.Brick;
import com.halfshaved.blg.Sprites.Coin;
import com.halfshaved.blg.biglebowski;

/**
 * Created by Jayden on 27/04/2016.
 */
public class B2WorldCreator
{
    public B2WorldCreator(World world, TiledMap level)
    {
        BodyDef bdef = new BodyDef(); //definition basically
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef(); //define and add to body, bodies get added to the world
        Body body;

        //create bodies and fixtures at every object in our level
        for(MapObject object : level.getLayers().get(2).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle(); //casting

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / biglebowski.PPM, (rect.getY() + rect.getHeight() / 2) / biglebowski.PPM);
            //bdef.angle = 45; //** Delete this line

            body = world.createBody(bdef); //adding the body to the world

            shape.setAsBox(rect.getWidth() / 2 / biglebowski.PPM, rect.getHeight() / 2 / biglebowski.PPM); //adding the fixture to the world
            fdef.shape = shape; //Look into the FixtureDef class (Shape shape, 'Shape' class is abstract)
            body.createFixture(fdef); //adding the fixture to the body
        }

        for(MapObject object : level.getLayers().get(3).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle(); //casting

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / biglebowski.PPM, (rect.getY() + rect.getHeight() / 2) / biglebowski.PPM);
            //bdef.angle = 45; //** Delete this line

            body = world.createBody(bdef); //adding the body to the world

            shape.setAsBox(rect.getWidth() / 2 / biglebowski.PPM, rect.getHeight() / 2 / biglebowski.PPM); //adding the fixture to the world
            fdef.shape = shape; //Look into the FixtureDef class (Shape shape, 'Shape' class is abstract)
            body.createFixture(fdef); //adding the fixture to the body
        }

        for(MapObject object : level.getLayers().get(4).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle(); //casting

            new Coin(world, level, rect);
        }

        for(MapObject object : level.getLayers().get(5).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle(); //casting

            new Brick(world, level, rect);
        }
    }
}
