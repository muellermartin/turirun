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

    public Audio_CameraSound audio_CameraSound;
    public Audio_Cheering audio_Cheering;
    public Audio_Helicopter audio_Helicopter;
    public Audio_MenuSelect audio_MenuSelect;
    public Audio_MenuSwitch audio_MenuSwitch;

    // AUDIO LOOPS
    public Audio_Loop1 audio_loop1;
    public Audio_Loop2 audio_loop2;
    public Audio_Loop3 audio_loop3;
    public Audio_Loop4 audio_loop4;
    public Audio_Loop5 audio_loop5;
    public Audio_Loop6 audio_loop6;

    // Gfx
    public Stone stone;
    public Player player;

    // Animation
    public TuriRunDown turiRunDown;
    public TuriRunTop turiRunTop;
    public TuriRunLeft turiRunLeft;
    public TuriRunRight turiRunRight;

    public CannibalRunDown cannibalRunDown;
    public CannibalRunTop cannibalRunTop;
    public CannibalRunLeft cannibalRunLeft;
    public CannibalRunRight cannibalRunRight;

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
        audio_CameraSound = new Audio_CameraSound();
        audio_Cheering = new Audio_Cheering();
        audio_Helicopter = new Audio_Helicopter();
        audio_MenuSelect = new Audio_MenuSelect();
        audio_MenuSwitch = new Audio_MenuSwitch();

        // AUDIO LOOPS
        audio_loop1 = new Audio_Loop1();
        audio_loop2 = new Audio_Loop2();
        audio_loop3 = new Audio_Loop3();
        audio_loop4 = new Audio_Loop4();
        audio_loop5 = new Audio_Loop5();
        audio_loop6 = new Audio_Loop6();


        // Gfx
        stone = new Stone(atlas);
        player = new Player(atlas);

        //gamegfx Animation
        turiRunDown = new TuriRunDown(atlas);
        turiRunTop = new TuriRunTop(atlas);
        turiRunLeft = new TuriRunLeft(atlas);
        turiRunTop = new TuriRunTop(atlas);

        cannibalRunDown = new CannibalRunDown(atlas);
        cannibalRunLeft = new CannibalRunLeft(atlas);
        cannibalRunTop = new CannibalRunTop(atlas);
        cannibalRunRight = new CannibalRunRight(atlas);



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




    // ANIMATION
    // TURI

    private class TuriRunDown
    {
        public final Animation turiRunDown;
        public TuriRunDown(TextureAtlas atlas) {
            turiRunDown = new Animation(1/4f, new TextureRegion( atlas.findRegion("tourist_front_walk1").getTexture()),new TextureRegion(atlas.findRegion("tourist_front_walk2").getTexture()),new TextureRegion(atlas.findRegion("tourist_front_walk3")),new TextureRegion(atlas.findRegion("tourist_front_walk4")));}
    }
    private class TuriRunTop {
        public final Animation turiRunTop;

        public TuriRunTop(TextureAtlas atlas) {
            turiRunTop = new Animation(1 / 4f, new TextureRegion(atlas.findRegion("tourist_back_walk1").getTexture()), new TextureRegion(atlas.findRegion("tourist_back_walk2").getTexture()), new TextureRegion(atlas.findRegion("tourist_back_walk3")), new TextureRegion(atlas.findRegion("tourist_back_walk4")));

        }}

        private class TuriRunLeft {
            public final Animation turiRunLeft;

            public TuriRunLeft(TextureAtlas atlas) {
                turiRunLeft = new Animation(1 / 4f, new TextureRegion(atlas.findRegion("tourist_left_walk1").getTexture()), new TextureRegion(atlas.findRegion("tourist_left_walk2").getTexture()), new TextureRegion(atlas.findRegion("tourist_left_walk3")), new TextureRegion(atlas.findRegion("tourist_left_walk4")));
            }
        }

        private class TuriRunRight {
            public final Animation turiRunRight;

            public TuriRunRight(TextureAtlas atlas) {
                turiRunRight = new Animation(1 / 4f, new TextureRegion(atlas.findRegion("tourist_right_walk1").getTexture()), new TextureRegion(atlas.findRegion("tourist_right_walk2").getTexture()), new TextureRegion(atlas.findRegion("tourist_right_walk3")), new TextureRegion(atlas.findRegion("tourist_right_walk4")));
            }
        }

    // ANIMATION
    // Cannibal


    private class CannibalRunDown
    {
        public final Animation cannibalRunDown;
        public CannibalRunDown(TextureAtlas atlas) {
            cannibalRunDown = new Animation(1/4f, new TextureRegion( atlas.findRegion("tourist_front_walk1").getTexture()),new TextureRegion(atlas.findRegion("tourist_front_walk2").getTexture()),new TextureRegion(atlas.findRegion("tourist_front_walk3")),new TextureRegion(atlas.findRegion("tourist_front_walk4")));}
    }
    private class CannibalRunTop {
        public final Animation cannibalRunTop;

        public CannibalRunTop(TextureAtlas atlas) {
            cannibalRunTop = new Animation(1 / 4f, new TextureRegion(atlas.findRegion("tourist_front_walk1").getTexture()), new TextureRegion(atlas.findRegion("tourist_front_walk2").getTexture()), new TextureRegion(atlas.findRegion("tourist_front_walk3")), new TextureRegion(atlas.findRegion("tourist_front_walk4")));

        }}

    private class CannibalRunLeft {
        public final Animation cannibalRunLeft;

        public CannibalRunLeft(TextureAtlas atlas) {
            cannibalRunLeft = new Animation(1 / 4f, new TextureRegion(atlas.findRegion("tourist_front_walk1").getTexture()), new TextureRegion(atlas.findRegion("tourist_front_walk2").getTexture()), new TextureRegion(atlas.findRegion("tourist_front_walk3")), new TextureRegion(atlas.findRegion("tourist_front_walk4")));
        }
    }

    private class CannibalRunRight {
        public final Animation cannibalRunRight;

        public CannibalRunRight(TextureAtlas atlas) {
            cannibalRunRight = new Animation(1 / 4f, new TextureRegion(atlas.findRegion("tourist_front_walk1").getTexture()), new TextureRegion(atlas.findRegion("tourist_front_walk2").getTexture()), new TextureRegion(atlas.findRegion("tourist_front_walk3")), new TextureRegion(atlas.findRegion("tourist_front_walk4")));
        }
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


    public class Audio_CameraSound
    {
        public final Sound audio_CameraSound;
        public Audio_CameraSound()
        {
            audio_CameraSound = audio.newSound(Gdx.files.internal("audio/audio_cameraSound.ogg"));
        }
    }

    public class Audio_Cheering
    {
        public final Sound audio_Cheering;
        public Audio_Cheering()
        {
            audio_Cheering = audio.newSound(Gdx.files.internal("audio/audio_cheering.ogg"));
        }
    }

    public class Audio_Helicopter
    {
        public final Sound audio_Helicopter;
        public Audio_Helicopter()
        {
            audio_Helicopter = audio.newSound(Gdx.files.internal("audio/audio_helicopter.ogg"));
        }
    }

    public class Audio_MenuSelect
    {
        public final Sound audio_MenuSelect;
        public Audio_MenuSelect()
        {
            audio_MenuSelect = audio.newSound(Gdx.files.internal("audio/audio_menuSelect.ogg"));
        }
    }

    public class Audio_MenuSwitch
    {
        public final Sound audio_MenuSwitch;
        public Audio_MenuSwitch()
        {
            audio_MenuSwitch = audio.newSound(Gdx.files.internal("audio/audio_menuSwitch.ogg"));
        }
    }

    public class Audio_Loop1
    {
        public final Sound audio_loop1;
        public Audio_Loop1()
        {
            audio_loop1 = audio.newSound(Gdx.files.internal("audio/audio_themeLoop1.ogg"));
        }
    }

    public class Audio_Loop2
    {
        public final Sound audio_loop2;
        public Audio_Loop2()
        {
            audio_loop2 = audio.newSound(Gdx.files.internal("audio/audio_themeLoop2.ogg"));
        }
    }


    public class Audio_Loop3
    {
        public final Sound audio_loop3;
        public Audio_Loop3()
        {
            audio_loop3 = audio.newSound(Gdx.files.internal("audio/audio_themeLoop3.ogg"));
        }
    }


    public class Audio_Loop4
    {
        public final Sound audio_loop4;
        public Audio_Loop4()
        {
            audio_loop4 = audio.newSound(Gdx.files.internal("audio/audio_themeLoop4.ogg"));
        }
    }
    public class Audio_Loop5
    {
        public final Sound audio_loop5;
        public Audio_Loop5()
        {
            audio_loop5 = audio.newSound(Gdx.files.internal("audio/audio_themeLoop5.ogg"));
        }
    }
    public class Audio_Loop6
    {
        public final Sound audio_loop6;
        public Audio_Loop6()
        {
            audio_loop6 = audio.newSound(Gdx.files.internal("audio/audio_themeLoop6.ogg"));
        }
    }




    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {

    }

    @Override
    public void dispose() {

    }
}
