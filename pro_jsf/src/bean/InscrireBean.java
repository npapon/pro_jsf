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
// nous avons annoté notre bean avec @RequestScoped pour le placer dans la
// portée requête : en effet, notre objet ne va intervenir qu'à chaque demande
// d'inscription
// et n'a donc pas vocation à être stocké plus longtemps que le temps d'une
// requête.
@ViewScoped
public class InscrireBean implements Serializable {
    private static final long serialVersionUID = 1L;

    // Notre objet contient tout d'abord une référence à notre entité
    // à laquelle est associée une méthode getter lignes 40
    private Utilisateur       utilisateur;

    // Injection de notre EJB (Session Bean Stateless)
    // Notre DAO Utilisateur avec JPA est un simple EJB Stateless,
    // qui est injecté (initié) automatiquement via l'annotation @EJB
    @EJB
    private UtilisateurDao    utilisateurDao;

    // Cette entité Utilisateur est initialisée depuis un simple constructeur
    // public

    public InscrireBean() {
        utilisateur = new Utilisateur();
    }

    // Méthode d'action appelée lors du clic sur le bouton du formulaire
    // d'inscription
    // cette méthode d'action nommée inscrire() est chargée :
    // d'initialiser la propriété dateInscription de l'entité Utilisateur avec
    // la date courante, via la méthode initialiserDateInscription()
    // d'enregistrer l'utilisateur en base via utilisateurDao.creer()
    // d'initialiser un message de succès de la validation.

    public void inscrire() {
        initialiserDateInscription();
        utilisateurDao.creer( utilisateur );
        // FacesMessage : cet objet permet simplement de définir un message de
        // validation,
        // mis dans son constructeur. Il existe d'autres constructeurs,
        // notamment un qui permet d'associer
        // à un message un niveau de criticité, en précisant une catégorie qui
        // est définie par FacesMessage.Severity. Les niveaux existants sont
        // représentés par des constantes que vous pouvez retrouver sur la
        // documentation de l'objet FacesMessage.
        // En ce qui nous concerne, nous
        // ne spécifions qu'un message dans le constructeur, et c'est par
        // conséquent la criticité Severity.INFO qui est appliquée par défaut à
        // notre message par JSF ;
        FacesMessage message = new FacesMessage( "Succès de l'inscription !" );
        // FacesContext : cet objet contient l'arbre des composants d'une vue
        // ainsi que les éventuels messages d'erreur qui leur sont associés.
        // nous nous en servons pour mettre en place un FacesMessage dans le
        // contexte courant
        // via la méthode addMessage() pour que la réponse puisse ensuite
        // l'afficher
        // nous reviendrons ensemble sur l'intérêt de passer null en tant que
        // premier argument de cette méthode
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