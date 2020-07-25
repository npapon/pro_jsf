package bean;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import dao.ImageDao;
import dao.UtilisateurDao;

@ManagedBean
@SessionScoped

public class ProfilBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Utilisateur       utilisateur;

    @EJB
    private UtilisateurDao    utilisateurDao;

    @EJB
    private ImageDao          imageDao;

    public ProfilBean() {
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

}
