package bean;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @EJB
    private UtilisateurDao    utilisateurDao;

    @EJB
    private ImageDao          imageDao;

    public ConnexionBean() {
        utilisateur = new Utilisateur();
    }

    public void connexion() {
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
        emplacementImageProfil = imageDao.rechercherImage( email ).getEmplacement();

    }

    public String getEmplacementImageProfil() {

        return emplacementImageProfil;

    }

    public void setCookieLogin() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        cookieLogin = new Cookie( "cookieLogin", utilisateur.getLogin() );
        cookieLogin.setPath( request.getContextPath() );
        cookieLogin.setMaxAge( 1000 );
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.addCookie( cookieLogin );

    }

    public Cookie getCookieLogin() {

        CookieGenerateur cookieGenerateur = new CookieGenerateur();
        FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        cookieLogin = new Cookie( "cookieLogin", utilisateur.getLogin() );
        cookieLogin.setPath( request.getContextPath() );
        cookieLogin.setMaxAge( 1000 );
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.addCookie( cookieLogin );

        return cookieLogin;
    }

}
