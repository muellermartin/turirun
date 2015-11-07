package net.mueller_martin.turirun;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import net.mueller_martin.turirun.screens.GameScreen;
import net.mueller_martin.turirun.MainMenuScreen;
import net.mueller_martin.turirun.JoinMenuScreen;

public class ScreenManager {
    public final static String TAG = ScreenManager.class.getName();
    GameScreen gameScreen;
    MainMenuScreen menuScreen;
    JoinMenuScreen joinMenuScreen;
    public Turirun game;
    boolean switchScreen;
    int state = 0;
    Screen currentScreen;

    public ScreenManager(Turirun t)
    {
        switchScreen = false;

        game = t;
        init();
    }
    public void init()
    {
        if (state == 0)
        {
            setScreenState(Constants.MENUSCREEN);
        }
    }

    public void setScreenState(int s)
    {
        if(s != state) {
            this.state = s;
            switch (state) {
                case Constants.GAMESCREEN:
                    gameScreen = new GameScreen(this, game);
                    MusicBox.instance.playSound(Constants.AUDIO_MENUSWITCH);
                    game.setScreen(gameScreen);
                    joinMenuScreen.dispose();
                    joinMenuScreen = null;
                    break;

                case Constants.MENUSCREEN:
                    MusicBox.instance.playSound(Constants.AUDIO_MENUSWITCH);
                    menuScreen = new MainMenuScreen(this);

                    game.setScreen(menuScreen);
                    break;

                case Constants.JOINMENUSCREEN:
                    joinMenuScreen = new JoinMenuScreen(this);
                    MusicBox.instance.playSound(Constants.AUDIO_MENUSWITCH);
                    game.setScreen(joinMenuScreen);
                    menuScreen.dispose();
                    break;
            }
        }
    }

    public void render(float delta)
    {
        if(game.getScreen() != null)
        {

            switch(state)
            {
                case Constants.GAMESCREEN:
                    gameScreen.render(delta);
                    gameScreen.update(delta);
                    break;

                case Constants.MENUSCREEN:
                    menuScreen.render(delta);
                    break;

                case Constants.JOINMENUSCREEN:
                    joinMenuScreen.render(delta);

              //  case Constants.SCREEN_MENU_AFTER_GAMEOVER:
              //      menuScreen.render(delta);
              //      break;
            }
        }
    }
}
