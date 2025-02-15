/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jovana
 */
public class PokreniServer extends Thread{
    private ServerSocket serverskiSoket;
    

    @Override
    public void run() {
        try {
            serverskiSoket= new ServerSocket(9000);
            while (true) {
                Socket s = serverskiSoket.accept();
                ObradaKlijentskihZahteva nit = new ObradaKlijentskihZahteva(s);
                controller.Controller.getInstance().getKlijenti().add(nit);
                nit.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(PokreniServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
