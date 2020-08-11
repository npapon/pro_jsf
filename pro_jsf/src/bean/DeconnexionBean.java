package bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class DeconnexionBean implements Serializable {

    public void deconnexion() {

        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

    }

    public String goToPageConnexion() {
        // ...
        return "/connexion.xhtml?faces-redirect=true";
    }

}
