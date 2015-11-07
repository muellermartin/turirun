package net.mueller_martin.turirun;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import net.mueller_martin.turirun.screens.GameScreen;

/**
 *
 */
public class ScreenManager {

    public final static String TAG = ScreenManager.class.getName();
    GameScreen gameScreen;
    net.mueller_martin.turirun.MainMenuScreen menuScreen;
    Turirun game;
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
        if(state==0)
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
                    gameScreen = new GameScreen(this,game);
                    game.setScreen(gameScreen);
                    menuScreen.dispose();
                    menuScreen = null;
                    break;

                case Constants.MENUSCREEN:
                    menuScreen = new net.mueller_martin.turirun.MainMenuScreen(this);
                    game.setScreen(menuScreen);
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
              //  case Constants.SCREEN_MENU_AFTER_GAMEOVER:
              //      menuScreen.render(delta);
              //      break;
            }

        }

    }

}
