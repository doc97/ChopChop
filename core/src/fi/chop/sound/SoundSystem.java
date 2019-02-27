/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.chop.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
/**
 *
 * @author Pelitalo
 */
public class SoundSystem {
    
    private AssetManager assets;
    private SoundType[] sounds;
    private MusicType[] musics;
    
    private Music bgMusic;
    private boolean bgMusicInitialized = false;
    
    private float masterVolume = 1;
    private float bgMusicVolume = 1;
    private float effectVolume = 1;
    
    public SoundSystem(AssetManager assets, SoundType[] sounds, MusicType[] musics) {
        this.assets = assets;
        this.sounds = sounds;
        this.musics = musics;
    }
    
    public void loadSounds() {
        for(SoundType type : sounds) {
            assets.load(type.getURL(), Sound.class);
        }
        for(MusicType type : musics) {
            assets.load(type.getURL(), Music.class);
        }
    }
    
    public void playOnce(SoundType s) {
        Sound sound = assets.get(s.getURL(), Sound.class);
        sound.play(effectVolume * masterVolume);
    }
    
    public void setBackgroundMusic(MusicType m) {
        if(bgMusicInitialized) {
            bgMusic.stop();
            bgMusic.dispose();
        }
        bgMusicInitialized = true;
        
        bgMusic = assets.get(m.getURL(), Music.class);
        bgMusic.play();
        bgMusic.setVolume(bgMusicVolume * masterVolume);
        bgMusic.setLooping(true);
    }
    
    public void setBackgroundMusicVoume(float bgMusicVolume) {
        this.bgMusicVolume = bgMusicVolume;
        if(bgMusicInitialized) {
            bgMusic.setVolume(bgMusicVolume * masterVolume);
        }
    }
    
    public float getBackgroundMusicVolume() {
        return bgMusicVolume;
    }
    
    public void setEffectVolume(float effectVolume) {
        this.effectVolume = effectVolume;
    }
    
    public float getEffectVolume() {
        return effectVolume;
    } 
    
    public void setMasterVolume(float masterVolume) {
        this.masterVolume = masterVolume;
        if(bgMusicInitialized) {
            bgMusic.setVolume(bgMusicVolume * masterVolume);
        }
    }
    
    public float getMasterVolume() {
        return masterVolume;
    }
}
