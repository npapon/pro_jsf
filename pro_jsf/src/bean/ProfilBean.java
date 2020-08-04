package bean;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import constante.Dossiers;
import constante.MessagesErreur;
import constante.ParametresFormulaire;
import constante.Tampon;
import dao.DAOException;
import dao.ImageDao;
import dao.UtilisateurDao;
import eu.medsea.mimeutil.MimeUtil;
import exception.FormValidationException;

@ManagedBean
@SessionScoped
@MultipartConfig( location = "C:\\Users\\npapo\\git\\pro_jsf\\pro_jsf", maxFileSize = 10485760, maxRequestSize = 52428800, fileSizeThreshold = 1048576 )
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

        try {
            supprimerImageProfil();
        } catch ( NullPointerException e ) {
            System.out.println( "Pas d'image à supprimer" );
        }
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

    public void

            creerImageProfil() throws Exception {
        // recuperation email
        String contexteApplication = Dossiers.REPERTOIRE_CONTEXTE_APPLICATION;
        String chemin = Dossiers.REPERTOIRE_IMAGESPROFIL;
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        param1 = params.get( "param1Name" );
        param2 = params.get( "param2Name" );
        utilisateur.setLogin( param1 );
        utilisateur.setMot_de_passe( param2 );
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();

        Timestamp dateModification = new Timestamp( System.currentTimeMillis() );

        try {
            Part part = chargerImageProfil( request );
            System.out.println( "PART NAME" + part.getName() );
            String email = utilisateurDao.rechercherSession(
                    utilisateur.getLogin(), utilisateur.getMot_de_passe() ).getEmail();

            imageProfil.setEmail( email );
            String libelleImage = affecterLibelleImage( part, imageProfil );
            String emplacement = affecterEmplacementImage( chemin, libelleImage, imageProfil );

            enregistrerImageProfil( part, contexteApplication, chemin, libelleImage );
            if ( imageDao.rechercherImage( imageProfil.getEmail() ) == null ) {
                imageDao.creerImage( imageProfil );
            } else {
                imageDao.modifierImage( imageProfil.getEmail(), imageProfil.getLibelle(), imageProfil.getEmplacement(),
                        dateModification );
            }

        } catch (

        DAOException e ) {

            e.printStackTrace();
        }

    }

    public String affecterEmplacementImage( String chemin, String libelleImage, Image image ) {
        String emplacement = chemin + "/" + libelleImage;
        if ( emplacement.isEmpty() ) {
            try {
                throw new FormValidationException( MessagesErreur.EMPLACEMENT_VIDE );
            } catch ( FormValidationException e ) {
                System.out.println( e.getMessage() );
                ;
            }

        } else {
            image.setEmplacement( emplacement );
        }
        return emplacement;
    }

    public String affecterLibelleImage( Part part, Image image ) throws Exception {

        String libelleImage = this.recupererNomImage( part );

        validationFormatImage( part );

        image.setLibelle( libelleImage );

        return libelleImage;

    }

    // getParts permet de récupérer les données envoyer dans un formulaire
    // de type type enctype="multipart/form-data" (fichiers, champs
    // classiques ...
    // elle retourne une collection d'éléments de type Part
    // Collection<Part> parts = request.getParts();
    // getPart permet de récupérer un élément du formulaire en particulier
    // passé en paramètre

    public Part chargerImageProfil( HttpServletRequest request ) {

        Part part = null;
        try {
            part = request.getPart( ParametresFormulaire.IMAGEPROFIL );
            System.out.println( "NULL POINTER" + part.getName() );

            champContientUnFichier( part );

        } catch ( IOException | ServletException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch ( IllegalStateException e ) {
            e.printStackTrace();

        }

        return part;
    }

    /*
     * Comment savoir si on a un fichier dans les éléments du formulaire une
     * seule solution : vérifier que la requête envoyée dans POST contient
     * l'attribut filename
     * 
     * Ex de requête post avec un fichier :
     * 
     * Content-Disposition: form-data;name="fichier";
     * filename="nom_du_fichier.ext"
     */

    private void champContientUnFichier( Part part ) {

        Boolean estUnFichier = false;
        String enteteRequetePostContentDisposition = part.getHeader( "Content-Disposition" );

        for ( String coupleAttributValeur : enteteRequetePostContentDisposition.split( ";" ) ) {
            if ( coupleAttributValeur.trim().startsWith( "filename" ) ) {
                estUnFichier = true;
            }
        }
        if ( estUnFichier == false ) {
            try {
                throw ( new Exception( MessagesErreur.AUCUN_FICHIER ) );
            } catch ( Exception e ) {
                e.printStackTrace();

            }
        }

    }

    public String recupererNomImage( Part part ) {
        String enteteRequetePostContentDisposition = part.getHeader( "Content-Disposition" );
        System.out.println( "enteteRequetePostContentDisposition " + enteteRequetePostContentDisposition );
        String nomImage = null;
        for ( String coupleAttributValeur : enteteRequetePostContentDisposition.split( ";" ) ) {
            if ( coupleAttributValeur.trim().startsWith( "filename" ) ) {

                nomImage = coupleAttributValeur.substring( coupleAttributValeur.lastIndexOf( "\\" ) + 1,
                        coupleAttributValeur.lastIndexOf( "\"" ) );

                return nomImage;
            }
        }
        return nomImage;

    }

    private void validationFormatImage( Part part ) throws Exception {

        InputStream contenuFichier = null;
        // recupération du contenu du fichier

        contenuFichier = part.getInputStream();

        /*
         * Extraction du type MIME (charger la librarie) du fichier depuis
         * l'InputStream
         */

        MimeUtil.registerMimeDetector( "eu.medsea.mimeutil.detector.MagicMimeMimeDetector" );
        // <?> correspond à une collection qui accepte tout type
        Collection<?> mimeTypes = MimeUtil.getMimeTypes( contenuFichier );
        System.out.println( mimeTypes );// ex : image/jpeg

        /*
         * Si le fichier est bien une image, alors son en-tête MIME commence par
         * la chaîne "image"
         */
        if ( !mimeTypes.toString().startsWith( "image" ) ) {

            throw new Exception( MessagesErreur.FICHIER_PAS_UNE_IMAGE );

        }

    }

    private void enregistrerImageProfil( Part part, String contexteApplication, String chemin, String libelleImage ) {
        BufferedInputStream entree = null;
        BufferedOutputStream sortie = null;
        try {
            entree = new BufferedInputStream( part.getInputStream(), Tampon.TAILLE_TAMPON_10240 );

            sortie = new BufferedOutputStream(
                    new FileOutputStream( new File( contexteApplication + "/" + chemin + "/" + libelleImage ) ),
                    Tampon.TAILLE_TAMPON_10240 );

            /*
             * Lit le fichier reçu et écrit son contenu dans un fichier sur le
             * disque.
             */
            byte[] tampon = new byte[Tampon.TAILLE_TAMPON_10240];
            int longueur;
            while ( ( longueur = entree.read( tampon ) ) > 0 ) {
                sortie.write( tampon, 0, longueur );
            }

        }

        catch ( FileNotFoundException e ) {
            e.printStackTrace();

        }

        catch ( IOException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        finally {
            try {
                sortie.close();
            } catch ( IOException ignore ) {
            }
            try {
                entree.close();
            } catch ( IOException ignore ) {
            }
        }
        System.out.println( "Fichier enregistré ici : " + chemin + "/" + libelleImage );

    }

}
