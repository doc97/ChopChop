/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.chop.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
/**
 *
 * @author Pelitalo
 */
public class SoundSystem {
    
    private AssetManager assets;
    private SoundType[] sounds;
    
    private Sound backgroundMusic;
    private float masterVolume = 1;
    private float backgroundMusicVolume = 1;
    private float effectVolume = 1;
    
    public SoundSystem(AssetManager assets, SoundType[] sounds) {
        this.assets = assets;
        this.sounds = sounds;
    }
    
    public void loadSounds() {
        for(SoundType type : sounds) {
            assets.load(type.getURL(), Sound.class);
        }
    }
    
    public void playOnce(SoundType s) {
        Sound sound = assets.get(s.getURL(), Sound.class);
        sound.play(effectVolume * masterVolume);
    }
    
    public void setBackgroundMusic(SoundType s) {
        Sound old = backgroundMusic;
        backgroundMusic = Gdx.audio.newSound(Gdx.files.internal(s.getURL()));
    }
    
    public void setBackgroundMusicVoume(float backgroundMusicVolume) {
        this.backgroundMusicVolume = backgroundMusicVolume;
        if(backgroundMusic != null) {
            
        }
        
    }
    
}
