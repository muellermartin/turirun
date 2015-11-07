package net.mueller_martin.turirun;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.ArrayList;

/**
 * Created by DM on 07.11.15.
 *
 *
 */
public class Level {
    public final static String TAG = Level.class.getName();

    TiledMapRenderer tiledMapRenderer;
    TiledMap map;

    public Level()
    {
        map = new TmxMapLoader().load("maps/lvl.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map);
    }

    public void render()
    {
        tiledMapRenderer.setView(CameraHelper.instance.camera);
        tiledMapRenderer.render();
    }
}
