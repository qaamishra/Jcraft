package com.qaamishra.jcraft.sendFileToLinux;

import com.jcraft.jsch.*;
import com.qaamishra.jcraft.session.JcraftSession;


import java.io.IOException;


public class PushFileToLinux {


    /**
     * @param host
     * @param uname
     * @param pwd
     * @param fileToPush       Providing ony the file name will pick it up from ./
     * @param linuxDestination provide complete path
     * @throws IOException
     * @throws JSchException
     * @throws SftpException Note : For this program to work you need to make the changes in the sshd_config as "Subsystem sftp   internal-sftp"
     *                       Then stop the sshd service and start it again
     *                       service sshd stop
     *                       ps -ef |grep sshd
     *                       service sshd start
     */
    public void sendFileToLinuxDevBoxOnly(String host, String uname, String pwd, String fileToPush, String linuxDestination) throws IOException, JSchException, SftpException {



        Session session = JcraftSession.getJcraftSession(host, uname, pwd);
        session.connect();

        Channel channel = session.openChannel("sftp");
        channel.connect();

        String copyFrom = fileToPush;
        String copyTo = linuxDestination;

        ChannelSftp sftpChannel = (ChannelSftp) channel;
        sftpChannel.put(copyFrom, copyTo);

        System.out.println("Exit Status for file push Expected is  minus 1  : Actual -->" + sftpChannel.getExitStatus());
        System.out.println("File "+copyFrom+" sent and placed here "+copyTo);

        sftpChannel.disconnect();
        channel.disconnect();
        session.disconnect();


    }
}

