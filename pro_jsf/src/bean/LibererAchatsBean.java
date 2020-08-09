package bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import dao.AchatDao;

@ManagedBean
@RequestScoped
public class LibererAchatsBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Achat>       listAchats;
    private Utilisateur       utilisateur;
    private List<Achat>       achatsBloquesV2;
    private String            param1;

    @EJB
    private AchatDao          achatDao;

    public LibererAchatsBean() {
        utilisateur = new Utilisateur();
        achatsBloquesV2 = new ArrayList<>();

    }

    public void libererAchatV2() {

        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        param1 = params.get( "param1Name" );

        utilisateur.setLogin( param1 );
        String loginUser = utilisateur.getLogin();

        achatsBloquesV2 = achatDao.rechercherAchatsBloquesV2();
        System.out.println( " achat bloqué " + achatsBloquesV2 );
        Timestamp dateModification = new Timestamp( System.currentTimeMillis() );

        for ( Achat achat : achatsBloquesV2 ) {
            System.out.println( "ID :" + achat.getId() );
            achatDao.modifierAchatBloqueV3( achat.getId(), loginUser, dateModification );
        }

        FacesContext.getCurrentInstance().addMessage( null, new FacesMessage(
                "libre" ) );

    }

    public List<Achat> getListAchats() {
        return listAchats;
    }

    public void setListAchats( List<Achat> listAchats ) {
        this.listAchats = listAchats;
    }

}
