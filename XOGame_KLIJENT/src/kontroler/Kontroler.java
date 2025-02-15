/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroler;

import domen.Potez;
import domen.User;
import forme.KorisnickaForma;
import forme.LoginForma;

/**
 *
 * @author Jovana
 */
public class Kontroler {
     private static Kontroler instance;
     private LoginForma lf;
     private KorisnickaForma kf;
     
     private Kontroler(){
     }
     public  static Kontroler getInstance(){
        if(instance==null)
            instance= new Kontroler();
        return instance;
    }

    public void setLf(LoginForma lf) {
        this.lf = lf;
    }

    public void setKf(KorisnickaForma kf) {
        this.kf = kf;
    }
     

    public void odgovorLOGIN(User user) {
        lf.odgovorLOGIN(user);
    }

    public void odgovorPOTEZ(Potez potez) {
        kf.odgovorPOTEZ(potez);
    }

    public void odgovorPOTEZ_DRUGOG(Potez potez) {
        kf.odgovorPOTEZ_DRUGOG(potez);
    }

    public void krajIgre(String poruka) {
        kf.krajIgre(poruka);
    }

    public void odgovorNOVA_IGRA() {
        kf.novaIgra();
    }

    public void odgovorPOCETAK_IGRE() {
        kf.pocetakIgre();
    }
     
}
