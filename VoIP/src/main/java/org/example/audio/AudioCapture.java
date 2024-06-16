package org.example.audio;

import org.example.constants;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import static javax.sound.sampled.DataLine.Info;

public class AudioCapture {

    private final TargetDataLine targetLine;

    private final AudioFormat audioFormat;

    private final Info info;

    private byte[] data;


    public AudioCapture() throws LineUnavailableException{
        this.audioFormat = new AudioFormat(constants.AudioSampleRate,
                                           constants.AudioSampleSize,
                                           constants.AudioChannels,
                                           constants.AudioSigned,
                                           constants.AudioBigEndian);

        this.info = new Info(TargetDataLine.class, this.audioFormat);

        this.targetLine = (TargetDataLine)  AudioSystem.getLine(this.info);

        this.data = new byte[(int) this.audioFormat.getSampleRate()];
    }


    public void open() throws LineUnavailableException{
        this.targetLine.open(this.audioFormat);

        this.targetLine.start();
    }

    public byte[] getRead(){
        this.targetLine.read(data,0, data.length);

        return data;
    }

    public void close(){

        this.targetLine.drain();
        this.targetLine.stop();
        this.targetLine.close();
    }
}


