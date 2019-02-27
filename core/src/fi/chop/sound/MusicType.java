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
    
    MUSIC("audio/BGM/ExecutionBGM.ogg");
    
    private final String url;
    
    private MusicType(String URL) {
        this.url = URL;
    }
    
    public String getURL() {
        return url;
    }
}
