package bean;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

@ManagedBean
@RequestScoped
public class UploadBean3 implements Serializable {
    private static final long serialVersionUID = 1L;

    private FichierV2         fichier;

    // Initialisation du bean fichier
    public UploadBean3() {
        fichier = new FichierV2();
    }

    public void envoyer() throws IOException {
        String nomFichier = FilenameUtils.getName( fichier.getContenu().getName() );
        String tailleFichier = FileUtils.byteCountToDisplaySize( fichier.getContenu().getSize() );
        String typeFichier = fichier.getContenu().getContentType();
        byte[] contenuFichier = fichier.getContenu().getBytes();

        Path nicoFichier = Paths.get( "C:\\upload\\topgay.txt" );
        try {
            Files.write( nicoFichier, contenuFichier );
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        /*
         * Effectuer ici l'enregistrement du contenu du fichier sur le disque,
         * ou dans la BDD (accompagné du type du contenu, éventuellement), ou
         * tout autre traitement souhaité...
         */

        FacesContext.getCurrentInstance().addMessage( null, new FacesMessage(
                String.format( "Fichier '%s', de taille '%s' et de type '%s' envoyé avec succès !",
                        nomFichier, tailleFichier, typeFichier ) ) );
    }

    public FichierV2 getFichier() {
        return fichier;
    }

    public void setFichier( FichierV2 fichier ) {
        this.fichier = fichier;
    }
}