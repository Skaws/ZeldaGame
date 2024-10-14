package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    Clip clip;
    // URL array stores the filepaths of the sound files in an array
    URL soundURL[] = new URL[30];
    
    public Sound(){
        soundURL[0] = getClass().getResource("/sound/overworldTheme.wav");
        soundURL[1] = getClass().getResource("/sound/rupee.wav");
        soundURL[2] = getClass().getResource("/sound/itemGet.wav");
        soundURL[3] = getClass().getResource("/sound/doorOpen.wav");
        soundURL[4] = getClass().getResource("/sound/classicPowerup.wav");
        soundURL[5] = getClass().getResource("/sound/victory.wav");
    }
    // pass in the index of the filepath in the array and turn that into the audio
    public void setFile(int i){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            // open the clip in the same way that u open data files in programming
            clip.open(ais);
        } catch (Exception e) {
            System.out.println("Failed to get sound at ID: "+i);
            System.out.println(e.getMessage());
        }
    }
    
    public void play(){

        clip.start();

    }

    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        clip.stop();
    }
}
