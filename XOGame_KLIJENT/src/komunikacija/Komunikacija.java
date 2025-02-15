/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package komunikacija;

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
public class Komunikacija extends Thread {
    private Socket s;
    private static Komunikacija instance;
    private Komunikacija(){
        try {
            s=new Socket("localhost", 9000);
        } catch (IOException ex) {
            Logger.getLogger(Komunikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static Komunikacija getInstance(){
        if(instance==null)
            instance=new Komunikacija();
        return instance;
    }

    @Override
    public void run() {
        while (true) {            
            ServerskiOdgovor so = primiOdgovor();
            switch (so.getOperacija()) {
                case operacije.Operacije.LOGIN:
                    kontroler.Kontroler.getInstance().odgovorLOGIN((User)so.getOdgovor());
                    break;
                case operacije.Operacije.ODIGRAJ_POTEZ:
                    kontroler.Kontroler.getInstance().odgovorPOTEZ((Potez)so.getOdgovor());
                    break;
                case operacije.Operacije.KRAJ_IGRE:
                    kontroler.Kontroler.getInstance().krajIgre((String)so.getOdgovor());
                    break;
                case operacije.Operacije.POTEZ_DRUGOG:
                    kontroler.Kontroler.getInstance().odgovorPOTEZ_DRUGOG((Potez)so.getOdgovor());
                    break;
                case operacije.Operacije.NOVA_IGRA:
                    kontroler.Kontroler.getInstance().odgovorNOVA_IGRA();
                    break;
                case operacije.Operacije.POCETAK_IGRE:
                    kontroler.Kontroler.getInstance().odgovorPOCETAK_IGRE();
                    break;
                default:
                    return;
            }
        }
    }
    
    public void posaljiZahtev(KlijentskiZahtev kz){
        try {
            ObjectOutputStream oos=  new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(kz);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(Komunikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public ServerskiOdgovor primiOdgovor(){
        try {
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            return (ServerskiOdgovor) ois.readObject();
        } catch (IOException ex) {
            Logger.getLogger(Komunikacija.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Komunikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
