package bean;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import constante.Dossiers;
import constante.MessagesErreur;
import dao.DAOException;
import dao.ImageDao;
import dao.UtilisateurDao;
import exception.FormValidationException;

@ManagedBean
@SessionScoped

public class ProfilBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Utilisateur       utilisateur;
    private Image             imageProfil;
    private String            param1;
    private String            param2;
    private FichierV2         fichier;
    @EJB
    private UtilisateurDao    utilisateurDao;

    @EJB
    private ImageDao          imageDao;

    public ProfilBean() {
        utilisateur = new Utilisateur();
        imageProfil = new Image();
        fichier = new FichierV2();

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
        try {
            imageDao.supprimerImage( imageProfil.getEmail() );
            supprimerImageDuRepertoire( Dossiers.REPERTOIRE_CONTEXTE_APPLICATION, Dossiers.REPERTOIRE_IMAGESPROFIL,
                    imageProfil.getEmail() );
        } catch ( NullPointerException e ) {
            System.out.println( "pas d'image à supprimer" );
        }

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

    public void

            creerImageProfil() throws IOException {
        // recuperation email

        Timestamp dateModification = new Timestamp( System.currentTimeMillis() );
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        param1 = params.get( "param1Name" );
        param2 = params.get( "param2Name" );
        utilisateur.setLogin( param1 );
        utilisateur.setMot_de_passe( param2 );

        try {

            String email = utilisateurDao.rechercherSession(
                    utilisateur.getLogin(), utilisateur.getMot_de_passe() ).getEmail();

            imageProfil.setEmail( email );
            String libelleImage = FilenameUtils.getName( fichier.getContenu().getName() );
            imageProfil.setLibelle( libelleImage );
            String emplacement = affecterEmplacementImage( Dossiers.REPERTOIRE_IMAGESPROFIL, libelleImage, imageProfil );

            chargerImageProfil( Dossiers.REPERTOIRE_CONTEXTE_APPLICATION, Dossiers.REPERTOIRE_IMAGESPROFIL, libelleImage );

            if ( imageDao.rechercherImage( imageProfil.getEmail() ) == null ) {
                imageDao.creerImage( imageProfil );
            } else {
                imageDao.modifierImage( imageProfil.getEmail(), imageProfil.getLibelle(), imageProfil.getEmplacement(),
                        dateModification );
            }

        } catch ( DAOException e ) {

            e.printStackTrace();
        }

    }

    public void chargerImageProfil( String contexteApplication, String chemin, String libelleImage ) throws IOException {
        String nomFichier = FilenameUtils.getName( fichier.getContenu().getName() );
        System.out.println( "nom fichier " + nomFichier );
        String tailleFichier = FileUtils.byteCountToDisplaySize( fichier.getContenu().getSize() );
        String typeFichier = fichier.getContenu().getContentType();
        byte[] contenuFichier = fichier.getContenu().getBytes();
        System.out.println( "image enregistré ici : " + contexteApplication + "/" + chemin + "/" + libelleImage );
        Path repertoireFichier = Paths.get( contexteApplication + "/" + chemin + "/" + libelleImage );

        try {
            Files.write( repertoireFichier, contenuFichier );
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        FacesContext.getCurrentInstance().addMessage( null, new FacesMessage(
                String.format( "Fichier '%s', de taille '%s' et de type '%s' envoyé avec succès !",
                        nomFichier, tailleFichier, typeFichier ) ) );
    }

    public String affecterEmplacementImage( String chemin, String libelleImage, Image image ) {
        String emplacement = chemin + "/" + libelleImage;
        if ( emplacement.isEmpty() ) {
            try {
                throw new FormValidationException( MessagesErreur.EMPLACEMENT_VIDE );
            } catch ( FormValidationException e ) {
                System.out.println( e.getMessage() );
            }

        } else {
            image.setEmplacement( emplacement );
        }
        return emplacement;
    }

    public FichierV2 getFichier() {
        return fichier;
    }

    public void setFichier( FichierV2 fichier ) {
        this.fichier = fichier;
    }

}
