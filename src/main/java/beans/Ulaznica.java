package beans;
// Generated Jan 21, 2018 2:00:58 AM by Hibernate Tools 4.3.1



/**
 * Ulaznica generated by hbm2java
 */
public class Ulaznica  implements java.io.Serializable {


     private Integer idUlaznica;
     private String statusUlaznice;
     private Integer idKorisnik;
     private Integer idProjekcija;

    public Ulaznica() {
    }

    public Ulaznica(String statusUlaznice, Integer idKorisnik, Integer idProjekcija) {
       this.statusUlaznice = statusUlaznice;
       this.idKorisnik = idKorisnik;
       this.idProjekcija = idProjekcija;
    }
   
    public Integer getIdUlaznica() {
        return this.idUlaznica;
    }
    
    public void setIdUlaznica(Integer idUlaznica) {
        this.idUlaznica = idUlaznica;
    }
    public String getStatusUlaznice() {
        return this.statusUlaznice;
    }
    
    public void setStatusUlaznice(String statusUlaznice) {
        this.statusUlaznice = statusUlaznice;
    }
    public Integer getIdKorisnik() {
        return this.idKorisnik;
    }
    
    public void setIdKorisnik(Integer idKorisnik) {
        this.idKorisnik = idKorisnik;
    }
    public Integer getIdProjekcija() {
        return this.idProjekcija;
    }
    
    public void setIdProjekcija(Integer idProjekcija) {
        this.idProjekcija = idProjekcija;
    }




}


