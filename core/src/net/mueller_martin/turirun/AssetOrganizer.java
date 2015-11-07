package net.mueller_martin.turirun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by DM on 06.11.15.
 */
public class AssetOrganizer implements Disposable, AssetErrorListener
{

    public static final String TAG = AssetOrganizer.class.getName();

    public static final AssetOrganizer instance = new AssetOrganizer();
    private AssetOrganizer assetOrganizer;


    // Menu
    public BtnPlay btnPlay;
    public BtnQuit btnQuit;
    public MenuBtnBg menuBtnBg;


    public AssetOrganizer()
    {
    }


    public void init (AssetManager assetManager) {
        //audio = Gdx.audio;
        Gdx.app.log(TAG, "AssetManager");
        this.assetOrganizer = assetOrganizer;
        // set asset manager error handler
        assetManager.setErrorListener(this);
        // load texture atlas

        assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS,
                TextureAtlas.class);
        assetManager.load("sfx/mine.wav", Sound.class);
        assetManager.load("sfx/mined.wav", Sound.class);

        // start loading assets and wait until finished
        assetManager.finishLoading();

        Gdx.app.debug(TAG, "# of assets loaded: "
                + assetManager.getAssetNames().size);
        for (String a : assetManager.getAssetNames())
            Gdx.app.debug(TAG, "asset: " + a);

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);
//        Sound mine = assetManager.get("sfx/mine.wav");
        for (Texture t : atlas.getTextures())
            t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        // MENU
        btnPlay = new BtnPlay(atlas);
        btnQuit = new BtnQuit(atlas);
        menuBtnBg = new MenuBtnBg(atlas);
    }

        public class MenuBtnBg
    {
        public final TextureRegion menuBtnBg;
        public MenuBtnBg(TextureAtlas atlas)
        {
            menuBtnBg = atlas.findRegion("menuBtnBg");
        }
    }

    public class BtnQuit
    {
        public final TextureRegion btnQuit;
        public BtnQuit(TextureAtlas atlas)
        {
            btnQuit = atlas.findRegion("btnQuit");
        }
    }

    public class BtnPlay
    {
        public final TextureRegion btnPlay;
        public BtnPlay(TextureAtlas atlas)
        {
            btnPlay = atlas.findRegion("btnPlay");
        }
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {

    }

    @Override
    public void dispose() {

    }
}
