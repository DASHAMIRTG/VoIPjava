package org.example.audio;

import org.example.constants;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class AudioPlayback {

    private final SourceDataLine dataLine;

    private final AudioFormat audioFormat;

    public AudioPlayback() throws LineUnavailableException {
        this.audioFormat = new AudioFormat(constants.AudioSampleRate,
                                           constants.AudioSampleSize,
                                           constants.AudioChannels,
                                           constants.AudioSigned,
                                           constants.AudioBigEndian);

        this.dataLine = AudioSystem.getSourceDataLine(this.audioFormat);
    }


    public void open() throws LineUnavailableException {

        this.dataLine.open(this.audioFormat);

        this.dataLine.start();
    }

    public void write(byte[] data){
        this.dataLine.write(data,0,data.length);
    }

    public void stop(){

        this.dataLine.drain();
        this.dataLine.stop();
        this.dataLine.close();
    }


    public AudioFormat getAudioFormat() {
        return audioFormat;
    }
}
