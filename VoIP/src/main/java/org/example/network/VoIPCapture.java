package org.example.network;

import org.example.constants;
import org.example.audio.AudioCapture;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import javax.sound.sampled.LineUnavailableException;

public class VoIPCapture implements Runnable {

    private final InetAddress host ;

    private final DatagramSocket socket ;

    private final AudioCapture audioCapture ;

    private DatagramPacket packet ;

    private byte[] data;

    private boolean reading;

    public VoIPCapture(InetAddress host) throws LineUnavailableException , SocketException{

        this.host = host;

        this.audioCapture = new AudioCapture();

        this.socket = new DatagramSocket();
    }

    @Override
    public void run() {
        this.reading = true;

        try{
            this.audioCapture.open();

            while(this.reading){
                this.data = this.audioCapture.getRead();

                this.packet = new DatagramPacket(this.data, this.data.length, this.host ,constants.VoIPPort);

                try{
                    this.socket.send(this.packet);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            this.audioCapture.close();
        }catch (LineUnavailableException e){
            e.printStackTrace();
        }
        this.socket.close();
    }
    public void stop(){
        this.reading = false;
    }
}
