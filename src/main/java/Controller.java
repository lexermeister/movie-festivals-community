
import beans.Festival;
import beans.Film;
import beans.Komentar;
import beans.Korisnik;
import beans.Mestoodrzavanja;
import beans.Ocena;
import beans.Programfestivala;
import beans.Projekcija;
import beans.Rezervacija;
import beans.Sala;
import beans.Slikafilm;
import beans.Ulaznica;
import beans.Zahtevregistracija;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import helpers.FestivalHelper;
import helpers.FilmHelper;
import helpers.KomentarHelper;
import helpers.KorisnikHelper;
import helpers.MestoOdrzavanjaHelper;
import helpers.OcenaHelper;
import helpers.ProgramHelper;
import helpers.ProjekcijaHelper;
import helpers.RezervacijaHelper;
import helpers.SalaHelper;
import helpers.SlikaFilmHelper;
import helpers.UlaznicaHelper;
import helpers.ZahtevRegistracijaHelper;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.jboss.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DefaultUploadedFile;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Aleksa
 */
@ManagedBean
@SessionScoped
public class Controller {

    Logger logger = Logger.getLogger("logger");

    private static final String pdfDest = "C:\\Users\\Aleksa\\Documents\\NetBeansProjects\\PIAFestivali\\web\\resources\\program.pdf";
    private static final String filmFolder = "C:\\Users\\Aleksa\\Documents\\NetBeansProjects\\PIAFestivali\\web\\resources\\films\\";
    private static final String podaciFestivaliDest = "C:\\Users\\Aleksa\\Documents\\NetBeansProjects\\PIAFestivali\\web\\resources\\podaci.json";
    private static final String podaciFilmoviDest = "C:\\Users\\Aleksa\\Documents\\NetBeansProjects\\PIAFestivali\\web\\resources\\podaciFilmovi.json";
    private static final String API_KEY = "c891736d";
    
    private Korisnik korisnik = new Korisnik();
    private Korisnik noviKorisnik = new Korisnik();
    private String username = "", password = "", novaLozinka = "", porukaLogin = "", porukaPromena = "";
    private KorisnikHelper korisnikHelper = new KorisnikHelper();
    private boolean isRegKorisnik = false, isGost = true, isAdmin = false, isProdavac = false;

    // Admin
    private int maxRezervacija = 4;
    private boolean skip;
    private boolean izmeniActive = false;
    JSONParser parser=new JSONParser();
    private List<Festival> listaFestivala = new ArrayList<Festival>();
    private List<Film> listaFilmova = new ArrayList<Film>();

    // Zahtev za registraciju
    private Zahtevregistracija zr = new Zahtevregistracija();
    private ZahtevRegistracijaHelper zrHelper = new ZahtevRegistracijaHelper();
    private String lozinkaPotvrda = "", porukaZahtev = "";
    private List<Zahtevregistracija> pristigliZahtevi = new ArrayList<Zahtevregistracija>();

    // Festivala
    private String porukaPretraga = "";
    private String filterNaziv = "", filterOriginalniNaziv = "";
    private Date filterDatumOd, filterDatumDo;
    private boolean pretragaUspesna = false, postavljenOriginalanNaziv = false;
    private List<Festival> festivaliResult = new ArrayList<>();
    private FestivalHelper festivalHelper = new FestivalHelper();
    private MestoOdrzavanjaHelper mestoHelper = new MestoOdrzavanjaHelper();
    private Festival festivalResult = null;
    private String mestaOdrzavanja = "";
    private SalaHelper salaHelper = new SalaHelper();

    // Film
    private Film filmResult = null;
    private FilmHelper filmHelper = new FilmHelper();
    private List<Festival> festivaliFilm = new ArrayList<Festival>();
    private StreamedContent slikaFilm, slikaTest;
    private List<Slikafilm> galerijaFilm = new ArrayList<Slikafilm>();
    private SlikaFilmHelper slikaHelper = new SlikaFilmHelper();
    private List<String> listContent = new ArrayList<String>();
    private int trFilm, trFestival;
    private String nazivFilmaZaOcenjivanje = "";
    private Film filmZaOcenjivanje = new Film();
    private String[] moguceOcene = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private String ocena = "", komentar = "";
    private OcenaHelper ocenaHelper = new OcenaHelper();
    private KomentarHelper komentarHelper = new KomentarHelper();
    private String porukaOcenjivanje = "";
    private List<Komentar> komentari = new ArrayList<Komentar>();
    private int ocenaInt;

    // Projekcija
    private Projekcija projekcijaResult = null;
    private ProjekcijaHelper projekcijaHelper = new ProjekcijaHelper();
    private List<Projekcija> projekcije = new ArrayList<Projekcija>();
    private List<Projekcija> projekcijeResult = new ArrayList<Projekcija>();
    private Projekcija projekcijaSelected;
    private int projekcijaId;
    private List<Projekcija> ostvareneProjekcije = new ArrayList<Projekcija>();
    private List<Projekcija> sveProjekcije = new ArrayList<Projekcija>();
    private Projekcija izabranaProjekcija = null;
    private String brojUlaznicaSlobodnaProdaja = "";
    private String porukaProdaja = "";
    private int izabranaProjekcijaId;
    private String novaSatnica;

    // Rezervacija
    private boolean rezervacijaEnabled;
    private int brojRezervisano;
    private Rezervacija rezervacija = null;
    private String porukaRezervacija = "";
    private RezervacijaHelper rezervacijaHelper = new RezervacijaHelper();
    private List<Rezervacija> aktuelneRezervacije = new ArrayList<Rezervacija>();
    private List<Rezervacija> otkazaneRezervacije = new ArrayList<Rezervacija>();
    private List<Rezervacija> ostvareneRezervacije = new ArrayList<Rezervacija>();
    private String porukaPregledRezervacija = "";
    private String dialogCena = "", dialogIme = "leksa", dialogPrezime = "", dialogFestival = "", dialogKod = "", dialogFilm = "";
    private Rezervacija rezervacijaZaOdobravanje = null;
    private String filterKod = "", filterIme = "", filterPrezime = "";
    private boolean pretragaEnabled = false;

    // Ulaznica
    private UlaznicaHelper ulaznicaHelper = new UlaznicaHelper();

    // Unos
    private Festival noviFestival = new Festival();
    private Projekcija novaProjekcija = new Projekcija();
    private List<Projekcija> noveProjekcije = new ArrayList<Projekcija>();
    private String porukaDodavanjeProjekcije = "";
    private String ispisProjekcija = "";
    private ProgramHelper programHelper = new ProgramHelper();
    private String porukaDodavanje = "";
    private Film noviFilm = new Film();
    private UploadedFile slikaUploaded;
    private List<UploadedFile> slikeZaGaleriju = new ArrayList<UploadedFile>();
    private String porukaParsiranje = "";
    private String porukaDodavanjeProjekcije1 = "";
    
    // PDF 
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    
    // IMDB
    private String imdbParametarNaziv = "";
    private String imdbParametarGodina = "";
    private boolean pretragaIMDBUspesna = false;
    private Film imdbFilm = new Film();
    private String porukaImdbPretraga = "";

    public enum statusRezervacije {
        PODNETA, OTKAZANA, ISTEKLA
    };

    // Korisnik
    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public Korisnik getNoviKorisnik() {
        return noviKorisnik;
    }

    public void setNoviKorisnik(Korisnik noviKorisnik) {
        this.noviKorisnik = noviKorisnik;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public KorisnikHelper getKorisnikHelper() {
        return korisnikHelper;
    }

    public void setKorisnikHelper(KorisnikHelper korisnikHelper) {
        this.korisnikHelper = korisnikHelper;
    }

    public String getPorukaLogin() {
        return porukaLogin;
    }

    public void setPorukaLogin(String porukaLogin) {
        this.porukaLogin = porukaLogin;
    }

    public String getNovaLozinka() {
        return novaLozinka;
    }

    public void setNovaLozinka(String novaLozinka) {
        this.novaLozinka = novaLozinka;
    }

    public String getPorukaPromena() {
        return porukaPromena;
    }

    public void setPorukaPromena(String porukaPromena) {
        this.porukaPromena = porukaPromena;
    }

    public boolean isRegKorisnik() {
        return isRegKorisnik;
    }

    public boolean isGost() {
        return isGost;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isProdavac() {
        return isProdavac;
    }

    public String login() {
        username = noviKorisnik.getUsername();
        password = noviKorisnik.getLozinka();
        logger.info("user: " + username + "|| pass: " + password);

        korisnik = korisnikHelper.getKorisnikByUsername(username);
        if (korisnik == null) {
            logger.info("");
            porukaLogin = "Ne postoji korisnik sa tim korisnickim imenom.";
            return "index";
        }
        logger.info("prosao username");
        korisnik = korisnikHelper.getKorisnikByUsernameAndPassword(username, password);
        if (korisnik == null) {
            logger.info("");
            porukaLogin = "Pogresna lozinka!";
            return "index";
        }

        switch (korisnik.getStatusKorisnik()) {
            case "regkorisnik":
                isGost = false;
                isRegKorisnik = true;
                return "korisnik";
            case "admin":
                isGost = false;
                isAdmin = true;
                postaviZaAdmina();
                return "admin";
            case "prodavac":
                isGost = false;
                isProdavac = true;
                return "prodavac";
            default:
                return "index";
        }

    }

    public void postaviZaAdmina(){
        sveProjekcije = projekcijaHelper.getProjekcije();
        for (Projekcija p : sveProjekcije) {
            Film film = filmHelper.getFilmById(p.getIdFilm());
            p.setNazivFilma(film.getNaziv());
            Festival fest = festivalHelper.getFestivalByProjekcija(p.getIdProjekcija());
            p.setNazivFestivala(fest.getNaziv());
        }
    }
    
    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        session.invalidate();
        return "index";
    }

    public String promenaLozinke() {
        username = noviKorisnik.getUsername();
        password = noviKorisnik.getLozinka();
        logger.info("user: " + username + "|| pass: " + password);

        korisnik = korisnikHelper.getKorisnikByUsername(username);
        if (korisnik == null) {
            logger.info("");
            porukaPromena = "Ne postoji korisnik sa tim korisnickim imenom.";
            return "promenaLozinke";
        }
        logger.info("prosao username");
        korisnik = korisnikHelper.getKorisnikByUsernameAndPassword(username, password);
        if (korisnik == null) {
            logger.info("");
            porukaPromena = "Pogresna lozinka!";
            return "promenaLozinke";
        }

        if (!korisnikHelper.updateKorisnikLozinka(username, novaLozinka)) {
            porukaPromena = "Greska u promeni lozinke! Ponovite unos.";
            novaLozinka = "";
            password = "";
            return "promenaLozinke";
        }

        return "promenaLozinkeUspesna";

    }

    public int getMaxRezervacija() {
        return maxRezervacija;
    }

    public List<UploadedFile> getSlikeZaGaleriju() {
        return slikeZaGaleriju;
    }

    public void setSlikeZaGaleriju(List<UploadedFile> slikeZaGaleriju) {
        this.slikeZaGaleriju = slikeZaGaleriju;
    }

    public String getImdbParametarNaziv() {
        return imdbParametarNaziv;
    }

    public void setImdbParametarNaziv(String imdbParametarNaziv) {
        this.imdbParametarNaziv = imdbParametarNaziv;
    }

    public String getImdbParametarGodina() {
        return imdbParametarGodina;
    }

    public void setImdbParametarGodina(String imdbParametarGodina) {
        this.imdbParametarGodina = imdbParametarGodina;
    }

    
    
    public void setMaxRezervacija(int maxRezervacija) {
        this.maxRezervacija = maxRezervacija;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public String getPorukaDodavanjeProjekcije() {
        return porukaDodavanjeProjekcije;
    }

    public void setPorukaDodavanjeProjekcije(String porukaDodavanjeProjekcije) {
        this.porukaDodavanjeProjekcije = porukaDodavanjeProjekcije;
    }

    public String getPorukaDodavanjeProjekcije1() {
        return porukaDodavanjeProjekcije1;
    }

    public void setPorukaDodavanjeProjekcije1(String porukaDodavanjeProjekcije1) {
        this.porukaDodavanjeProjekcije1 = porukaDodavanjeProjekcije1;
    }
    
    

    public String getNovaSatnica() {
        return novaSatnica;
    }

    public void setNovaSatnica(String novaSatnica) {
        this.novaSatnica = novaSatnica;
    }

    //Zahtev za registraciju
    public Zahtevregistracija getZr() {
        return zr;
    }

    public void setZr(Zahtevregistracija zr) {
        this.zr = zr;
    }

    public List<Festival> getListaFestivala() {
        return listaFestivala;
    }

    public void setListaFestivala(List<Festival> listaFestivala) {
        this.listaFestivala = listaFestivala;
    }

    
    
    public ZahtevRegistracijaHelper getZrHelper() {
        return zrHelper;
    }

    public void setZrHelper(ZahtevRegistracijaHelper zrHelper) {
        this.zrHelper = zrHelper;
    }

    public String getLozinkaPotvrda() {
        return lozinkaPotvrda;
    }

    public void setLozinkaPotvrda(String lozinkaPotvrda) {
        this.lozinkaPotvrda = lozinkaPotvrda;
    }

    public String getPorukaZahtev() {
        return porukaZahtev;
    }

    public void setPorukaZahtev(String porukaZahtev) {
        this.porukaZahtev = porukaZahtev;
    }

    public List<Zahtevregistracija> getPristigliZahtevi() {
        return pristigliZahtevi;
    }

    public void setPristigliZahtevi(List<Zahtevregistracija> pristigliZahtevi) {
        this.pristigliZahtevi = pristigliZahtevi;
    }

    public List<Zahtevregistracija> dohvatiZahteve() {
        List<Zahtevregistracija> zahtevi = zrHelper.getZahtevi();
        for (Zahtevregistracija zahtev : zahtevi) {
            logger.info("Zahtev: user=" + zahtev.getUsername() + " || lozinka=" + zahtev.getLozinka());
        }
        return zahtevi;
    }

    public boolean zahtevPostoji(int zahtevId) {
        return (zrHelper.getZahtevById(zahtevId) != null);
    }

    public String odobriZahtev(Zahtevregistracija zahtev) {
        Korisnik korisnikTemp = new Korisnik();
        korisnikTemp.setUsername(zahtev.getUsername());
        korisnikTemp.setLozinka(zahtev.getLozinka());
        korisnikTemp.setIme(zahtev.getIme());
        korisnikTemp.setPrezime(zahtev.getPrezime());
        korisnikTemp.setKontakt(zahtev.getKontakt());
        korisnikTemp.setEmail(zahtev.getEmail());
        korisnikTemp.setBlokiran(0);
        korisnikTemp.setStatusKorisnik("regkorisnik");
        if (!korisnikHelper.insertKorisnik(korisnikTemp)) {
            logger.info("ERROR: Neuspesno dodavanje korisnika");
            return "odobravanjeZahteva";
        }

        if (!zrHelper.deleteZahtev(zahtev)) {
            logger.info("ERROR: Neuspesno brisanje zahteva");
            return "odobravanjeZahteva";
        }

        return "odobravanjeZahteva";
    }

    public String odbaciZahtev(Zahtevregistracija zahtev) {
        if (!zrHelper.deleteZahtev(zahtev)) {
            logger.info("ERROR: Neuspesno brisanje zahteva");
            return "odobravanjeZahteva";
        }

        return "odobravanjeZahteva";
    }

    public String registracija() {
        if (korisnikHelper.getKorisnikByUsername(zr.getUsername()) != null) {
            porukaZahtev = "Korisnicko ime je zauzeto! Ponovite unos.";
            zr.setUsername("");
            return "registracija";
        }

        if (!zr.getLozinka().equals(lozinkaPotvrda)) {
            logger.info("nisu iste lozinke");
            porukaZahtev = "Lozinka i potvrda lozinke se razlikuju! Ponovite unos.";
            zr.setLozinka("");
            lozinkaPotvrda = "";
            return "registracija";
        }

        if (!zrHelper.insertNoviZahtev(zr)) {
            logger.info("greska pri registraciji");
            porukaZahtev = "Greska pri registraciji! Unesite ponovo podatke.";
            zr = null;
            return "registracija";
        }
        pristigliZahtevi.add(zr);
        zr = new Zahtevregistracija();
        return "registracijaUspesna";

    }

    // Pretraga festivala
    public String getPorukaPretraga() {
        return porukaPretraga;
    }

    public void setPorukaPretraga(String porukaPretraga) {
        this.porukaPretraga = porukaPretraga;
    }

    public String getFilterNaziv() {
        return filterNaziv;
    }

    public void setFilterNaziv(String filterNaziv) {
        this.filterNaziv = filterNaziv;
    }

    public Date getFilterDatumOd() {
        return filterDatumOd;
    }

    public void setFilterDatumOd(Date filterDatumOd) {
        this.filterDatumOd = filterDatumOd;
    }

    public Date getFilterDatumDo() {
        return filterDatumDo;
    }

    public void setFilterDatumDo(Date filterDatumDo) {
        this.filterDatumDo = filterDatumDo;
    }

    public String getFilterOriginalniNaziv() {
        return filterOriginalniNaziv;
    }

    public void setFilterOriginalniNaziv(String filterOriginalniNaziv) {
        this.filterOriginalniNaziv = filterOriginalniNaziv;
    }

    public List<Festival> getFestivaliResult() {
        return festivaliResult;
    }

    public void setFestivaliResult(List<Festival> festivaliResult) {
        this.festivaliResult = festivaliResult;
    }

    public boolean isPretragaUspesna() {
        return pretragaUspesna;
    }

    public void setPretragaUspesna(boolean pretragaUspesna) {
        this.pretragaUspesna = pretragaUspesna;
    }

    public Festival getFestivalResult() {
        return festivalResult;
    }

    public void setFestivalResult(Festival festivalResult) {
        this.festivalResult = festivalResult;
    }

    public boolean isPostavljenOriginalanNaziv() {
        return postavljenOriginalanNaziv;
    }

    public void setPostavljenOriginalanNaziv(boolean postavljenOriginalanNaziv) {
        this.postavljenOriginalanNaziv = postavljenOriginalanNaziv;
    }

    public String getMestaOdrzavanja() {
        return mestaOdrzavanja;
    }

    public void setMestaOdrzavanja(String mestaOdrzavanja) {
        this.mestaOdrzavanja = mestaOdrzavanja;
    }

    public List<Projekcija> getProjekcije() {
        return projekcije;
    }

    public List<Film> getListaFilmova() {
        return listaFilmova;
    }

    public void setListaFilmova(List<Film> listaFilmova) {
        this.listaFilmova = listaFilmova;
    }
    
    

    public void setProjekcije(List<Projekcija> projekcije) {
        this.projekcije = projekcije;
    }

    public String pretraziFestivale() {
        porukaPretraga = "";
        List<Mestoodrzavanja> mesta = new ArrayList<Mestoodrzavanja>();
        projekcijeResult = new ArrayList<Projekcija>();
        postavljenOriginalanNaziv = false;

        if (!filterOriginalniNaziv.equals("")) {
            postavljenOriginalanNaziv = true;
        }

        if (!(filterDatumOd == null || filterDatumDo == null)) {
            if (filterDatumDo.before(filterDatumOd)) {
                porukaPretraga = "Nekorektan unos datuma! Ponovite unos.";
                filterDatumOd = null;
                filterDatumDo = null;
                return "pretragaFestivala";
            }
        }
        
        if(filterDatumOd == null && filterDatumDo == null && filterNaziv.equals("") && filterOriginalniNaziv.equals("") && postavljenOriginalanNaziv){
            projekcijeResult = projekcijaHelper.getProjekcije();
            pretragaUspesna = true;
            return "pretragaFestivala";
        }
        
        if(filterDatumOd == null && filterDatumDo == null && filterNaziv.equals("") && filterOriginalniNaziv.equals("") && !postavljenOriginalanNaziv){
            festivaliResult = festivalHelper.getFestivali();
            pretragaUspesna = true;
            return "pretragaFestivala";
        }
        
        if (filterOriginalniNaziv != null && filterNaziv.equals("") && filterDatumOd == null && filterDatumDo == null) {
//            logger.info("prosao originalni naziv 1");
            filmResult = filmHelper.getFilmByOriginalniNaziv(filterOriginalniNaziv);
            logger.info("film result id: " + filmResult.getIdFilm());
            projekcijeResult = projekcijaHelper.getProjekcijaByFilm(filmResult.getIdFilm());

            for (Projekcija p : projekcijeResult) {
                logger.info("projekcijaresult: " + p.getIdProjekcija());
                Festival fest = festivalHelper.getFestivalByProjekcija(p.getIdProjekcija());
                p.setNazivFestivala(fest.getNaziv());
            }

//            festivalResult = festivalHelper.getFestivalByProjekcija(projekcijaResult.getIdProjekcija());
//            logger.info("fest result: " + festivalResult.getNaziv());
            pretragaUspesna = true;

            return "pretragaFestivala";
        }

        if (filterNaziv.equals("")) {

            List<Festival> festivaliNovo = new ArrayList<Festival>();
            logger.info("usao 1");
            festivaliResult = festivalHelper.getFestivaliByDates(filterDatumOd, filterDatumDo);
            for (Festival f : festivaliResult) {
                if (!f.getStatusFestival().equals("zavrsen")) {
                    festivaliNovo.add(f);
                }
            }
            festivaliResult = festivaliNovo;
            for (Festival f : festivaliResult) {
                mesta = mestoHelper.getMestaByFestivalId(f.getIdFestival());
                for (Mestoodrzavanja m : mesta) {
                    String pom = f.getMestaOdrzavanja();
                    f.setMestaOdrzavanja(f.getMestaOdrzavanja() + "|" + m.getNazivMesta());
                }
            }

            pretragaUspesna = true;

            return "pretragaFestivala";
        }

        logger.info("stigao do naziv");
        festivaliResult = festivalHelper.getFestivalByNaziv(filterNaziv);
        for (Festival f : festivaliResult) {
            mesta = mestoHelper.getMestaByFestivalId(f.getIdFestival());
            for (Mestoodrzavanja m : mesta) {
                String pom = f.getMestaOdrzavanja();
                f.setMestaOdrzavanja(f.getMestaOdrzavanja() + "\n" + m.getNazivMesta());
            }
        }

        if (filterOriginalniNaziv != null && filterNaziv.equals("")) {
            logger.info("prosao originalni naziv");
//            festivalResult = festivaliResult.get(0);

            for (Festival f : festivaliResult) {
                filmResult = filmHelper.getFilmByOriginalniNaziv(filterOriginalniNaziv);
                projekcijaResult = projekcijaHelper.getProjekcijaByFestivalAndFilm(f.getIdFestival(), filmResult.getIdFilm());
                projekcijaResult.setNazivFestivala(f.getNaziv());
                projekcijeResult.add(projekcijaResult);
            }

        }

        if (!filterOriginalniNaziv.equals("") && !filterNaziv.equals("")) {
            filmResult = filmHelper.getFilmByOriginalniNaziv(filterOriginalniNaziv);
            festivalResult = festivalHelper.getFestivalByNaziv(filterNaziv).get(0);
            projekcijeResult = projekcijaHelper.getProjekcijeZaFestivalIFilm(festivalResult.getIdFestival(), filmResult.getIdFilm());
            for (Projekcija p : projekcijeResult) {
                p.setNazivFestivala(festivalResult.getNaziv());

            }
        }

        pretragaUspesna = true;

        return "pretragaFestivala";
    }

    public List<Festival> dohvatiPetAktuelnihFestivala() {
        logger.info("usao u funkciju");
        List<Festival> petAktuelnih = new ArrayList<Festival>();
        petAktuelnih = festivalHelper.getPetAktuelnih();
        for (Festival f : petAktuelnih) {
            logger.info("Naziv: " + f.getNaziv());
        }

        return petAktuelnih;
    }

    public boolean isIzmeniActive() {
        return izmeniActive;
    }

    public void setIzmeniActive(boolean izmeniActive) {
        this.izmeniActive = izmeniActive;
    }

    public String dohvatiFestival(int idFestival) {
        mestaOdrzavanja = "";
        festivalResult = festivalHelper.getFestivalById(idFestival);
        List<Mestoodrzavanja> mesta = mestoHelper.getMestoByNaziv(festivalResult.getMesto());
        int j = 0;
        for (Mestoodrzavanja m : mesta) {
            mestaOdrzavanja += m.getNazivMesta() + "/" + m.getLokacija() + "(Sale: ";
            List<Sala> sale = salaHelper.getSaleByMesto(m.getIdMesto());
            int i = 0;
            for (Sala s : sale) {
                logger.info("sala: " + s.getNaziv());
                mestaOdrzavanja += s.getNaziv();
                if (i < sale.size() - 1) {
                    mestaOdrzavanja += ", ";
                }
                i++;
            }
            mestaOdrzavanja += ")";
            if (j < mesta.size() - 1) {
                mestaOdrzavanja += ", ";
            }
            j++;
        }

        projekcije = projekcijaHelper.getProjekcijaByFestival(idFestival);
        for (Projekcija p : projekcije) {
            logger.info("projId: " + p.getIdProjekcija());
            Film filmPom = filmHelper.getFilmById(p.getIdFilm());
            p.setNazivFilma(filmPom.getNaziv());
            logger.info("film projekcija: " + filmPom.getNaziv());
        }
        return "festival";
    }

    public List<Festival> getFestivaliFilm() {
        return festivaliFilm;
    }

    public void setFestivaliFilm(List<Festival> festivaliFilm) {
        this.festivaliFilm = festivaliFilm;
    }

    public Festival getNoviFestival() {
        return noviFestival;
    }

    public void setNoviFestival(Festival noviFestival) {
        this.noviFestival = noviFestival;
    }

    public String getPorukaParsiranje() {
        return porukaParsiranje;
    }

    public void setPorukaParsiranje(String porukaParsiranje) {
        this.porukaParsiranje = porukaParsiranje;
    }
    
    

    public Projekcija getNovaProjekcija() {
        return novaProjekcija;
    }

    public void setNovaProjekcija(Projekcija novaProjekcija) {
        this.novaProjekcija = novaProjekcija;
    }

    public List<Projekcija> getNoveProjekcije() {
        return noveProjekcije;
    }

    public void setNoveProjekcije(List<Projekcija> noveProjekcije) {
        this.noveProjekcije = noveProjekcije;
    }

    // Za filmove
    public Film getFilmResult() {
        return filmResult;
    }

    public String getPorukaImdbPretraga() {
        return porukaImdbPretraga;
    }

    public void setPorukaImdbPretraga(String porukaImdbPretraga) {
        this.porukaImdbPretraga = porukaImdbPretraga;
    }
    
    

    public void setFilmResult(Film filmResult) {
        this.filmResult = filmResult;
    }

    public StreamedContent getSlikaFilm() {
        return slikaFilm;
    }

    public boolean isPretragaIMDBUspesna() {
        return pretragaIMDBUspesna;
    }

    public void setPretragaIMDBUspesna(boolean pretragaIMDBUspesna) {
        this.pretragaIMDBUspesna = pretragaIMDBUspesna;
    }
    
    

    public void setSlikaFilm(StreamedContent slikaFilm) {
        this.slikaFilm = slikaFilm;
    }

    public List<Slikafilm> getGalerijaFilm() {
        return galerijaFilm;
    }

    public void setGalerijaFilm(List<Slikafilm> galerijaFilm) {
        this.galerijaFilm = galerijaFilm;
    }

    public StreamedContent getSlikaTest() {
        return slikaTest;
    }

    public void setSlikaTest(StreamedContent slikaTest) {
        this.slikaTest = slikaTest;
    }

    public List<String> getListContent() {
        return listContent;
    }

    public void setListContent(List<String> listContent) {
        this.listContent = listContent;
    }

    public int getTrFilm() {
        return trFilm;
    }

    public void setTrFilm(int trFilm) {
        this.trFilm = trFilm;
    }

    public int getTrFestival() {
        return trFestival;
    }

    public void setTrFestival(int trFestival) {
        this.trFestival = trFestival;
    }

    public List<Komentar> getKomentari() {
        return komentari;
    }

    public void setKomentari(List<Komentar> komentari) {
        this.komentari = komentari;
    }

    public int getOcenaInt() {
        return ocenaInt;
    }

    public void setOcenaInt(int ocenaInt) {
        this.ocenaInt = ocenaInt;
    }

    public String dohvatiFilm(int idFestival, int idFilm) {
        porukaRezervacija = "";
        logger.info("korisnik: " + korisnik.getUsername());
        logger.info("rezenabled: " + rezervacijaEnabled);
        trFilm = idFilm;
        trFestival = idFestival;

        rezervacijaEnabled = false;
        filmResult = filmHelper.getFilmById(idFilm);
        projekcijeResult = projekcijaHelper.getProjekcijeZaFestivalIFilm(idFestival, idFilm);
        for (Projekcija p : projekcijeResult) {
            Festival fest = festivalHelper.getFestivalByProjekcija(p.getIdProjekcija());
            p.setNazivFilma(filmResult.getNaziv());
            p.setNazivFestivala(fest.getNaziv());
        }

        festivaliFilm = festivalHelper.getAktuelniFestivaliByFilm(idFilm);

        File file = new File(filmFolder + filmResult.getSlikaPoster());
        try {
            slikaFilm = new DefaultStreamedContent(new FileInputStream(file), "image/jpg");
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        galerijaFilm = slikaHelper.getSlikeZaFilm(idFilm);
        for (Slikafilm s : galerijaFilm) {
            logger.info("galerija: " + s.getSlika());
            File f = new File(filmFolder + s.getSlika());
            try {
                StreamedContent sc = new DefaultStreamedContent(new FileInputStream(f), "image/jpg");
                slikaTest = sc;
                listContent.add(s.getSlika());
                s.setContent(sc);

            } catch (FileNotFoundException ex) {
                java.util.logging.Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        if (galerijaFilm.size() == 0) {
            galerijaFilm = null;
        }

        komentari = komentarHelper.getKomentarByFilm(idFilm);

        return "film";

    }

    public void redirect(String link) throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect(link);
    }

    public BufferedImage dohvatiSliku(String imeSlike) {
        BufferedImage image = null;
        try {
            logger.info("ime slike: " + imeSlike);
            File file = new File("C:\\Users\\Aleksa\\Documents\\NetBeansProjects\\PIAFestivali\\web\\resources\\films\\theexpendables.jpg");
            slikaFilm = new DefaultStreamedContent(new FileInputStream(file), "image/jpg");

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return image;
    }

    public String getNazivFilmaZaOcenjivanje() {
        return nazivFilmaZaOcenjivanje;
    }

    public void setNazivFilmaZaOcenjivanje(String nazivFilmaZaOcenjivanje) {
        this.nazivFilmaZaOcenjivanje = nazivFilmaZaOcenjivanje;
    }

    public Film getFilmZaOcenjivanje() {
        return filmZaOcenjivanje;
    }

    public void setFilmZaOcenjivanje(Film filmZaOcenjivanje) {
        this.filmZaOcenjivanje = filmZaOcenjivanje;
    }

    public String[] getMoguceOcene() {
        return moguceOcene;
    }

    public void setMoguceOcene(String[] moguceOcene) {
        this.moguceOcene = moguceOcene;
    }

    public String getOcena() {
        return ocena;
    }

    public void setOcena(String ocena) {
        this.ocena = ocena;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public String getPorukaOcenjivanje() {
        return porukaOcenjivanje;
    }

    public void setPorukaOcenjivanje(String porukaOcenjivanje) {
        this.porukaOcenjivanje = porukaOcenjivanje;
    }

    // Za projekcije
    public Projekcija getProjekcijaResult() {
        return projekcijaResult;
    }

    public void setProjekcijaResult(Projekcija projekcijaResult) {
        this.projekcijaResult = projekcijaResult;
    }

    public Projekcija getProjekcijaSelected() {
        return projekcijaSelected;
    }

    public void setProjekcijaSelected(Projekcija projekcijaSelected) {
        this.projekcijaSelected = projekcijaSelected;
    }

    public int getProjekcijaId() {
        return projekcijaId;
    }

    public void setProjekcijaId(int projekcijaId) {
        this.projekcijaId = projekcijaId;
    }

    public List<Projekcija> getProjekcijeResult() {
        return projekcijeResult;
    }

    public void setProjekcijeResult(List<Projekcija> projekcijeResult) {
        this.projekcijeResult = projekcijeResult;
    }

    public List<Projekcija> getOstvareneProjekcije() {
        return ostvareneProjekcije;
    }

    public void setOstvareneProjekcije(List<Projekcija> ostvareneProjekcije) {
        this.ostvareneProjekcije = ostvareneProjekcije;
    }

    public List<Projekcija> getSveProjekcije() {
        return sveProjekcije;
    }

    public void setSveProjekcije(List<Projekcija> sveProjekcije) {
        this.sveProjekcije = sveProjekcije;
    }

    public Projekcija getIzabranaProjekcija() {
        return izabranaProjekcija;
    }

    public void setIzabranaProjekcija(Projekcija izabranaProjekcija) {
        this.izabranaProjekcija = izabranaProjekcija;
    }

    public String getBrojUlaznicaSlobodnaProdaja() {
        return brojUlaznicaSlobodnaProdaja;
    }

    public Film getImdbFilm() {
        return imdbFilm;
    }

    public void setImdbFilm(Film imdbFilm) {
        this.imdbFilm = imdbFilm;
    }
    
    

    public void setBrojUlaznicaSlobodnaProdaja(String brojUlaznicaSlobodnaProdaja) {
        this.brojUlaznicaSlobodnaProdaja = brojUlaznicaSlobodnaProdaja;
    }

    public String getPorukaProdaja() {
        return porukaProdaja;
    }

    public void setPorukaProdaja(String porukaProdaja) {
        this.porukaProdaja = porukaProdaja;
    }

    public int getIzabranaProjekcijaId() {
        return izabranaProjekcijaId;
    }

    public void setIzabranaProjekcijaId(int izabranaProjekcijaId) {
        this.izabranaProjekcijaId = izabranaProjekcijaId;
    }

    public String getIspisProjekcija() {
        return ispisProjekcija;
    }

    public void setIspisProjekcija(String ispisProjekcija) {
        this.ispisProjekcija = ispisProjekcija;
    }

    public Film getNoviFilm() {
        return noviFilm;
    }

    public void setNoviFilm(Film noviFilm) {
        this.noviFilm = noviFilm;
    }

    public UploadedFile getSlikaUploaded() {
        return slikaUploaded;
    }

    public void setSlikaUploaded(UploadedFile slikaUploaded) {
        this.slikaUploaded = slikaUploaded;
    }

    //Za rezervacije
    public boolean isRezervacijaEnabled() {
        return rezervacijaEnabled;
    }

    public void setRezervacijaEnabled(boolean rezervacijaEnabled) {
        this.rezervacijaEnabled = rezervacijaEnabled;
    }

    public int getBrojRezervisano() {
        return brojRezervisano;
    }

    public void setBrojRezervisano(int brojRezervisano) {
        this.brojRezervisano = brojRezervisano;
    }

    public Rezervacija getRezervacija() {
        return rezervacija;
    }

    public void setRezervacija(Rezervacija rezervacija) {
        this.rezervacija = rezervacija;
    }

    public String getPorukaRezervacija() {
        return porukaRezervacija;
    }

    public void setPorukaRezervacija(String porukaRezervacija) {
        this.porukaRezervacija = porukaRezervacija;
    }

    public List<Rezervacija> getAktuelneRezervacije() {
        return aktuelneRezervacije;
    }

    public void setAktuelneRezervacije(List<Rezervacija> aktuelneRezervacije) {
        this.aktuelneRezervacije = aktuelneRezervacije;
    }

    public List<Rezervacija> getOtkazaneRezervacije() {
        return otkazaneRezervacije;
    }

    public void setOtkazaneRezervacije(List<Rezervacija> otkazaneRezervacije) {
        this.otkazaneRezervacije = otkazaneRezervacije;
    }

    public String getPorukaPregledRezervacija() {
        return porukaPregledRezervacija;
    }

    public void setPorukaPregledRezervacija(String porukaPregledRezervacija) {
        this.porukaPregledRezervacija = porukaPregledRezervacija;
    }

    public List<Rezervacija> getOstvareneRezervacije() {
        return ostvareneRezervacije;
    }

    public void setOstvareneRezervacije(List<Rezervacija> ostvareneRezervacije) {
        this.ostvareneRezervacije = ostvareneRezervacije;
    }

    public String getDialogCena() {
        return dialogCena;
    }

    public void setDialogCena(String dialogCena) {
        this.dialogCena = dialogCena;
    }

    public String getDialogIme() {
        return dialogIme;
    }

    public void setDialogIme(String dialogIme) {
        this.dialogIme = dialogIme;
    }

    public String getDialogPrezime() {
        return dialogPrezime;
    }

    public void setDialogPrezime(String dialogPrezime) {
        this.dialogPrezime = dialogPrezime;
    }

    public String getDialogFestival() {
        return dialogFestival;
    }

    public void setDialogFestival(String dialogFestival) {
        this.dialogFestival = dialogFestival;
    }

    public String getDialogKod() {
        return dialogKod;
    }

    public void setDialogKod(String dialogKod) {
        this.dialogKod = dialogKod;
    }

    public String getDialogFilm() {
        return dialogFilm;
    }

    public void setDialogFilm(String dialogFilm) {
        this.dialogFilm = dialogFilm;
    }

    public String getPorukaDodavanje() {
        return porukaDodavanje;
    }

    public void setPorukaDodavanje(String porukaDodavanje) {
        this.porukaDodavanje = porukaDodavanje;
    }

    public Rezervacija getRezervacijaZaOdobravanje() {
        return rezervacijaZaOdobravanje;
    }

    public void setRezervacijaZaOdobravanje(Rezervacija rezervacijaZaOdobravanje) {
        this.rezervacijaZaOdobravanje = rezervacijaZaOdobravanje;
    }

    public String getFilterKod() {
        return filterKod;
    }

    public void setFilterKod(String filterKod) {
        this.filterKod = filterKod;
    }

    public String getFilterIme() {
        return filterIme;
    }

    public void setFilterIme(String filterIme) {
        this.filterIme = filterIme;
    }

    public String getFilterPrezime() {
        return filterPrezime;
    }

    public void setFilterPrezime(String filterPrezime) {
        this.filterPrezime = filterPrezime;
    }

    public boolean isPretragaEnabled() {
        return pretragaEnabled;
    }

    public void setPretragaEnabled(boolean pretragaEnabled) {
        this.pretragaEnabled = pretragaEnabled;
    }

    public String rezervacijaEnable() {
        logger.info("rezenabled: " + rezervacijaEnabled);
        logger.info("usao u rezenable:");
        for (Projekcija p : projekcijeResult) {
            logger.info("proj film: " + p.getNazivFilma());
        }
        rezervacijaEnabled = true;
        logger.info("rezenabled: " + rezervacijaEnabled);

        return "film";
    }

    public String potvrdiRezervaciju() {

        logger.info("Pozvao funkciju potvrdirezervaciju");

        Calendar cal = Calendar.getInstance();
        Date d1 = new Date(cal.getTimeInMillis());

        if (brojRezervisano > maxRezervacija || brojRezervisano < 1) {
            porukaRezervacija = "Nekorektan unos broja rezervacija!";
            return "film";
        }

        logger.info("korisnik: " + korisnik.getUsername());
        String jedinstveniKod = getSaltString();
        logger.info("kod: " + jedinstveniKod);
        logger.info("projId: " + projekcijaId);

        for (Projekcija p : projekcijeResult) {
            logger.info("p.id: " + p.getIdProjekcija());
            logger.info("p.satnica: " + p.getSatnica());
        }

//        projekcijaSelected = projekcijaHelper.getProjekcijaById(projekcijaId);
        rezervacija = new Rezervacija();
        rezervacija.setJedinstveniKod(jedinstveniKod);
        rezervacija.setDatumRezervacije(d1);
        rezervacija.setStatusRezervacije("podneta");
        rezervacija.setBrojRezervacija(brojRezervisano);
        rezervacija.setIdKorisnik(korisnik.getIdKorisnik());
        rezervacija.setIdProjekcija(projekcijaId);

        if (!rezervacijaHelper.insertRezervacija(rezervacija)) {
            porukaRezervacija = "Greska u pokusaju rezervacije! Pokusajte ponovo.";
            return "film";
        }

        porukaRezervacija = "Uspesno ste izvrsili rezervaciju projekcije!";

        return "rezervacijaUspesna";

    }

    public String dohvatiRezervacije() {

        List<Rezervacija> rezervacije = rezervacijaHelper.getRezervacijaByKorisnik(korisnik.getIdKorisnik());
        int brojIsteklih = 0;
        for (Rezervacija r : rezervacije) {
            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();
            c1.setTime(r.getDatumRezervacije());
//            c2.setTime(new Date());

            c1.add(Calendar.DAY_OF_MONTH, 2);
            if (c1.before(c2) && r.getStatusRezervacije().equals("podneta")) {
                r.setStatusRezervacije("istekla");
                rezervacijaHelper.updateRezervacija(r);
                brojIsteklih++;
            }
            int idProj = r.getIdProjekcija();
            Projekcija p = projekcijaHelper.getProjekcijaById(idProj);
            logger.info("p: " + p.getNazivFilma());
            Film film = filmHelper.getFilmById(p.getIdFilm());
            p.setNazivFilma(film.getNaziv());
            logger.info("proj film: " + projekcijaHelper.getProjekcijaById(idProj).getNazivFilma());
            r.setProjekcija(p);
        }

        if (brojIsteklih >= 3) {
            porukaPregledRezervacija = "Korisniku je zabranjen pristup sistemu za rezervisanje!";
            korisnik.setBlokiran(1);
            korisnikHelper.updateKorisnik(korisnik);
        }

        aktuelneRezervacije = rezervacijaHelper.getRezervacijaByKorisnikAndStatus(korisnik.getIdKorisnik(), "podneta");
        for (Rezervacija r : aktuelneRezervacije) {

            int idProj = r.getIdProjekcija();
            Projekcija p = projekcijaHelper.getProjekcijaById(idProj);
            Film film = filmHelper.getFilmById(p.getIdFilm());
            p.setNazivFilma(film.getNaziv());
            logger.info("proj film: " + projekcijaHelper.getProjekcijaById(idProj).getNazivFilma());
            r.setProjekcija(p);
        }

        otkazaneRezervacije = rezervacijaHelper.getRezervacijaByKorisnikAndStatus(korisnik.getIdKorisnik(), "otkazana");
        for (Rezervacija r : otkazaneRezervacije) {

            int idProj = r.getIdProjekcija();
            Projekcija p = projekcijaHelper.getProjekcijaById(idProj);
            Film film = filmHelper.getFilmById(p.getIdFilm());
            p.setNazivFilma(film.getNaziv());
            logger.info("proj film: " + projekcijaHelper.getProjekcijaById(idProj).getNazivFilma());
            r.setProjekcija(p);
        }
        return "pregledRezervacija";
    }

    public String otkaziRezervaciju(int idRezervacija) {
        logger.info("usao u otkazi");

//        rezervacijaHelper.deleteRezervacija(rezervacijaHelper.getRezervacijaById(idRezervacija));
        Rezervacija rez = rezervacijaHelper.getRezervacijaById(idRezervacija);
        rez.setStatusRezervacije("otkazana");
        rezervacijaHelper.updateRezervacija(rez);
//        return dohvatiRezervacije();
        return dohvatiRezervacije();
    }

    public boolean prosaoFestival(Projekcija projekcija) {
        Festival festival = festivalHelper.getFestivalByProjekcija(projekcija.getIdProjekcija());
        if (festival.getStatusFestival().equals("zavrsen")) {
            return true;
        }
        return false;
    }

    public String dohvatiDogadjaje() {

        ostvareneProjekcije = new ArrayList<Projekcija>();

        ostvareneRezervacije = rezervacijaHelper.getRezervacijaByKorisnikAndStatus(korisnik.getIdKorisnik(), "ostvarena");
        
        for (Rezervacija r : ostvareneRezervacije) {
            Projekcija p = projekcijaHelper.getProjekcijaById(r.getIdProjekcija());
            Festival fest = festivalHelper.getFestivalByProjekcija(p.getIdProjekcija());
            p.setNazivFestivala(fest.getNaziv());
            Film film = filmHelper.getFilmById(p.getIdFilm());
            p.setNazivFilma(film.getNaziv());
            if(p.getDatum().before(new Date())){
                ostvareneProjekcije.add(p);
            }
        }

        return "pregledDogadjaja";

    }

    public String redirectToOceniFilm(String nazivFilma) {

        nazivFilmaZaOcenjivanje = nazivFilma;
        filmZaOcenjivanje = filmHelper.getFilmByNaziv(nazivFilma);
        return "oceniFilm";

    }

    public String oceniFilm() {

//        if(Integer.parseInt(ocena) < 1 || Integer.parseInt(ocena) > 10){
//            porukaOcenjivanje = "Unesite ocenu u opsegu 1-10";
//            return "oceniFilm";
//        }
        if (ocenaHelper.getOcenaZaKorisnikAndFilm(korisnik.getIdKorisnik(), filmZaOcenjivanje.getIdFilm()) == null) {
            porukaOcenjivanje = "Vec ste ocenili ovaj film!";
            return "oceniFilm";
        }

//        int ocenaInt = Integer.parseInt(ocena);
        Ocena ocenaObj = new Ocena();
        ocenaObj.setIdFilm(filmZaOcenjivanje.getIdFilm());
        ocenaObj.setOcena(ocenaInt);
        ocenaObj.setIdKorisnik(korisnik.getIdKorisnik());
        ocenaHelper.insertOcena(ocenaObj);

        Film filmPom = filmZaOcenjivanje;
        int brojGlasova = filmPom.getBrojOcena();
        List<Ocena> oceneFilm = ocenaHelper.getOcenaZaFilm(filmZaOcenjivanje.getIdFilm());
        double sumaOcena = 0.0;
        double noviRejting;
        for (Ocena o : oceneFilm) {
            sumaOcena += o.getOcena();
        }
        noviRejting = sumaOcena / oceneFilm.size();
        logger.info("novi rejting: " + noviRejting);
        logger.info("novi rejting decimal: " + new BigDecimal(noviRejting));
        filmPom.setRejting(noviRejting);
        filmPom.setBrojOcena(oceneFilm.size());
        filmHelper.updateFilm(filmPom);

        Komentar komentarObj = new Komentar();
        komentarObj.setIdFilm(filmZaOcenjivanje.getIdFilm());
        komentarObj.setSadrzaj(komentar);
        komentarObj.setIdKorisnik(korisnik.getIdKorisnik());
        komentarHelper.insertKomentar(komentarObj);

        return "ocenjivanjeUspesno";
    }

    // Za prodavca
    public List<Rezervacija> dohvatiPodneteRezervacije() {

        if (pretragaEnabled) {
            return aktuelneRezervacije;
        }

        aktuelneRezervacije = rezervacijaHelper.getRezervacijaByStatus("podneta");
        for (Rezervacija r : aktuelneRezervacije) {
            int idKorisnik = r.getIdKorisnik();
            Korisnik k = korisnikHelper.getKorisnikById(idKorisnik);
            r.setImeKorisnika(k.getIme());
            r.setPrezimeKorisnika(k.getPrezime());
        }

        return aktuelneRezervacije;

    }

    public void dohvatiPodatkeRezervacije(Rezervacija rez) {
        rezervacijaZaOdobravanje = rez;
        Projekcija proj = projekcijaHelper.getProjekcijaById(rez.getIdProjekcija());
        dialogFilm = filmHelper.getFilmById(projekcijaHelper.getProjekcijaById(rez.getIdProjekcija()).getIdFilm()).getNaziv();
        dialogIme = rez.getImeKorisnika();
        dialogPrezime = rez.getPrezimeKorisnika();
        Festival fest = festivalHelper.getFestivalByProjekcija(rez.getIdProjekcija());
        dialogCena = "" + proj.getCena() * rezervacijaZaOdobravanje.getBrojRezervacija();
        dialogFestival = "" + fest.getNaziv();
        dialogKod = rez.getJedinstveniKod();
//        return "\"PF('dlg').show();\"";
//        return "prodavac";
    }

    public String odobriRezervaciju() {
        rezervacijaZaOdobravanje.setStatusRezervacije("ostvarena");
        rezervacijaHelper.updateRezervacija(rezervacijaZaOdobravanje);

        for (int i = 0; i < rezervacijaZaOdobravanje.getBrojRezervacija(); i++) {
            Ulaznica ulaznica = new Ulaznica();
            ulaznica.setIdProjekcija(rezervacijaZaOdobravanje.getIdProjekcija());
            ulaznica.setIdKorisnik(rezervacijaZaOdobravanje.getIdKorisnik());
            ulaznica.setStatusUlaznice("kupljena");
            ulaznicaHelper.insertUlaznica(ulaznica);
        }
        return "prodavac";
    }

    public String pretraziRezervacije() {
        pretragaEnabled = false;
        aktuelneRezervacije = dohvatiPodneteRezervacije();
        logger.info("usao u pretragu rez");
        pretragaEnabled = true;
        List<Rezervacija> rezervacijePretraga = new ArrayList<Rezervacija>();
        if (!filterKod.equals("")) {
            logger.info("prosao uslov1");
            for (Rezervacija r : aktuelneRezervacije) {
                if (r.getJedinstveniKod().startsWith(filterKod)) {
                    rezervacijePretraga.add(r);
                }
            }
            aktuelneRezervacije = rezervacijePretraga;
            for (Rezervacija r : aktuelneRezervacije) {
                int idKorisnik = r.getIdKorisnik();
                Korisnik k = korisnikHelper.getKorisnikById(idKorisnik);
                r.setImeKorisnika(k.getIme());
                r.setPrezimeKorisnika(k.getPrezime());
            }
        }

        if (!filterPrezime.equals("")) {
            logger.info("prosao uslov2");
            rezervacijePretraga = new ArrayList<Rezervacija>();
            for (Rezervacija r : aktuelneRezervacije) {
                if (r.getPrezimeKorisnika().startsWith(filterPrezime)) {
                    rezervacijePretraga.add(r);
                }
            }
            aktuelneRezervacije = rezervacijePretraga;
            for (Rezervacija r : aktuelneRezervacije) {
                int idKorisnik = r.getIdKorisnik();
                Korisnik k = korisnikHelper.getKorisnikById(idKorisnik);
                r.setImeKorisnika(k.getIme());
                r.setPrezimeKorisnika(k.getPrezime());
            }
        }

        if (!filterIme.equals("")) {
            logger.info("prosao uslov2");
            rezervacijePretraga = new ArrayList<Rezervacija>();
            for (Rezervacija r : aktuelneRezervacije) {
                if (r.getImeKorisnika().startsWith(filterIme)) {
                    rezervacijePretraga.add(r);
                }
            }
            aktuelneRezervacije = rezervacijePretraga;
            for (Rezervacija r : aktuelneRezervacije) {
                int idKorisnik = r.getIdKorisnik();
                Korisnik k = korisnikHelper.getKorisnikById(idKorisnik);
                r.setImeKorisnika(k.getIme());
                r.setPrezimeKorisnika(k.getPrezime());
            }
        }

        return "prodavac";
    }

    public List<Projekcija> dohvatiSveProjekcije() {

        sveProjekcije = projekcijaHelper.getProjekcije();
        for (Projekcija p : sveProjekcije) {
            Film film = filmHelper.getFilmById(p.getIdFilm());
            p.setNazivFilma(film.getNaziv());
            Festival fest = festivalHelper.getFestivalByProjekcija(p.getIdProjekcija());
            p.setNazivFestivala(fest.getNaziv());
        }
        return sveProjekcije;

    }

    public String potvrdiProdaju() {

        logger.info("usao u prodaju");
        izabranaProjekcija = projekcijaHelper.getProjekcijaById(izabranaProjekcijaId);
        int brojUlaznicaInt = Integer.parseInt(brojUlaznicaSlobodnaProdaja);
        if (brojUlaznicaInt < 1 || brojUlaznicaInt > maxRezervacija) {
            porukaProdaja = "Broj ulaznica nije korektan.";
            return "prodavac";
        }
        logger.info("prosao broj ulaznica");
        Ulaznica ulaznicaProdaja = new Ulaznica();
        logger.info("izab. proj.: " + izabranaProjekcija.getIdProjekcija());
        ulaznicaProdaja.setIdProjekcija(izabranaProjekcija.getIdProjekcija());
        ulaznicaProdaja.setIdKorisnik(korisnik.getIdKorisnik());
        ulaznicaProdaja.setStatusUlaznice("kupljena");
        for (int i = 0; i < brojUlaznicaInt; i++) {
            ulaznicaHelper.insertUlaznica(ulaznicaProdaja);
        }
        porukaProdaja = "Uspesno izdate i fakturisane ulaznice.";

        return "prodavac";

    }

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 11) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    public List<Film> dohvatiSveFilmove() {

        List<Film> filmovi = filmHelper.getFilmovi();
        return filmovi;
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    public List<Mestoodrzavanja> dohvatiSvaMesta() {
        List<Mestoodrzavanja> mesta = new ArrayList<Mestoodrzavanja>();
        List<Mestoodrzavanja> svaMesta = mestoHelper.getMestaDistinct();
        
        for(Mestoodrzavanja sm : svaMesta){
            int ima = 0;
            for(Mestoodrzavanja m : mesta){
                if(m.getNazivMesta().equals(sm.getNazivMesta())){
                    ima = 1;
                }
            }
            if(ima == 0){
                mesta.add(sm);
            }
        }
        return mesta;
        
//        return mestoHelper.getMestaDistinct();
    }

    public List<Mestoodrzavanja> dohvatiMestaZaFestival() {
        return mestoHelper.getMestoByNaziv(noviFestival.getMesto());
    }
    
    public List<Mestoodrzavanja> dohvatiMestaZaFestival(Festival fest){
        return mestoHelper.getMestoByNaziv(fest.getMesto());
    }

    public void dodajProjekciju() {
        if(novaProjekcija.getDatum().before(noviFestival.getDatumPocetka()) || novaProjekcija.getDatum().after(noviFestival.getDatumKraja())){
            porukaDodavanjeProjekcije = "Nekorektan datum projekcije!";
            return;
        }
        noveProjekcije.add(novaProjekcija);
        porukaDodavanjeProjekcije = "Projekcija uspešno dodata.";

    }
    
    public void ucitajProjekciju(Festival fest){
//        if(novaProjekcija.getDatum().before(fest.getDatumPocetka()) || novaProjekcija.getDatum().after(fest.getDatumKraja())){
//            porukaDodavanjeProjekcije1 = "Nekorektan datum projekcije!";
//            return;
//        }
        logger.info("proj: " + novaProjekcija.getIdFilm());
        Projekcija projekcija = new Projekcija();
        projekcija.setCena(novaProjekcija.getCena());
        projekcija.setDatum(novaProjekcija.getDatum());
        projekcija.setIdFilm(novaProjekcija.getIdFilm());
        projekcija.setMesto(novaProjekcija.getMesto());
        projekcija.setSatnica(novaProjekcija.getSatnica());
        
        projekcijaHelper.insertProjekcija(projekcija);
        Programfestivala prog = new Programfestivala();
        prog.setIdFestival(fest.getIdFestival());
        prog.setIdFilm(projekcija.getIdFilm());
        prog.setIdProjekcija(projekcija.getIdProjekcija());
        prog.setNazivPrograma("p1");
        programHelper.insertProgram(prog);
        porukaDodavanjeProjekcije1 = "Projekcija uspešno dodata.";
    }

    public List<Projekcija> dohvatiNoveProjekcije() {

        for (Projekcija p : noveProjekcije) {
            logger.info("nova projekcija: " + p.getIdFilm());
        }
        return noveProjekcije;
    }

    public String ispisiProjekcije() {
        String ispis = "";
        int i = 1;
        for (Projekcija p : noveProjekcije) {
            Film film = filmHelper.getFilmById(p.getIdFilm());
            p.setNazivFilma(film.getNaziv());
            ispis += "" + i + ". Film: " + p.getNazivFilma() + " | Cena: " + p.getCena() + ".00 RSD | Datum: " + p.getDatumPocetkaLepo() + " " + p.getSatnica() + " | Mesto: " + p.getMesto();
            i++;
        }
//        ispis = ispis.replaceAll("(\r\n|\n)", "<br />");
        return ispis;
    }

    public String dodajNoviFestival() {

        Date currDate = new Date();

        if (currDate.after(noviFestival.getDatumKraja())) {
            noviFestival.setStatusFestival("zavrsen");
        } else if (currDate.after(noviFestival.getDatumPocetka()) && currDate.before(noviFestival.getDatumKraja())) {
            noviFestival.setStatusFestival("u toku");
        } else if (currDate.before(noviFestival.getDatumPocetka())) {
            noviFestival.setStatusFestival("nije poceo");
        }
        festivalHelper.insertFestival(noviFestival);
        Festival festival = festivalHelper.getFestivalByNaziv(noviFestival.getNaziv()).get(0);
        for (Projekcija p : noveProjekcije) {
            projekcijaHelper.insertProjekcija(p);
            Programfestivala program = new Programfestivala();
            program.setIdFestival(festival.getIdFestival());
            program.setIdFilm(p.getIdFilm());
            program.setIdProjekcija(p.getIdProjekcija());
            program.setNazivPrograma("program");
            programHelper.insertProgram(program);
        }

        porukaDodavanje = "Uspesno dodat festival " + festival.getNaziv() + "!";
        noviFestival = new Festival();
        noveProjekcije = new ArrayList<Projekcija>();
        novaProjekcija = new Projekcija();
        return "unosFestivala";

    }

    public List<Sala> dohvatiSaleZaMesto(String lokacija) {
        logger.info("naziv mesta: " + noviFestival.getMesto());
        Mestoodrzavanja mesto = mestoHelper.getMestoByLokacija(noviFestival.getMesto(), lokacija);
        List<Sala> sale = new ArrayList<Sala>();
        if(mesto != null){
            sale = salaHelper.getSaleByMesto(mesto.getIdMesto());
        }
        return sale;
    }

    public String dodajNoviFilm() {
        logger.info("usao u dodajfilm");

        logger.info("slikaUploaded: " + slikaUploaded.getFileName());
        String imeSlike = noviFilm.getNaziv().replaceAll(" ", "");
        try {
            FileOutputStream fileStream = new FileOutputStream("C:\\Users\\Aleksa\\Documents\\NetBeansProjects\\PIAFestivali\\web\\resources\\films\\" + imeSlike + "Poster.jpg");
            byte[] imageBytes = slikaUploaded.getContents();
            fileStream.write(imageBytes);
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        noviFilm.setSlikaPoster(imeSlike + "Poster.jpg");
        filmHelper.insertFilm(noviFilm);
        porukaDodavanje = "Uspesno dodat film: " + noviFilm.getNaziv() + "!";
        return "unosFestivala";
    }

    public void handleFileUpload(FileUploadEvent event) {
        logger.info("usao u handle");
        slikaUploaded = event.getFile();
    }
    
    public void handleFileUploadGalerija(FileUploadEvent event) {
        logger.info("usao u handle galerija");
        slikeZaGaleriju.add(event.getFile());
    }

    public String izmeniEnabled(Projekcija p) {
//        izmeniActive = true;
        p.setEditable(true);
        logger.info("usao u enable");
        return "admin";
    }

    public String izmeniPodatak(Projekcija p) {
        logger.info("usao u izmeni");
        logger.info("satnica: " + p.getSatnica());
        logger.info("film: " + p.getNazivFilma());
        projekcijaHelper.updateProjekcija(p);
        izmeniActive = false;
        p.setEditable(false);
        List<Rezervacija> rezervacijePodnete = rezervacijaHelper.getRezervacijaByProjekcijaAndStatus(p.getIdProjekcija(), "podneta");
        for(Rezervacija r : rezervacijePodnete){
            Korisnik k = korisnikHelper.getKorisnikById(r.getIdKorisnik());
            if(k.getPoruka() == null){
                k.setPoruka("Predstava " + p.getNazivFilma() + " dana " + p.getDatumPocetkaLepo() + " je izmenjena, proverite nove podatke.");
            }else{
                k.setPoruka(k.getPoruka() + "\n " + "Predstava " + p.getNazivFilma() + " dana " + p.getDatumPocetkaLepo() + " je izmenjena, proverite nove podatke.");
            }
            logger.info("kornsik poruka:" + k.getPoruka());
            korisnikHelper.updateKorisnik(k);
        }
        
        List<Rezervacija> rezervacijeOstvarene = rezervacijaHelper.getRezervacijaByProjekcijaAndStatus(p.getIdProjekcija(), "ostvarena");
        for(Rezervacija r : rezervacijeOstvarene){
            Korisnik k = korisnikHelper.getKorisnikById(r.getIdKorisnik());
            if(k.getPoruka() == null){
                k.setPoruka("Predstava " + p.getNazivFilma() + " dana " + p.getDatumPocetkaLepo() + " je izmenjena, proverite nove podatke.");
            }else{
                k.setPoruka(k.getPoruka() + "\n " + "Predstava " + p.getNazivFilma() + " dana " + p.getDatumPocetkaLepo() + " je izmenjena, proverite nove podatke.");
            }
            korisnikHelper.updateKorisnik(k);
        }

        return "admin";

    }
    
    public String otkaziProjekciju(Projekcija p){
        logger.info("usao u otkazi1");
        logger.info(" proj: " + p.getIdProjekcija());
        int idProjekcije = p.getIdProjekcija();
//        projekcijaHelper.deleteProjekcija(p);
//        programHelper.deleteProjekciju(p.getIdProjekcija());
        List<Rezervacija> rezervacijePodnete = rezervacijaHelper.getRezervacijaByProjekcijaAndStatus(p.getIdProjekcija(), "podneta");
        for(Rezervacija r : rezervacijePodnete){
            Korisnik k = korisnikHelper.getKorisnikById(r.getIdKorisnik());
            if(k.getPoruka() == null || k.getPoruka().equals("")){
                k.setPoruka("Predstava " + p.getNazivFilma() + " dana " + p.getDatumPocetkaLepo() + "|" + p.getSatnica() + " je otkazana, nestalo struje, molim Vas podjite za mnom.");
            }else{
                k.setPoruka(k.getPoruka() + "\n " + "Predstava " + p.getNazivFilma() + " dana " + p.getDatumPocetkaLepo() + "|" + p.getSatnica() + " je otkazana, nestalo struje, molim Vas podjite za mnom.");

            }
            r.setStatusRezervacije("otkazana");
            rezervacijaHelper.updateRezervacija(r);
            logger.info("kornsik poruka:" + k.getPoruka());
            korisnikHelper.updateKorisnik(k);
        }
        List<Rezervacija> rezervacijeOstvarene = rezervacijaHelper.getRezervacijaByProjekcijaAndStatus(p.getIdProjekcija(), "ostvarena");
        
        for(Rezervacija r : rezervacijeOstvarene){
            Korisnik k = korisnikHelper.getKorisnikById(r.getIdKorisnik());
            if(k.getPoruka() == null || k.getPoruka().equals("")){
                k.setPoruka("Predstava " + p.getNazivFilma() + " dana " + p.getDatumPocetkaLepo() + "|" + p.getSatnica() + " je otkazana, nestalo struje, mozete doci po pare.");
            }else{
                k.setPoruka(k.getPoruka() + "\n " + "Predstava " + p.getNazivFilma() + " dana " + p.getDatumPocetkaLepo() + "|" + p.getSatnica() + " je otkazana, nestalo struje, mozete doci po pare.");

            }
            r.setStatusRezervacije("otkazana");
            rezervacijaHelper.updateRezervacija(r);
            korisnikHelper.updateKorisnik(k);

        }
        sveProjekcije.remove(p);
        
        return "admin";
    }

    public String ucitajFestivale(){
        
        try {
            Object obj =  parser.parse(new FileReader(podaciFestivaliDest));
            JSONObject jsonObject = (JSONObject) obj;
            System.out.println(jsonObject);
            
            JSONArray festivali = (JSONArray)jsonObject.get("Festivals");

            System.out.println(festivali);
            Iterator<Object> festivaliIter = festivali.iterator();
            while(festivaliIter.hasNext()){
                Object festivalObj = festivaliIter.next();
                JSONObject festivalJSON = (JSONObject)festivalObj;
                Festival festival = new Festival();
                festival.setNaziv("" + festivalJSON.get("Festival"));
                festival.setMesto("" + festivalJSON.get("Place"));
                festival.setKratakOpis("" + festivalJSON.get("About"));
                Date d1 = new Date();
                String datum1 = festivalJSON.get("StartDate").toString();
                String[] params = datum1.split("/");
                Calendar c1 = Calendar.getInstance();
                c1.set(Integer.parseInt(params[2]), Integer.parseInt(params[1]), Integer.parseInt(params[0]));
                d1 = c1.getTime();
                festival.setDatumPocetka(d1);
                
                Date d2 = new Date();
                String datum2 = festivalJSON.get("EndDate").toString();
                String[] params1 = datum1.split("/");
                Calendar c2 = Calendar.getInstance();
                c2.set(Integer.parseInt(params1[2]), Integer.parseInt(params1[1]) - 1, Integer.parseInt(params1[0]));
                d2 = c2.getTime();
                festival.setDatumKraja(d2);
                
                Date currDate = new Date();
                if (currDate.after(festival.getDatumKraja())) {
                    festival.setStatusFestival("zavrsen");
                } else if (currDate.after(festival.getDatumPocetka()) && currDate.before(festival.getDatumKraja())) {
                    festival.setStatusFestival("u toku");
                } else if (currDate.before(festival.getDatumPocetka())) {
                    festival.setStatusFestival("nije poceo");
                }
                listaFestivala.add(festival);
                festivalHelper.insertFestival(festival);
                
            }
            
            JSONArray mesta = (JSONArray)jsonObject.get("Locations");
            
            Iterator<Object> mestaIter = mesta.iterator();
            while(mestaIter.hasNext()){
                Object mestoObj = mestaIter.next();
                JSONObject mestoJSON = (JSONObject)mestoObj;
//                Mestoodrzavanja mesto = new Mestoodrzavanja();
//                mesto.setNazivMesta("" + mestoJSON.get("Place"));
                String nazivMesta = "" + mestoJSON.get("Place");
                JSONArray lokacije = (JSONArray)mestoJSON.get("Location");
                Iterator<Object> lokacijeIter = lokacije.iterator();
                while(lokacijeIter.hasNext()){
                    Object lokacijaObj = lokacijeIter.next();
                    JSONObject lokacijaJSON = (JSONObject)lokacijaObj;
                    Mestoodrzavanja mesto = new Mestoodrzavanja();
                    mesto.setNazivMesta("" + nazivMesta);
                    String lokacijaNiz[] = ("" + lokacijaJSON.get("Name")).split(",");
                    mesto.setLokacija("" + lokacijaNiz[0]);
                    if(mestoHelper.getMestoByLokacija(nazivMesta, lokacijaNiz[0]) == null){
                        mestoHelper.insertMesto(mesto);
                    }
                    
                    if(lokacijaNiz.length > 1){
                        int idMesto = mestoHelper.getMestoByNaziv(nazivMesta).get(0).getIdMesto();
                        Sala sala = new Sala();
                        sala.setIdMesto(idMesto);
                        sala.setNaziv(lokacijaNiz[1]);
                        salaHelper.insertSala(sala); 
                    }
                }
            }
            
            
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            porukaParsiranje = "Greska! Los format fajla! ";
            java.util.logging.Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "ucitavanjeFestivala";
//        return "admin";
    }
    
    public String ucitajFilmove(){
        try {
            Object obj =  parser.parse(new FileReader(podaciFilmoviDest));
            JSONObject jsonObject = (JSONObject) obj;
            System.out.println(jsonObject);
            
            JSONArray filmovi = (JSONArray)jsonObject.get("Movies");
            
            Iterator<Object> filmoviIter = filmovi.iterator();
            while(filmoviIter.hasNext()){
                Object filmObj = filmoviIter.next();
                JSONObject filmJSON = (JSONObject)filmObj;
                Film film = new Film();
                film.setNaziv("" + filmJSON.get("Title"));
                film.setOriginalniNaziv("" + filmJSON.get("OriginalTitle"));
                film.setGodina("" + filmJSON.get("Year"));
                film.setKratakOpis("" + filmJSON.get("Summary"));
                film.setReziser("" + filmJSON.get("Director"));
                film.setGlavniGlumci("" + filmJSON.get("Stars"));
                film.setTrajanje("" + filmJSON.get("Runtime"));
                film.setZemljaPorekla("" + filmJSON.get("Country"));
                film.setLinkImdb("" + filmJSON.get("Link1"));
                film.setLinkRotten("" + filmJSON.get("Link2"));
                
                listaFilmova.add(film);
                filmHelper.insertFilm(film);
            }
            
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            porukaParsiranje = "Greska! Los format fajla! ";
            java.util.logging.Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        return "admin";
        return "ucitavanjeFestivala";
    }
    
    public String generisiPDF(){
        
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(pdfDest));
            document.open();
            addTitlePage(document);
            addProgramFestivala(document);
            document.close();
            
            
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            java.util.logging.Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "festival";
    }
    
    public void addProgramFestivala(Document document){
        try {
//            PdfPTable table = new PdfPTable(projekcije.size() + 1);            

            PdfPTable table = new PdfPTable(5);
            PdfPCell c1 = new PdfPCell(new Phrase("Naziv filma"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            
            c1 = new PdfPCell(new Phrase("Datum"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            
            c1 = new PdfPCell(new Phrase("Satnica"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            
            c1 = new PdfPCell(new Phrase("Mesto"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            
            c1 = new PdfPCell(new Phrase("Sala"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            table.setHeaderRows(1);
            
            for(Projekcija p : projekcije){
                table.addCell("" + p.getNazivFilma());
                table.addCell("" + p.getDatumPocetkaLepo());
                table.addCell("" + p.getSatnica());
                table.addCell("" + p.getMesto());
                table.addCell("" + p.getSala());
            }

            document.add(table);
        } catch (DocumentException ex) {
            java.util.logging.Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void addTitlePage(Document document){
        try {
            Paragraph preface = new Paragraph();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            preface.add(new Paragraph("Program festivala: " + festivalResult.getNaziv(), catFont));
            addEmptyLine(preface, 5);
            document.add(preface);
        } catch (DocumentException ex) {
            java.util.logging.Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
     
    public void ucitajMultimediju(Film film){
        logger.info("usao u multimediju");
        String imeSlike = film.getNaziv().replaceAll(" ", "");
        try {
            FileOutputStream fileStream = new FileOutputStream(filmFolder + imeSlike + "Poster.jpg");
            byte[] imageBytes = slikaUploaded.getContents();
            fileStream.write(imageBytes);
            int i = 1;
            for(UploadedFile upload : slikeZaGaleriju){
                fileStream = new FileOutputStream(filmFolder + imeSlike + i + ".jpg");
                byte[] imageBytes1 = upload.getContents();
                fileStream.write(imageBytes1);
                
                Slikafilm slikaFilm = new Slikafilm();
                slikaFilm.setIdFilm(film.getIdFilm());
                slikaFilm.setNaziv("slika");
                slikaFilm.setSlika(imeSlike + i + ".jpg");
                slikaHelper.insertSlika(slikaFilm);
                i++;
            }
            
            
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        slikeZaGaleriju = new ArrayList<UploadedFile>();
        film.setSlikaPoster(imeSlike + "Poster.jpg");
        filmHelper.updateFilm(film);
//        return "ucitavanjeFestivala";
    }
     
    
    public String imdbPretraga(){
        try {
            porukaImdbPretraga = "";
            logger.info("usao u imdb");
            String url = "http://www.omdbapi.com/?t=" + imdbParametarNaziv.replaceAll(" ", "+") + "&apikey=" + API_KEY;
            
            InputStream response = new URL(url).openStream();
            String rezultat = IOUtils.toString(response, "UTF-8");
            logger.info("thestring: " + rezultat);
            
            Object obj = parser.parse(rezultat);
            JSONObject jsonObject = (JSONObject) obj;
            if(jsonObject.containsKey("Error")){
                porukaImdbPretraga = "Ne postoji taj film";
                return "imdbPretraga";
            }
            
            logger.info("naslov:" + jsonObject.get("Title"));
            
            imdbFilm.setOriginalniNaziv("" + jsonObject.get("Title"));
            imdbFilm.setReziser("" + jsonObject.get("Director"));
            imdbFilm.setTrajanje("" + jsonObject.get("Runtime"));
            imdbFilm.setGodina("" + jsonObject.get("Year"));
            imdbFilm.setRejting(Double.parseDouble("" + jsonObject.get("imdbRating")));
            imdbFilm.setBrojOcena(Integer.parseInt(("" + jsonObject.get("imdbVotes")).replaceAll(",", "")));
            imdbFilm.setKratakOpis("" + jsonObject.get("Plot"));
            imdbFilm.setGlavniGlumci("" + jsonObject.get("Actors"));
            imdbFilm.setZemljaPorekla("" + jsonObject.get("Country"));
            imdbFilm.setSlikaPoster("" + jsonObject.get("Poster"));
            


        } catch (MalformedURLException ex) {
            java.util.logging.Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            java.util.logging.Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        pretragaIMDBUspesna = true;
        return "imdbPretraga";
        
    }
     
}
