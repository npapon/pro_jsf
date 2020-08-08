package bean;

import javax.validation.constraints.NotNull;

import org.apache.myfaces.custom.fileupload.UploadedFile;

public class FichierV2 {

    @NotNull( message = "Merci de sélectionner un fichier à envoyer" )
    private UploadedFile contenu;

    public UploadedFile getContenu() {
        return contenu;
    }

    public void setContenu( UploadedFile contenu ) {
        this.contenu = contenu;
    }
}