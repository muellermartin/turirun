package net.mueller_martin.turirun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Der Preference Manager speichert wichtige Informationen in einer xml Datei
 * Zum Beispiel der Spielername oder der ausgewaehlte Charakter werden gespeichert.
 * 
 * Nach Vorlage http://steigert.blogspot.de/2012/03/6-libgdx-tutorial-preferences.html
 * 
 * @author Daniel
 *
 *
 * BISHER NOCH KEINE VERSCHLUESSELUNG --> Modifizierbar
 *
 */
public class PreferenceManager
{
	private static final String TAG = PreferenceManager.class.getName();
	public static final PreferenceManager instance = new PreferenceManager();
	 Preferences Prefs;
	public PreferenceManager()
	{
		Gdx.app.log(TAG, "PREF MANAGER");
		Prefs = getPrefs();
		
	}
	
	protected Preferences getPrefs()
    {
        return Gdx.app.getPreferences(Constants.PREFERENCES_NAME);
    }
	
	public boolean setOptionDebug(boolean b)
	{
		Prefs.putBoolean(Constants.PREFERENCES_OPTIONS_DEBUG,b);
		Prefs.flush();
		return true;
	}
	
	public boolean getOptionDebug()
	{
		return Prefs.getBoolean(Constants.PREFERENCES_OPTIONS_DEBUG); 
	}

	

	
	
}
