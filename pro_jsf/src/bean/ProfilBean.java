package bean;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import constante.Dossiers;
import dao.ImageDao;
import dao.UtilisateurDao;

@ManagedBean
@SessionScoped

public class ProfilBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Utilisateur       utilisateur;
    private Image             imageProfil;
    private String            param1;
    private String            param2;
    @EJB
    private UtilisateurDao    utilisateurDao;

    @EJB
    private ImageDao          imageDao;

    public ProfilBean() {
        utilisateur = new Utilisateur();
        imageProfil = new Image();
        System.out.println( "EMMMMAILLLL" );

    }

    public void connexion() {
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur( Utilisateur utilisateur ) {
        this.utilisateur = utilisateur;
    }

    public Image getImageProfil() {
        return imageProfil;
    }

    public void setImageProfil( Image imageProfil ) {
        this.imageProfil = imageProfil;
    }

    public String goToInscriptionPage() {
        // ...
        return "/inscription.xhtml?faces-redirect=true";
    }

    public void parametersAction() {
        // ces 2 lignes permettent de récupérer tous les paramètres d'une vue
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        param1 = params.get( "param1Name" );
        param2 = params.get( "param2Name" );

        System.out.println( "valeur " + param1 + param2 );

    }

    public String getParam1() {
        return param1;
    }

    public String getParam2() {
        return param2;
    }

    public void supprimerCompte() {

        supprimerImageProfil();
        supprimerUtilisateur();

    }

    public void supprimerImageProfil() {

        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        param1 = params.get( "param1Name" );
        param2 = params.get( "param2Name" );
        utilisateur.setLogin( param1 );
        utilisateur.setMot_de_passe( param2 );
        String email = utilisateurDao.rechercherSession(
                utilisateur.getLogin(), utilisateur.getMot_de_passe() ).getEmail();

        imageProfil.setEmail( email );
        imageProfil = imageDao.rechercherImage( email );

        imageDao.supprimerImage( imageProfil.getEmail() );
        supprimerImageDuRepertoire( Dossiers.REPERTOIRE_CONTEXTE_APPLICATION, Dossiers.REPERTOIRE_IMAGESPROFIL,
                imageProfil.getEmail() );

    }

    public void supprimerImageDuRepertoire( String contexteApplication, String chemin, String libelleImage ) {
        Path path = Paths.get( contexteApplication + "/" + chemin + "/" + libelleImage );

        try {
            if ( Files.exists( path ) ) {
                Files.delete( path );
            }
        } catch ( NoSuchFileException e ) {
            System.out.println( "Le fichier suivant n'existe pas : " + path.getFileName() );
            System.out.println( e.getMessage() );

        } catch ( IOException e ) {
            System.out.println( e.getMessage() );

        }
    }

    public void supprimerUtilisateur() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        param1 = params.get( "param1Name" );
        param2 = params.get( "param2Name" );
        utilisateur.setLogin( param1 );
        utilisateur.setMot_de_passe( param2 );
        String email = utilisateurDao.rechercherSession(
                utilisateur.getLogin(), utilisateur.getMot_de_passe() ).getEmail();

        utilisateur.setEmail( email );
        utilisateurDao.supprimerUtilisateur( utilisateur.getEmail() );

    }

}
