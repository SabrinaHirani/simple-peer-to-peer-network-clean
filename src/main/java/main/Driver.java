package main;

import node.*;
import node.rpc.*;
import graphics.*;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

import com.github.weisj.darklaf.*;
import com.github.weisj.darklaf.theme.*;

public class Driver 
{

    public static Node THIS = null;
    
    public static void main( String[] args )
    {

        try {
            THIS = new Node();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Client.connect(9000);

        SwingUtilities.invokeLater(() -> {
            LafManager.install(new DarculaTheme());
            new App();
        });
        
    }
}
