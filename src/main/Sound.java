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
			 soundURL[2] = getClass().getResource("/sounds/Door.wav");
			 soundURL[3] = getClass().getResource("/sounds/Eating1.wav");
			 soundURL[4] = getClass().getResource("/sounds/chest.wav");
			 soundURL[5] = getClass().getResource("/sounds/coin.wav");
			 soundURL[6] = getClass().getResource("/sounds/hit_mon.wav");
			 soundURL[7] = getClass().getResource("/sounds/receive_damage.wav");
			 soundURL[8] = getClass().getResource("/sounds/attack_pistol.wav");
			 soundURL[9] = getClass().getResource("/sounds/levelup.wav");
			 soundURL[10] = getClass().getResource("/sounds/cursor.wav");
			 
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
