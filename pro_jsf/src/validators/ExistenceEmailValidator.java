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
     * un objet de type FacesContext qui permet d'accéder au contexte de
     * l'application jsf
     *//*
        * un objet de type UICOmponent qui contient une référence sur le
        * composant dont la donnée est à valider
        */
    /*
     * un objet de type Object qui encapsule la valeur de la données à valider.
     * Le traiement va s'appliquer sur l'attribut value du champ concerné
     */
    public void validate( FacesContext context, UIComponent component, Object value ) throws ValidatorException {
        /* Récupération de la valeur à traiter depuis le paramètre value */
        String email = (String) value;
        try {
            if ( utilisateurDao.trouver( email ) != null ) {
                /*
                 * Si une adresse est retournée, alors on envoie une exception
                 * propre à JSF, qu'on initialise avec un FacesMessage de
                 * gravité "Erreur" et contenant le message d'explication. Le
                 * framework va alors gérer lui-même cette exception et s'en
                 * servir pour afficher le message d'erreur à l'utilisateur.
                 */

                throw new ValidatorException(
                        new FacesMessage( FacesMessage.SEVERITY_ERROR, MessagesErreur.EMAIL_DEJA_PRIS, null ) );

            }
        } catch ( DAOException e ) {

            /*
             * En cas d'erreur imprévue émanant de la BDD, on prépare un message
             * d'erreur contenant l'exception retournée, pour l'afficher à
             * l'utilisateur ensuite.
             */
            FacesMessage message = new FacesMessage( FacesMessage.SEVERITY_ERROR, e.getMessage(), null );
            FacesContext facesContext = FacesContext.getCurrentInstance();
            // dans addMessage on met pas null
            /*
             * Toutefois, il est possible de n'afficher que les messages qui ne
             * sont attachés à aucun composant défini, c'est-à-dire les messages
             * dont l'id est null, en utilisant l'attribut optionnel
             * globalOnly="true" : <h:messages globalOnly="true" />
             * 
             */

            /*
             * Le premier argument permet de définir le composant associé au
             * message * Toutefois, il est possible de n'afficher que les
             * messages qui ne sont attachés à aucun composant défini,
             * c'est-à-dire les messages dont l'id est null, en utilisant
             * l'attribut optionnel globalOnly="true" : <h:messages
             * globalOnly="true" />
             */

            facesContext.addMessage( component.getClientId( facesContext ), message );
        }

    }

}
