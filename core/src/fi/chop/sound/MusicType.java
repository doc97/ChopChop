/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.chop.sound;

/**
 *
 * @author Pelitalo
 */
public enum MusicType {
    
    MAIN_MUSIC("audio/BGM/Lute.ogg", 1),
    CROWD_SOUNDS("audio/BGS/Crowd.ogg", 0.2f);

    private final String url;
    private final float relativeVolume;
    
    MusicType(String URL, float relativeVolume) {
        this.url = URL;
        this.relativeVolume = relativeVolume;
    }
    
    public String getURL() {
        return url;
    }

    public float getRelativeVolume() {
        return relativeVolume;
    }
}
