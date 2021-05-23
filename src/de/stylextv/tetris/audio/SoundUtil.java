package de.stylextv.tetris.audio;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import de.stylextv.tetris.main.Main;

public class SoundUtil {
	
	private static final float VOLUME = 0.85f;
	
	public static Clip CLEAR_SOUND;
	public static Clip TETRIS_SOUND;
	public static Clip DEATH_SOUND;
	
	public static void load() {
		try {
			
			CLEAR_SOUND = loadWav("clear.wav");
			TETRIS_SOUND = loadWav("tetris.wav");
			DEATH_SOUND = loadWav("death.wav");
			
		} catch (Exception ex) {
			ex.printStackTrace();
			
			System.exit(1);
		}
	}
	
	private static Clip loadWav(String name) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
		Clip clip = AudioSystem.getClip();
		
        AudioInputStream inputStream = AudioSystem.getAudioInputStream(SoundUtil.class.getResource("/assets/sounds/" + name));
        
        clip.open(inputStream);
        
        FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        
        float min = volumeControl.getMinimum() / 2;
        float gain = min - (min * VOLUME);
        
        volumeControl.setValue(gain);
        
        return clip;
	}
	
	public static void play(Clip clip) {
		if(Main.isRunning()) {
			clip.setFramePosition(0);
			clip.start();
		}
	}
	
}