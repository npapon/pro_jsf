package validators;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import constante.MessagesErreur;
import dao.DAOException;
import dao.UtilisateurDao;

//on ne peut utiliser @FacesValidator car on utilise un EJB
//on passe donc par un backing bean
@ManagedBean
@RequestScoped
public class IdentifiantsValidator implements Validator {
    @EJB
    UtilisateurDao              utilisateurDao;

    private static final String COMPOSANT_LOGIN = "composantLogin";

    /*
     * le lien avec la vue se fait via la balise <f:validator
     * binding="#{identifiantsValidator}" />
     */
    @Override
    public void validate( FacesContext facesContext, UIComponent component, Object value ) throws ValidatorException {

        // dans le formulaire on a mis dans le champ login, cet attribut
        // binding="#{composantLogin}" pour le r�cup�rer apres dans le champ mot
        // de passe ...
        // car c'est le mot de passe qui sera le param�tre value de notre
        // m�thode qu'on va tester
        // et voir s'il matche avec le login r�cup�r�

        // pour r�cup�rer ce champ dans le champ mot de passe, on passe par la
        // balise
        // <f:attribute name="composantLogin" value="#{composantLogin}"

        // 1. R�cup�ration de la valeur � traiter depuis le param�tre value

        String motDePasse = (String) value;

        // 2. On r�cup�re dans le champ mot de passe la valeur de l'attribut
        // login r�cup�re dans la
        // balise // <f:attribute name="composantLogin"
        // value="#{composantLogin}" via son name
        // c'est la value qu'on r�cup�re

        UIInput composantLogin = (UIInput) component.getAttributes().get( COMPOSANT_LOGIN );
        String login = (String) composantLogin.getValue();

        // FacesMessage : cet objet permet simplement de d�finir un message de
        // validation,
        // mis dans son constructeur. Il existe d'autres constructeurs,
        // notamment un qui permet d'associer
        // � un message un niveau de criticit�, en pr�cisant une cat�gorie qui
        // est d�finie par FacesMessage.Severity. Les niveaux existants sont
        // repr�sent�s par des constantes que vous pouvez retrouver sur la
        // documentation de l'objet FacesMessage.
        // si nous ne sp�cifions qu'un message dans le constructeur, et c'est
        // par
        // cons�quent la criticit� Severity.INFO qui est appliqu�e par d�faut �
        // notre message par JSF ;
        // ici on met un mmessage de type erreur
        // le deuxi�me argument et le message d'erreur

        try {

            if ( utilisateurDao.rechercherSession( login, motDePasse ) == null ) {

                throw new ValidatorException(

                        // argument : sev�rit�, summary, detail
                        // les 2 derniers argument servent � affichr le message
                        // d'erreur
                        // avec du d�tail ou non mais pas besoin des 2

                        // ici on fait pas de addMessage car j'imagine qu'il
                        // comprend tout seul que c'est li� au composant
                        // via <f:validator binding="#{identifiantsValidator}"
                        // />�
                        new FacesMessage( FacesMessage.SEVERITY_ERROR, MessagesErreur.SESSION_INTROUVABLE, null ) );

            }
        } catch ( DAOException e ) {

            // FacesContext : cet objet contient l'arbre des composants d'une
            // vue
            // ainsi que les �ventuels messages d'erreur qui leur sont associ�s.
            // nous nous en servons pour mettre en place un FacesMessage dans le
            // contexte courant
            // via la m�thode addMessage() pour que la r�ponse puisse ensuite
            // l'afficher
            // Le premier argument permet de d�finir le composant associ� au
            // message
            // Toutefois, il est possible de n'afficher que les
            // messages qui ne sont attach�s � aucun composant d�fini,
            // c'est-�-dire les messages dont l'id est null, en utilisant
            // l'attribut optionnel globalOnly="true" : <h:messages
            // globalOnly="true" />

            FacesMessage message = new FacesMessage( FacesMessage.SEVERITY_ERROR, e.getMessage(), null );

            facesContext.addMessage( component.getClientId( facesContext ), message );

        }

    }

}
