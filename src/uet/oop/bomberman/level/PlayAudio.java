package uet.oop.bomberman.level;

import javax.sound.sampled.*;
import java.net.URL;

public class PlayAudio {
    private Clip clip;
    public PlayAudio (String pathfile) {
        try{
            URL path = this.getClass().getResource(pathfile);
            AudioInputStream ais = AudioSystem.getAudioInputStream(path);
            clip = AudioSystem.getClip();
            clip.open(ais);
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void play() {
        if(clip!=null) {
            clip.setFramePosition(0);
            clip.loop(0);
            clip.start();
        }
    }

    public void playmusic() {
        if(clip!=null) {
            clip.setFramePosition(0);
            clip.loop(100);
            clip.start();
        }
    }

    public void loop() {
        clip.loop(clip.LOOP_CONTINUOUSLY);
    }

    public void pause() {
        clip.stop();
    }

    public void stop() {
        clip.close();
    }
}
