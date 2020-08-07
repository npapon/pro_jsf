package bean;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.myfaces.custom.fileupload.UploadedFile;

public class FichierV2 {

    @Size( min = 15, message = "La phrase de description du fichier doit contenir au moins 15 caractères" )
    private String       description;
    @NotNull( message = "Merci de sélectionner un fichier à envoyer" )
    private UploadedFile contenu;

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public UploadedFile getContenu() {
        return contenu;
    }

    public void setContenu( UploadedFile contenu ) {
        this.contenu = contenu;
    }
}