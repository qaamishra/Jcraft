package com.qaamishra.jcraft.session;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class JcraftSession {

    public static Session getJcraftSession(String host, String userName, String password) throws JSchException, IOException {


        JSch conn = new JSch();

        Session session = conn.getSession(userName, host, 22);
        session.setPassword(password);
        session.setTimeout(300000);

        Properties properties = new Properties();
        properties.put("StrictHostKeyChecking", "no");
        properties.put("PreferredAuthentications", "publickey,keyboard-interactive,password");

        session.setConfig(properties);

        return session;
    }

}

