package beans;
// Generated Jan 21, 2018 2:00:58 AM by Hibernate Tools 4.3.1



/**
 * Korisnik generated by hbm2java
 */
public class Korisnik  implements java.io.Serializable {


     private Integer idKorisnik;
     private String username;
     private String lozinka;
     private String statusKorisnik;
     private String ime;
     private String prezime;
     private String kontakt;
     private String email;
     private Integer blokiran;
     private String poruka;

    public Korisnik() {
    }

    public Korisnik(String username, String lozinka, String statusKorisnik, String ime, String prezime, String kontakt, String email, Integer blokiran) {
       this.username = username;
       this.lozinka = lozinka;
       this.statusKorisnik = statusKorisnik;
       this.ime = ime;
       this.prezime = prezime;
       this.kontakt = kontakt;
       this.email = email;
       this.blokiran = blokiran;
    }
   
    public Integer getIdKorisnik() {
        return this.idKorisnik;
    }
    
    public void setIdKorisnik(Integer idKorisnik) {
        this.idKorisnik = idKorisnik;
    }
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    public String getLozinka() {
        return this.lozinka;
    }
    
    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }
    public String getStatusKorisnik() {
        return this.statusKorisnik;
    }
    
    public void setStatusKorisnik(String statusKorisnik) {
        this.statusKorisnik = statusKorisnik;
    }
    public String getIme() {
        return this.ime;
    }
    
    public void setIme(String ime) {
        this.ime = ime;
    }
    public String getPrezime() {
        return this.prezime;
    }
    
    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }
    public String getKontakt() {
        return this.kontakt;
    }
    
    public void setKontakt(String kontakt) {
        this.kontakt = kontakt;
    }
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    public Integer getBlokiran() {
        return this.blokiran;
    }
    
    public void setBlokiran(Integer blokiran) {
        this.blokiran = blokiran;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }
    
    




}

