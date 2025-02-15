/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import domen.Potez;
import domen.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import transfer.KlijentskiZahtev;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Jovana
 */
public class ObradaKlijentskihZahteva extends Thread{
    private Socket s;
    private User korisnik=null;
    
    public ObradaKlijentskihZahteva(Socket s) {
        this.s = s;
    }
    
    @Override
    public void run() {
        while (true) {            
            KlijentskiZahtev kz = primiZahtev();
            ServerskiOdgovor so = new ServerskiOdgovor();
            switch (kz.getOperacija()) {
                case operacije.Operacije.LOGIN:
                    korisnik = controller.Controller.getInstance().loginKlijent((User)kz.getParametar());
                    so.setOdgovor(korisnik);
                    System.out.println(korisnik);
                    so.setOperacija(operacije.Operacije.LOGIN);
                    posaljiOdgovor(so);
                    if(controller.Controller.getInstance().getUlogovani().size()==2)
                        controller.Controller.getInstance().pocetakIgre();
                    break;
                case operacije.Operacije.ODIGRAJ_POTEZ:
                    Potez odgovor = controller.Controller.getInstance().odigrajPotez((Potez)kz.getParametar());
                    so.setOdgovor(odgovor);
                    so.setOperacija(operacije.Operacije.ODIGRAJ_POTEZ);
                    controller.Controller.getInstance().proveraKraja();
                    posaljiOdgovor(so);
                    break;
                default:
                    throw new AssertionError();
            }
        }
    }

    private KlijentskiZahtev primiZahtev() {
        try {
            ObjectInputStream ois  = new ObjectInputStream(s.getInputStream());
            return (KlijentskiZahtev) ois.readObject();
        } catch (IOException ex) {
            Logger.getLogger(ObradaKlijentskihZahteva.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ObradaKlijentskihZahteva.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }

    private void posaljiOdgovor(ServerskiOdgovor so) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(so);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ObradaKlijentskihZahteva.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void obavestiKorisnika(ServerskiOdgovor so) {
        posaljiOdgovor(so);
    }

    public User getKorisnik() {
        return korisnik;
    }
    
}
