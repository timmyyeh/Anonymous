package com.anonymous.anonymous.Discussion;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.Socket;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;


public class FileClient {

    private Socket s;
    private String host;
    private int port;
    private String filename;
    private String filepath;

    public FileClient(final String host, final int port, final String filepath, String filename) {

        this.host = host;
        this.port = port;
        this.filepath = filepath;
        this.filename = filename;
    }

    public void sendFile(final String file) {


        new Thread() {
            @Override
            public void run() {
                try {
                    s = new Socket(host, port);
                    DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                    FileInputStream fis = new FileInputStream(file);

                    byte[] buffer = new byte[(int) fis.getChannel().size()];

                    while (fis.read(buffer) > 0) {
                        dos.write(buffer);
                    }

                    fis.close();
                    dos.close();
                    s.close();

                } catch (Exception e) {
                    Log.i("tag", "WrongWrong");
                }
            }
        }.start();

    }

    public void downloadFile()  {

        new Thread() {
            @Override
            public void run() {
                try {
                    //the server that I am using is personal server.
                    URL website = new URL(host + "/"+filename);
                    ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                    FileOutputStream fos = new FileOutputStream(filepath+"/"+filename);
                    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                } catch (Exception e) {
                    Log.e("file", e.getMessage());
                }
            }
        }.start();
    }
}
