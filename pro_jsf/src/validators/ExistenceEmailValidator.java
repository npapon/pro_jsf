package validators;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import constante.MessagesErreur;
import dao.DAOException;
import dao.UtilisateurDao;

@ManagedBean
@RequestScoped
public class ExistenceEmailValidator implements Validator {

    @EJB
    private UtilisateurDao utilisateurDao;

    @Override
    /*
     * un objet de type FacesContext qui permet d'acc�der au contexte de
     * l'application jsf
     *//*
        * un objet de type UICOmponent qui contient une r�f�rence sur le
        * composant dont la donn�e est � valider
        */
    /*
     * un objet de type Object qui encapsule la valeur de la donn�es � valider.
     * Le traiement va s'appliquer sur l'attribut value du champ concern�
     */
    public void validate( FacesContext context, UIComponent component, Object value ) throws ValidatorException {
        /* R�cup�ration de la valeur � traiter depuis le param�tre value */
        String email = (String) value;
        try {
            if ( utilisateurDao.trouver( email ) != null ) {
                /*
                 * Si une adresse est retourn�e, alors on envoie une exception
                 * propre � JSF, qu'on initialise avec un FacesMessage de
                 * gravit� "Erreur" et contenant le message d'explication. Le
                 * framework va alors g�rer lui-m�me cette exception et s'en
                 * servir pour afficher le message d'erreur � l'utilisateur.
                 */

                throw new ValidatorException(
                        new FacesMessage( FacesMessage.SEVERITY_ERROR, MessagesErreur.EMAIL_DEJA_PRIS, null ) );

            }
        } catch ( DAOException e ) {

            /*
             * En cas d'erreur impr�vue �manant de la BDD, on pr�pare un message
             * d'erreur contenant l'exception retourn�e, pour l'afficher �
             * l'utilisateur ensuite.
             */
            FacesMessage message = new FacesMessage( FacesMessage.SEVERITY_ERROR, e.getMessage(), null );
            FacesContext facesContext = FacesContext.getCurrentInstance();
            // dans addMessage on met pas null
            /*
             * Toutefois, il est possible de n'afficher que les messages qui ne
             * sont attach�s � aucun composant d�fini, c'est-�-dire les messages
             * dont l'id est null, en utilisant l'attribut optionnel
             * globalOnly="true" : <h:messages globalOnly="true" />
             * 
             */

            /*
             * Le premier argument permet de d�finir le composant associ� au
             * message * Toutefois, il est possible de n'afficher que les
             * messages qui ne sont attach�s � aucun composant d�fini,
             * c'est-�-dire les messages dont l'id est null, en utilisant
             * l'attribut optionnel globalOnly="true" : <h:messages
             * globalOnly="true" />
             */

            facesContext.addMessage( component.getClientId( facesContext ), message );
        }

    }

}
