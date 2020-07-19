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
     * C'est dans la m�thode envoyer() que vous devrez appeler les traitements
     * que vous souhaitez effectuer sur votre fichier : l'�crire sur le disque
     * comme nous l'avions fait dans notre syst�me bas� sur les servlets, ou
     * pourquoi pas l'enregistrer dans une table dans votre base de donn�es,
     * etc. mais ici on �crit pas en base ni enregistre sur le serveur on
     * affiche juste un message d'erreur
     */
    public void envoyer() throws IOException {

        /*
         * Toutes ces lignes servent � r�cup�rer depuis la facelet des
         * m�tadonn�es du fichier pour le mettre dans le message d'erreur plus
         * bas je r�cup�re le nom du fichier via UploadedFile.getName() et le
         * convertis dans un format propre gr�ce � la m�thode utilitaire
         * FilenameUtils.getName() ;
         * 
         * � la ligne 30, je r�cup�re la taille du fichier via
         * UploadedFile.getSize() et la convertis dans un format lisible gr�ce �
         * la m�thode utilitaire FileUtils.byteCountToDisplaySize() ;
         * 
         * � la ligne 31, je r�cup�re directement le type du fichier via
         * UploadedFile.getContentType().
         */
        String nomFichier = FilenameUtils.getName( fichier.getContenu().getName() );
        String tailleFichier = FileUtils.byteCountToDisplaySize( fichier.getContenu().getSize() );
        String typeFichier = fichier.getContenu().getContentType();
        byte[] contenuFichier = fichier.getContenu().getBytes();

        /*
         * Je g�n�re finalement un simple FacesMessage, initialis� avec un
         * identifiant � null et un message r�capitulant le nom, la taille et le
         * type du fichier envoy�, que j'ajoute au FacesContext. Tout ceci est
         * fait, comme vous devez vous en douter, en pr�vision d'un affichage
         * dans notre Facelet via la balise <h:messages globalOnly="true">.
         * 
         * String format remplace les %s (qui veut dire formater en String, on
         * pourrait mettre d�cimal %d ...) des valeurs en param�tres apres
         */

        FacesContext.getCurrentInstance().addMessage( null, new FacesMessage(
                String.format( "Fichier '%s', de taille '%s' et de type '%s' envoy� avec succ�s !",
                        nomFichier, tailleFichier, typeFichier ) ) );
    }

    public Fichier getFichier() {
        return fichier;
    }

    public void setFichier( Fichier fichier ) {
        this.fichier = fichier;
    }
}