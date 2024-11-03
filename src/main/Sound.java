package main;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
public class Sound {
	Clip clip;
	URL soundURL[]=new URL[30];
	public Sound() {
			 soundURL[0] = getClass().getResource("/sounds/memories.wav");
			 soundURL[1] = getClass().getResource("/sounds/wano.wav");
			 soundURL[2] = getClass().getResource("/sounds/luffydoor.wav");
			 soundURL[3] = getClass().getResource("/sounds/luffyblaugh.wav");
			 soundURL[4] = getClass().getResource("/sounds/luffywin.wav");
			 
	}
	public void setFile(int i) {
        try {    
		AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip=AudioSystem.getClip();
			clip.open(ais);
        }catch(Exception e) {
        	e.printStackTrace();
        }
	}
	public void play() {
		clip.start();
	}
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void stop() {
		clip.stop();
	}
}
