package net.mueller_martin.turirun;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;

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

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        TiledMapTileSet tileset = map.getTileSets().getTileSet("animated");

        if (tileset != null) {
            // Set Animations
            System.out.println("Level Animations!");

            // Search Animated Tiles
            for (int y = 0; y < layer.getHeight(); y++) {
                for (int x = 0; x < layer.getWidth(); x++) {
                    if (layer.getCell(x,y) == null)
                        continue;

                    int id = layer.getCell(x,y).getTile().getId();

                    if (tileset.getTile(id) != null) {
                        // is animated tileset
                        Array<StaticTiledMapTile> at = new Array<StaticTiledMapTile>();
                        if (tileset.getTile(id) != null) at.add((StaticTiledMapTile) tileset.getTile(id));
                        if (tileset.getTile(id+1) != null) at.add((StaticTiledMapTile) tileset.getTile(id+1));
                        if (tileset.getTile(id+2) != null) at.add((StaticTiledMapTile) tileset.getTile(id+2));
                        AnimatedTiledMapTile animation = new AnimatedTiledMapTile(0.3f, at);

                        layer.getCell(x, y).setTile(animation);
                    }
                }
            }

        }

        tiledMapRenderer = new OrthogonalTiledMapRenderer(map);
    }

    public void render()
    {
        AnimatedTiledMapTile.updateAnimationBaseTime();
        tiledMapRenderer.setView(CameraHelper.instance.camera);
        tiledMapRenderer.render();
    }
}
