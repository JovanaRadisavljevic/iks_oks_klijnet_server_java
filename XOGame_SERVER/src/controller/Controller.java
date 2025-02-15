/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import domen.Admin;
import domen.Potez;
import domen.User;
import forme.ServerskaForma;
import java.util.ArrayList;
import java.util.List;
import server.ObradaKlijentskihZahteva;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Jovana
 */
public class Controller {
    private static Controller instance;
    private List<ObradaKlijentskihZahteva> klijenti = new ArrayList<>();
    private List<Admin> admini = new ArrayList<>();
    private List<User> korisnici = new ArrayList<>();
    private List<User> ulogovani = new ArrayList<>();
    private String[][] polje=new String[3][3]; //sve vrednosti su null na pocetku
    private ServerskaForma sf;
    
    private Controller() {
        Admin a1 = new Admin("Milica", "Markovic", "milica.markovic", "milica123");
        Admin a2 = new Admin("Sasa", "Nikolic", "sasa.nikolic", "sasa123");
        Admin a3 = new Admin("Marko", "Jovanovic", "marko.jovanovic", "marko123");
        admini.add(a3);
        admini.add(a1);
        admini.add(a2);
        //korisnici
        User u1 = new User("Marija", "Markovic", "X", "marija.markovic", "marija123");
        User u2 = new User("Nikola", "Petrovic", "O", "nikola.petrovic", "nikola123");
        korisnici.add(u1);
        korisnici.add(u2);
    }

    public void setSf(ServerskaForma sf) {
        this.sf = sf;
    }
    
    public  static Controller getInstance(){
        if(instance==null)
            instance= new Controller();
        return instance;
    }

    public List<ObradaKlijentskihZahteva> getKlijenti() {
        return klijenti;
    }

    public Admin loginAdmin(Admin a) {
        for (Admin admin : admini) {
            if(admin.equals(a))
                return a;
        }
        return null;
    }

    public User loginKlijent(User u) {
        for (User user : korisnici) {
            if(user.equals(u)){
                
                if(ulogovani!=null && ulogovani.contains(u))
                    return null;
                ulogovani.add(user);
                return user;
            }
        }
        return null;
    }

    public Potez odigrajPotez(Potez potez) {
        
        if(polje[potez.getRed()][potez.getKolona()]==null){
            //prazno polje , smem da igram
            System.out.println(potez.getKorisnik().getOznakaIgraca());
            polje[potez.getRed()][potez.getKolona()]=potez.getKorisnik().getOznakaIgraca();
            System.out.println("upisana vrednost/oznaka: "+polje[potez.getRed()][potez.getKolona()]);
            //obavesti klijente
            for (ObradaKlijentskihZahteva nit : klijenti) {
                    ServerskiOdgovor so = new ServerskiOdgovor(potez, operacije.Operacije.POTEZ_DRUGOG);
                    nit.obavestiKorisnika(so);
            }
            for (int i = 0; i < polje.length; i++) {
                for (int j = 0; j < polje.length; j++) {
                    System.out.println(polje[i][j]+" ");
                }
                System.out.println();
            }
            return potez;
        }else{
            return null;
        }
    }

    public void proveraKraja() {
        String pobednik = pobednik();
        if(pobednik!=null){
            ServerskiOdgovor so = new ServerskiOdgovor(pobednik, operacije.Operacije.KRAJ_IGRE);
            for (ObradaKlijentskihZahteva nit : klijenti) {
                nit.obavestiKorisnika(so);
            }
        }
    }

    private String pobednik() {
        //da vidim da li je neko pobedio
             int n = polje.length;
             //GLAVNA DIJAGONALA
             boolean glavnaDijagonala=true;
             String pobednikGLDijagonala = polje[0][0]; 
             for (int i = 0; i < n-1; i++) {
                if(polje[i][i]==null || polje[i+1][i+1]==null || !polje[i][i].equals(polje[i+1][i+1])){
                    glavnaDijagonala=false;
                    break;//cim naidjem na prvi kojio ne valja izadji
                }
            }
             if(glavnaDijagonala){
                 sf.pobedio(pobednikGLDijagonala);
                 return pobednikGLDijagonala;
             }
             //sporedna dijagonala i -012 j-210
             boolean sporednaDijagonala = true;
             String pobednikSPDijagonala = polje[0][n-1];
             //samo mi trebaju 0 i 1 , jer za 2 nemam susedan elemnt za proveru
             for (int i = 0; i <= n-2; i++) {
                if(polje[i][n-i-1]==null || polje[i+1][n-i-2]==null || !polje[i][n-i-1].equals(polje[i+1][n-i-2])){
                    sporednaDijagonala=false;
                    break;
                }
            }
             if(sporednaDijagonala){
                 sf.pobedio(pobednikSPDijagonala);
                 return pobednikSPDijagonala;
             }
             //PROVERA REDOVA
            for (int i = 0; i < n; i++) {
                boolean redPobednik = true;
                String pobednikReda = polje[i][0];
                for (int j = 1; j < n; j++) {  // pocinjemo od 1 jer smo vec uzeli polje[i][0]
                    if (polje[i][j] == null || !polje[i][j].equals(pobednikReda)) {
                        redPobednik = false;
                        break;
                    }
                }
                if (redPobednik && pobednikReda != null) {
                    sf.pobedio(pobednikReda);
                    return pobednikReda; 
                }
            }

            // PROVERA KOLONA
            for (int j = 0; j < n; j++) {
                boolean kolonaPobednik = true;
                String pobednikKolone = polje[0][j];  // Prvi element u koloni
                for (int i = 1; i < n; i++) {  // Pocinjemo od 1 jer smo vec uzeli polje[0][j]
                    if (polje[i][j] == null || !polje[i][j].equals(pobednikKolone)) {
                        kolonaPobednik = false;
                        break;  
                    }
                }
                if (kolonaPobednik && pobednikKolone != null) {
                    sf.pobedio(pobednikKolone);
                    return pobednikKolone;  
                }
            }
            //AKO JE SVE POPUNJENO A NEMA POBEDNIKA ONDA NIKO NIJE POBEDIO
            boolean slobodnoMesto = false;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if(polje[i][j]==null){
                        slobodnoMesto=true;
                        break;
                    }
                }
            }
            if(!slobodnoMesto){
                sf.pobedio("nema pobednika");
                return "nema pobednika";
            }
            return null;
    }

    public void resetujIgru() {
        polje=new String[3][3];
        for (ObradaKlijentskihZahteva nit : klijenti) {
           ServerskiOdgovor so = new ServerskiOdgovor(null, operacije.Operacije.NOVA_IGRA);
           nit.obavestiKorisnika(so);
        }
    }

    public void pocetakIgre() {
        for (ObradaKlijentskihZahteva nit : klijenti) {
           ServerskiOdgovor so = new ServerskiOdgovor(null, operacije.Operacije.POCETAK_IGRE);
           nit.obavestiKorisnika(so);
        }
    }

    public List<User> getUlogovani() {
        return ulogovani;
    }

    
    
}
