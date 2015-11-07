package net.mueller_martin.turirun;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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
    Audio audio;
    public float stateTime;

    // Menu
    public BtnPlay btnPlay;
    public BtnQuit btnQuit;
    public MenuBtnBg menuBtnBg;

    //Audio
    public Audio_CannibalsEat audio_cannibalsEat;
    public Audio_CannibalsWin audio_cannibalsWin;
    public Audio_SoundTrack audio_soundTrack;
    public Audio_Running1 audio_running1;
    public Audio_Walking1 audio_walking1;
    public Audio_Walking2 audio_walking2;

    // Gfx
    public Stone stone;
    public Player player;

    public Animation touriRunDown;


    public AssetOrganizer()
    {

    }



    public void init (AssetManager assetManager) {
        audio = Gdx.audio;
        Gdx.app.log(TAG, "AssetManager");
        this.assetOrganizer = assetOrganizer;
        // set asset manager error handler
        assetManager.setErrorListener(this);
        // load texture atlas

        assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS,
                TextureAtlas.class);
        //assetManager.load("audio/audiocannibalsEat.ogg",Sound.class);
      //  assetManager.load("sfx/audio_cannibalsWin.ogg", Sound.class);
       // assetManager.load("sfx/I_Ate_The_Jungle.ogg", Sound.class);

        // start loading assets and wait until finished
        assetManager.finishLoading();

        Gdx.app.debug(TAG, "# of assets loaded: "
                + assetManager.getAssetNames().size);
        for (String a : assetManager.getAssetNames())
            Gdx.app.debug(TAG, "asset: " + a);

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);
//        Sound mine = assetManager.get("sfx/mine.ogg");
        for (Texture t : atlas.getTextures())
            t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        // MENU
        btnPlay = new BtnPlay(atlas);
        btnQuit = new BtnQuit(atlas);
        menuBtnBg = new MenuBtnBg(atlas);
        // AUDIO
        audio_cannibalsEat = new Audio_CannibalsEat();
        audio_cannibalsWin = new Audio_CannibalsWin();
        audio_soundTrack = new Audio_SoundTrack();
        audio_running1 = new Audio_Running1();
        audio_walking1 = new Audio_Walking1();
        audio_walking2 = new Audio_Walking2();
        // Gfx
        stone = new Stone(atlas);
        player = new Player(atlas);

        //gamegfx
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




    // GFX
    public class Stone
    {
        public final TextureRegion stone;
        public Stone(TextureAtlas atlas)
        {
            stone = atlas.findRegion("tile_5botanik");
        }
    }
    public class Player
    {
        public final TextureAtlas.AtlasRegion player;
        public Player(TextureAtlas atlas){ player = atlas.findRegion("tourist");}
    }

    // AUDIO

    public class Audio_CannibalsEat
    {
        public final Sound audio_cannibalsEat;
        public Audio_CannibalsEat()
        {
            audio_cannibalsEat = audio.newSound(Gdx.files.internal("audio/audio_CannibalsEat.ogg"));
        }
    }

    public class Audio_CannibalsWin
    {
        public final Sound audio_cannibalsWin;
        public Audio_CannibalsWin()
        {
            audio_cannibalsWin = audio.newSound(Gdx.files.internal("audio/audio_cannibalsWin.ogg"));
        }
    }
    public class Audio_SoundTrack
    {
        public final Sound audio_soundTrack;
        public Audio_SoundTrack()
        {
            audio_soundTrack = audio.newSound(Gdx.files.internal("audio/I_Ate_The_Jungle.ogg"));
        }
    }

    public class Audio_Running1
    {
        public final Sound audio_running1;
        public Audio_Running1()
        {
            audio_running1 = audio.newSound(Gdx.files.internal("audio/audio_running1.ogg"));
        }
    }
    public class Audio_Walking1
    {
        public final Sound audio_walking1;
        public Audio_Walking1()
        {
            audio_walking1 = audio.newSound(Gdx.files.internal("audio/audio_walking1.ogg"));
        }
    }

    public class Audio_Walking2
    {
        public final Sound audio_walking2;
        public Audio_Walking2()
        {
            audio_walking2 = audio.newSound(Gdx.files.internal("audio/audio_walking2.ogg"));
        }
    }


    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {

    }

    @Override
    public void dispose() {

    }
}
