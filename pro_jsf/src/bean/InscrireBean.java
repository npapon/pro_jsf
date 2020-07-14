package bean;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import dao.UtilisateurDao;

@ManagedBean
// nous avons annot� notre bean avec @RequestScoped pour le placer dans la
// port�e requ�te : en effet, notre objet ne va intervenir qu'� chaque demande
// d'inscription
// et n'a donc pas vocation � �tre stock� plus longtemps que le temps d'une
// requ�te.
@ViewScoped
public class InscrireBean implements Serializable {
    private static final long serialVersionUID = 1L;

    // Notre objet contient tout d'abord une r�f�rence � notre entit�
    // � laquelle est associ�e une m�thode getter lignes 40
    private Utilisateur       utilisateur;

    // Injection de notre EJB (Session Bean Stateless)
    // Notre DAO Utilisateur avec JPA est un simple EJB Stateless,
    // qui est inject� (initi�) automatiquement via l'annotation @EJB
    @EJB
    private UtilisateurDao    utilisateurDao;

    // Cette entit� Utilisateur est initialis�e depuis un simple constructeur
    // public

    public InscrireBean() {
        utilisateur = new Utilisateur();
    }

    // M�thode d'action appel�e lors du clic sur le bouton du formulaire
    // d'inscription
    // cette m�thode d'action nomm�e inscrire() est charg�e :
    // d'initialiser la propri�t� dateInscription de l'entit� Utilisateur avec
    // la date courante, via la m�thode initialiserDateInscription()
    // d'enregistrer l'utilisateur en base via utilisateurDao.creer()
    // d'initialiser un message de succ�s de la validation.

    public void inscrire() {
        initialiserDateInscription();
        utilisateurDao.creer( utilisateur );
        // FacesMessage : cet objet permet simplement de d�finir un message de
        // validation,
        // mis dans son constructeur. Il existe d'autres constructeurs,
        // notamment un qui permet d'associer
        // � un message un niveau de criticit�, en pr�cisant une cat�gorie qui
        // est d�finie par FacesMessage.Severity. Les niveaux existants sont
        // repr�sent�s par des constantes que vous pouvez retrouver sur la
        // documentation de l'objet FacesMessage.
        // En ce qui nous concerne, nous
        // ne sp�cifions qu'un message dans le constructeur, et c'est par
        // cons�quent la criticit� Severity.INFO qui est appliqu�e par d�faut �
        // notre message par JSF ;
        FacesMessage message = new FacesMessage( "Succ�s de l'inscription !" );
        // FacesContext : cet objet contient l'arbre des composants d'une vue
        // ainsi que les �ventuels messages d'erreur qui leur sont associ�s.
        // nous nous en servons pour mettre en place un FacesMessage dans le
        // contexte courant
        // via la m�thode addMessage() pour que la r�ponse puisse ensuite
        // l'afficher
        // nous reviendrons ensemble sur l'int�r�t de passer null en tant que
        // premier argument de cette m�thode
        FacesContext.getCurrentInstance().addMessage( null, message );
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    private void initialiserDateInscription() {
        Timestamp date = new Timestamp( System.currentTimeMillis() );
        utilisateur.setDate_creation( date );
    }
}