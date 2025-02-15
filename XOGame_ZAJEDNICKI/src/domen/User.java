/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domen;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Jovana
 */
public class User implements Serializable{
    private String ime;
    private String prezime;
    private String oznakaIgraca;
    private String korisnickoIme;
    private String loznika;

    public User() {
    }

    public User(String ime, String prezime, String oznakaIgraca, String korisnickoIme, String loznika) {
        this.ime = ime;
        this.prezime = prezime;
        this.oznakaIgraca = oznakaIgraca;
        this.korisnickoIme = korisnickoIme;
        this.loznika = loznika;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.korisnickoIme, other.korisnickoIme)) {
            return false;
        }
        return Objects.equals(this.loznika, other.loznika);
    }


    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getOznakaIgraca() {
        return oznakaIgraca;
    }

    public void setOznakaIgraca(String oznakaIgraca) {
        this.oznakaIgraca = oznakaIgraca;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLoznika() {
        return loznika;
    }

    public void setLoznika(String loznika) {
        this.loznika = loznika;
    }
    
}
