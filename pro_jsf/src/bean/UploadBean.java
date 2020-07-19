package bean;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

@ManagedBean
@RequestScoped
public class UploadBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private Fichier           fichier;

    // Initialisation du bean fichier
    public UploadBean() {
        fichier = new Fichier();
    }

    /*
     * 
     * C'est dans la méthode envoyer() que vous devrez appeler les traitements
     * que vous souhaitez effectuer sur votre fichier : l'écrire sur le disque
     * comme nous l'avions fait dans notre système basé sur les servlets, ou
     * pourquoi pas l'enregistrer dans une table dans votre base de données,
     * etc. mais ici on écrit pas en base ni enregistre sur le serveur on
     * affiche juste un message d'erreur
     */
    public void envoyer() throws IOException {

        /*
         * Toutes ces lignes servent à récupérer depuis la facelet des
         * métadonnées du fichier pour le mettre dans le message d'erreur plus
         * bas je récupère le nom du fichier via UploadedFile.getName() et le
         * convertis dans un format propre grâce à la méthode utilitaire
         * FilenameUtils.getName() ;
         * 
         * à la ligne 30, je récupère la taille du fichier via
         * UploadedFile.getSize() et la convertis dans un format lisible grâce à
         * la méthode utilitaire FileUtils.byteCountToDisplaySize() ;
         * 
         * à la ligne 31, je récupère directement le type du fichier via
         * UploadedFile.getContentType().
         */
        String nomFichier = FilenameUtils.getName( fichier.getContenu().getName() );
        String tailleFichier = FileUtils.byteCountToDisplaySize( fichier.getContenu().getSize() );
        String typeFichier = fichier.getContenu().getContentType();
        byte[] contenuFichier = fichier.getContenu().getBytes();

        /*
         * Je génère finalement un simple FacesMessage, initialisé avec un
         * identifiant à null et un message récapitulant le nom, la taille et le
         * type du fichier envoyé, que j'ajoute au FacesContext. Tout ceci est
         * fait, comme vous devez vous en douter, en prévision d'un affichage
         * dans notre Facelet via la balise <h:messages globalOnly="true">.
         * 
         * String format remplace les %s (qui veut dire formater en String, on
         * pourrait mettre décimal %d ...) des valeurs en paramètres apres
         */

        FacesContext.getCurrentInstance().addMessage( null, new FacesMessage(
                String.format( "Fichier '%s', de taille '%s' et de type '%s' envoyé avec succès !",
                        nomFichier, tailleFichier, typeFichier ) ) );
    }

    public Fichier getFichier() {
        return fichier;
    }

    public void setFichier( Fichier fichier ) {
        this.fichier = fichier;
    }
}