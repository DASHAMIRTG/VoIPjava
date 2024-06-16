package org.example.network;

import org.example.constants;
import org.example.audio.AudioPlayback;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import javax.sound.sampled.LineUnavailableException;
public class VoIPPlayback implements  Runnable{

    private final AudioPlayback playback;

    private final MulticastSocket socket;

    private final InetAddress group;

    private final InetAddress host;

    private String packet_address;

    private String host_address;

    private DatagramPacket packet;

    private byte[] data;

    private boolean reading;


    public VoIPPlayback(InetAddress multicast , InetAddress host) throws LineUnavailableException , IOException{

        this.playback = new AudioPlayback();

        this.group = multicast;

        this.host= host;

        this.host_address = this.toString();

        this.host_address = this.host_address.substring(this.host_address.indexOf('/')+1);

        this.socket = new MulticastSocket(constants.VoIPPort);
        this.socket.setReuseAddress(true);
        this.socket.joinGroup(this.group);

        this.data = new byte[(int) this.playback.getAudioFormat().getSampleRate()];
    }
    @Override
    public void run(){
        this.reading = true;

        try {
            this.playback.open();

            while(this.reading){
                try {

                    this.packet = new DatagramPacket(this.data,this.data.length);

                    this.socket.receive(this.packet);

                    this.packet_address = this.packet_address.substring(this.packet_address.indexOf('/')+ 1);

                    if (!this.packet.equals(this.host_address)){
                        this.playback.write(data);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            this.playback.stop();

            this.socket.leaveGroup(this.group);
            this.socket.close();
        }catch (LineUnavailableException | IOException e){
            e.printStackTrace();
        }
    }
    public void stop(){
        this.reading = false;
    }
}
