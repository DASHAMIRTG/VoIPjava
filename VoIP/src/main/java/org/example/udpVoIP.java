package org.example;

import org.example.network.VoIPPlayback;
import org.example.network.VoIPCapture;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import  javax.sound.sampled.LineUnavailableException;
public class udpVoIP {

    private final  InetAddress host;

    private final InetAddress address;

    private final VoIPPlayback voIPCapture;

    private final VoIPPlayback voIPPlayback;

    private final Thread capture;

    private final Thread playback;

    public udpVoIP(String host , String address) throws  UnknownHostException , SocketException , LineUnavailableException, IOException{

        this.host = InetAddress.getByName(host);
        this.address = InetAddress.getByName(address);

        this.voIPCapture = new VoIPPlayback(this.host, this.address);
        this.voIPPlayback = new VoIPPlayback(this.host ,this.address);

        this.playback = new Thread(this.voIPPlayback);
        this.capture = new  Thread(this.voIPCapture);
    }

    public void start(){
        this.playback.start();
        this.capture.start();
    }

    public void stop(){
        this.voIPPlayback.stop();
        this.voIPCapture.stop();
    }
}
