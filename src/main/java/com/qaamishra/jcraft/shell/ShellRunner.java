package com.qaamishra.jcraft.shell;

//Execute Shell script and get the "echo " output of  shell script

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.qaamishra.jcraft.session.JcraftSession;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ShellRunner {  

	public boolean runBatchScriptInLinux(String host,String uname,String pwd) throws JSchException, IOException, SftpException, InterruptedException{

        boolean flagCompletedBatch=false;
        boolean flagAnErrorOccurred=false;
        boolean smokeTestResult=false;



        String sudoPasswordLinux="somePassword";

        Session session= JcraftSession.getJcraftSession(host,uname,host);
        session.connect();

        /* Sudo Channel */
        ChannelShell shell = (ChannelShell) session.openChannel("shell");
        shell.connect();
        shell.setInputStream(null);
        shell.setOutputStream(null);
        InputStream io = shell.getInputStream();
        OutputStream output = shell.getOutputStream();

        String sudoPassword=sudoPasswordLinux;
        System.out.println("sudo password " + sudoPassword);
        output.write(("echo " + sudoPassword + " | sudo -S su - batchmgr && sudo -S su - batchmgr\n").getBytes());
        output.flush();
        output.write(("cd /local/app/shellScriptLocation/\n").getBytes());
        output.flush();
        output.write(("sh run.sh\n").getBytes());
        output.flush();

        byte[] bytes = new byte[1024];

            while(true){
                while(io.available() > 0){
                    final int i = io.read(bytes, 0, 1024);
                    if(i < 0)
                        break;
                    String str = new String(bytes, 0 , i);
                    System.out.println(str);

                    //Below two If's with string contains are absorbed from batch script file.
                    //If any future change in applyBatch script happens we need to change the below if's according to disconnect the shell.
                    if(str.contains("Batch Executed Successfully")){
                        flagCompletedBatch=true;
                        shell.disconnect();
                    }//if One
                    if(str.contains("An error occurred running batch")){
                        flagAnErrorOccurred=false;
                        shell.disconnect();
                    }//If two
                }//while
                if(shell.isClosed()){
                    break;
                }
            }

        shell.disconnect();
        session.disconnect();

        if(flagCompletedBatch && !flagAnErrorOccurred){

            smokeTestResult=true;
            System.out.println(" Test Passed");
        }
        else{

            System.out.println(" Test failed");
        }

        return smokeTestResult;
    }
}
