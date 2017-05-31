package com.qaamishra.jcraft.batch;

/**
 * Created by Ashutosh on 31-05-2017.
 */

import com.jcraft.jsch.*;
import com.qaamishra.jcraft.session.JcraftSession;

import java.io.IOException;
import java.io.InputStream;


public class WindowsBatchRunner {


    public String runWindowsBat(String host,String uname,String pwd) throws JSchException, IOException, SftpException, InterruptedException {

        String output = "";

        try {
            Session session = JcraftSession.getJcraftSession(host,uname,pwd);
            session.connect();

            if (session.isConnected()) {
                System.out.println("shell is connected");
            }


            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand("cmd.exe /c c:\\windows\\trigger.bat\n");


            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);

            InputStream in = channel.getInputStream();
            channel.connect();
            if (channel.isConnected()) {
                System.out.println("channel is connected");
            }
            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0)
                        break;
                    String local = new String(tmp, 0, i);

                    output = output + local;

                }
                if (channel.isClosed()) {
                    System.out.println("exit-status: " + channel.getExitStatus());
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ee) {
                }
            }
            channel.disconnect();
            session.disconnect();
            System.out.println("DONE");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //output=output.substring(output.indexOf("{"),output.lastIndexOf("}"));

        return output;
    }
}