package com.example.yaseenmunawwer.followme;

import android.app.Activity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Yaseen Munawwer on 16-05-2017.
 */

public class client extends Activity {
    private static final int SELECT_PICTURE = 1;




    public void connect(String selectedFile) {
        Socket sock;
        try {

            sock = new Socket("192.168.0.2", 8000);
            System.out.println("Connecting...");

            // sendfile
            File myFile = new File(selectedFile);
            byte[] mybytearray = new byte[(int) myFile.length()];
            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(mybytearray, 0, mybytearray.length);
            OutputStream os = sock.getOutputStream();
            System.out.println("Sending...");
            os.write(mybytearray, 0, mybytearray.length);
            os.flush();

            sock.close();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}