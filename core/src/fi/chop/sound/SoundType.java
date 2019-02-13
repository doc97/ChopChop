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
public enum SoundType {
    
    KILL_SOUND("not text yet");
    
    private final String url;
    
    private SoundType(String url) {
        this.url = url;
    }
    
    public String getURL() {
        return url;
    }
}
