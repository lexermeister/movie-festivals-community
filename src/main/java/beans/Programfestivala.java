package beans;
// Generated Jan 21, 2018 2:00:58 AM by Hibernate Tools 4.3.1



/**
 * Programfestivala generated by hbm2java
 */
public class Programfestivala  implements java.io.Serializable {


     private Integer idProgram;
     private String nazivPrograma;
     private Integer idFestival;
     private Integer idProjekcija;
     private Integer idFilm;

    public Programfestivala() {
    }

    public Programfestivala(String nazivPrograma, Integer idFestival, Integer idProjekcija, Integer idFilm) {
       this.nazivPrograma = nazivPrograma;
       this.idFestival = idFestival;
       this.idProjekcija = idProjekcija;
       this.idFilm = idFilm;
    }
   
    public Integer getIdProgram() {
        return this.idProgram;
    }
    
    public void setIdProgram(Integer idProgram) {
        this.idProgram = idProgram;
    }
    public String getNazivPrograma() {
        return this.nazivPrograma;
    }
    
    public void setNazivPrograma(String nazivPrograma) {
        this.nazivPrograma = nazivPrograma;
    }
    public Integer getIdFestival() {
        return this.idFestival;
    }
    
    public void setIdFestival(Integer idFestival) {
        this.idFestival = idFestival;
    }
    public Integer getIdProjekcija() {
        return this.idProjekcija;
    }
    
    public void setIdProjekcija(Integer idProjekcija) {
        this.idProjekcija = idProjekcija;
    }
    public Integer getIdFilm() {
        return this.idFilm;
    }
    
    public void setIdFilm(Integer idFilm) {
        this.idFilm = idFilm;
    }




}


