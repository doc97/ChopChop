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
    
    BELL("audio/SFX/Bell.ogg"), 
    CANCEL("audio/SFX/Cancel.ogg"),
    GASP_1("audio/SFX/Gasp1.ogg"), 
    GASP_2("audio/SFX/Gasp2.ogg"), 
    GASP_3("audio/SFX/Gasp3.ogg"), 
    GUILLOTINE_DROP("audio/SFX/GuillotineDrop.ogg"), 
    GUILLOTINE_RAISE("audio/SFX/GuillotineRaise.ogg"), 
    GUILLOTINE_SUCCESS_1("audio/SFX/GuillotineSuccess1.ogg"), 
    GUILLOTINE_SUCCESS_2("audio/SFX/GuillotineSuccess2.ogg"), 
    MENU_MOVE("audio/SFX/MenuMove.ogg"), 
    MENU_SELECT("audio/SFX/MenuSelect.ogg"), 
    NOTIFICATION("audio/SFX/Notification.mp3");
    
    private final String url;
    
    private SoundType(String url) {
        this.url = url;
    }
    
    public String getURL() {
        return url;
    }
}
