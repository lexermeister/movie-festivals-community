package beans;
// Generated Jan 21, 2018 2:00:58 AM by Hibernate Tools 4.3.1

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Projekcija generated by hbm2java
 */
public class Projekcija implements java.io.Serializable {

    private Integer idProjekcija;
    private String mesto;
    private String satnica;
    private String sala;
    private Date datum;
    private Integer idFilm;
    private String datumPocetkaLepo = "";
    private String nazivFilma = "";
    private String nazivFestivala = "";
    private Integer cena;
    private boolean editable = false;
    
    public Projekcija() {
    }

    public Projekcija(String mesto, String satnica, String sala, Date datum, Integer idFilm) {
        this.mesto = mesto;
        this.satnica = satnica;
        this.sala = sala;
        this.datum = datum;
        this.idFilm = idFilm;
    }

    public Integer getIdProjekcija() {
        return this.idProjekcija;
    }

    public void setIdProjekcija(Integer idProjekcija) {
        this.idProjekcija = idProjekcija;
    }

    public String getMesto() {
        return this.mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    public String getSatnica() {
        return this.satnica;
    }

    public void setSatnica(String satnica) {
        this.satnica = satnica;
    }

    public String getSala() {
        return this.sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public Date getDatum() {
        return this.datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Integer getIdFilm() {
        return this.idFilm;
    }

    public void setIdFilm(Integer idFilm) {
        this.idFilm = idFilm;
    }

    public String getDatumLepo() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy HH:mm");
        this.datumPocetkaLepo = sdf.format(datum);
        return this.datumPocetkaLepo;
    }

    public void setDatumLepo(String datumPocetkaLepo) {
        this.datumPocetkaLepo = datumPocetkaLepo;
    }

    public String getDatumPocetkaLepo() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
        this.datumPocetkaLepo = sdf.format(datum);
        return this.datumPocetkaLepo;
    }

    public void setDatumPocetkaLepo(String datumPocetkaLepo) {
        this.datumPocetkaLepo = datumPocetkaLepo;
    }

    public String getNazivFilma() {
        return nazivFilma;
    }

    public void setNazivFilma(String nazivFilma) {
        this.nazivFilma = nazivFilma;
    }

    public String getNazivFestivala() {
        return nazivFestivala;
    }

    public void setNazivFestivala(String nazivFestivala) {
        this.nazivFestivala = nazivFestivala;
    }

    public Integer getCena() {
        return cena;
    }

    public void setCena(Integer cena) {
        this.cena = cena;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    
    

    
    
    @Override
    public String toString() {
        return "Film: " + nazivFilma + " | Festival: " + nazivFestivala + " | Datum: " + getDatumLepo() + "" + " | Mesto: " + mesto;
    }
    
    
    
    

}