package utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class FileUtilities {

	public static void saveImage(String filePath, String destinationPath) {
		InputStream input = null;
		OutputStream output = null;
		try {
			try {
				input = new FileInputStream(filePath);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				output = new FileOutputStream(destinationPath);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			byte[] buf = new byte[1024];
			int bytesRead;
			while ((bytesRead = input.read(buf)) > 0) {
				output.write(buf, 0, bytesRead);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void saveAudio(String ficheroOriginal, String ficheroCopia) {
		File f=new File(ficheroOriginal);
		AudioInputStream audioInputStream = null;
		try {
				audioInputStream = AudioSystem.getAudioInputStream(f);
				AudioFormat frmt = new AudioFormat(44100, 8, 1, true, true);
				AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, new File(ficheroCopia));	
		} 
		catch (UnsupportedAudioFileException | IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

	}

	public static void removeFile(String url) {
		File f = new File(url);
		f.delete();
	}
}
