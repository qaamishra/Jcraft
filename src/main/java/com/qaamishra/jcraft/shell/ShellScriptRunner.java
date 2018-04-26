package com.qaamishra.jcraft.shell;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.qaamishra.jcraft.session.JcraftSession;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class ShellScriptRunner {

    /**
     * @param host        : Ip address of the host
     * @param uname       : Username to login
     * @param pwd         : Password to login
     * @param shellScript : Full name of the shell script to execute with extension
     * @return CompleteOutput : All the console output of linux
     * @throws JSchException
     * @throws IOException
     * @throws SftpException
     * @throws InterruptedException
     */
    public String runBatchScriptInLinux(String host, String uname, String pwd,String locationOfShellScript, String shellScript) throws JSchException, IOException, InterruptedException {


        String CompleteOutput = "";
        Session session = JcraftSession.getJcraftSession(host, uname, pwd);
        session.connect();

        ChannelShell shell = (ChannelShell) session.openChannel("shell");
        shell.connect();
        shell.setInputStream(null);
        shell.setOutputStream(null);
        InputStream io = shell.getInputStream();
        OutputStream output = shell.getOutputStream();

        //String shellCommand = "sh " + shellScript.trim();
        String shellCommand = "./" + shellScript.trim();

        output.write(("sesudo root\n").getBytes());
        output.flush();

        waitForStreamToFetch(2000);
        output.write((pwd.trim() + "\n").getBytes());
        output.flush();

        waitForStreamToFetch(2000);

        String locOfScript="cd "+locationOfShellScript;
        output.write((locOfScript.trim()+"\n").getBytes());
        output.flush();

        waitForStreamToFetch(2000);
        output.write((shellCommand + "\n").getBytes());
        output.flush();

        byte[] bytes = new byte[1024];

        while (true) {
            while (io.available() > 0) {
                waitForStreamToFetch(2000);
                int i = io.read(bytes, 0, 1024);

                if (i < 0) break;
                waitForStreamToFetch(2000);
                String batchOutput = new String(bytes, 0, i);
                waitForStreamToFetch(2000);
                CompleteOutput = CompleteOutput + batchOutput;

            }
            if ((io.available() > 0) == false) {
                shell.disconnect();
                break;
            }
        }

        session.disconnect();

        return CompleteOutput;
    }

    private void waitForStreamToFetch(int ms) throws InterruptedException {
        Thread.sleep(ms);
    }
}
