package com.qaamishra.jcraft.filefetcher;

//Copies file from Linux to Windows

import com.jcraft.jsch.*;
import com.qaamishra.jcraft.session.JcraftSession;

import java.io.IOException;

public class FetchFileFromLinux {

    public void getFileFromLinux(String host, String uname, String pwd, String fileNameWithLoc) {


        Session session = null;

        try {
            session = JcraftSession.getJcraftSession(host, uname, pwd);

            session.connect();
            Channel channel = session.openChannel("sftp");
            channel.connect();


          //String copyFrom = "/local/home/test_qa/outputTeamTest.log";
            String copyFrom = fileNameWithLoc;
            String copyTo = "./";

            ChannelSftp sftpChannel = (ChannelSftp) channel;
            sftpChannel.get(copyFrom, copyTo);
            sftpChannel.exit();


        } catch (JSchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        } finally {

            session.disconnect();
        }


    }
}
