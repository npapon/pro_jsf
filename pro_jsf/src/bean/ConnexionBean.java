package bean;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import cookie.CookieGenerateur;
import dao.ImageDao;
import dao.UtilisateurDao;

@ManagedBean
@SessionScoped

public class ConnexionBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Utilisateur       utilisateur;

    private String            emplacementImageProfil;

    private Cookie            cookieLogin;
    HttpSession               session;

    @EJB
    private UtilisateurDao    utilisateurDao;

    @EJB
    private ImageDao          imageDao;

    public ConnexionBean() {
        utilisateur = new Utilisateur();
        System.out.println( "session" + session );
    }

    public void createSession() {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        session = (HttpSession) facesContext.getExternalContext().getSession( true );
        System.out.println( "session : " + session );
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur( Utilisateur utilisateur ) {
        this.utilisateur = utilisateur;
    }

    public String goToPageProfil() {
        // ...
        return "/profil.xhtml?faces-redirect=true";
    }

    public void recupererEmplacementImageProfil() {

        String email = utilisateurDao.rechercherSession( utilisateur.getLogin(), utilisateur.getMot_de_passe() ).getEmail();
        try {
            emplacementImageProfil = imageDao.rechercherImage( email ).getEmplacement();
            System.out.println( "emplacement image profil " + emplacementImageProfil );
        } catch ( NullPointerException e ) {
            System.out.println( "Pas d'image de profil" );
        }

    }

    public String getEmplacementImageProfil() {

        return emplacementImageProfil;

    }

    public void setCookieLogin() {
        CookieGenerateur generateurCookie = new CookieGenerateur();
        cookieLogin = generateurCookie.setCookie( cookieLogin, "cookieLogin", utilisateur.getLogin(), 220000 );

    }

    public Cookie getCookieLogin() {

        return cookieLogin;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession( HttpSession session ) {
        this.session = session;
    }

}
