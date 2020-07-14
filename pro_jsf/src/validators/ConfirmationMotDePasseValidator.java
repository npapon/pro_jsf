package validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import constante.MessagesErreur;

@FacesValidator( value = "confirmationMotDePasseValidator" )
public class ConfirmationMotDePasseValidator implements Validator {

    private static final String CHAMP_MOT_DE_PASSE = "composantMotDePasse";

    @Override
    public void validate( FacesContext context, UIComponent component, Object value ) throws ValidatorException {

        /*
         * 
         * 
         * ici dans binding="#{composantMotDePasse}" on définit cet input en
         * tant que composant mot de passe
         * 
         * <h:outputLabel for="motdepasse">Mot de passe <span class="requis"
         * >*</span></h:outputLabel> <h:inputSecret id="motdepasse"
         * value="#{inscrireBean.utilisateur.mot_de_passe}"
         * binding="#{composantMotDePasse}" size="20" maxlength="20" > <f:ajax
         * event="blur" execute="motdepasse confirmation"
         * render="motDePasseMessage confirmationMessage" /> </h:inputSecret>
         * <h:message id="motDePasseMessage" for="motdepasse"
         * errorClass="erreur" /> <br />
         * 
         * // dans la balise f:atribute dans value, on met le
         * composantMotDePasse défini plus haut ce qui permet de récupérer la
         * valeur saisie dans le champ mot de passe et de l'avoir en attribut
         * dans le champ confirmation
         * 
         * 
         * <h:outputLabel for="confirmation">Confirmation du mot de passe <span
         * class="requis" >*</span></h:outputLabel> <h:inputSecret
         * id="confirmation" value="#{inscrireBean.utilisateur.mot_de_passe}"
         * size="20" maxlength="20" > <f:ajax event="blur"
         * execute="motdepasse confirmation"
         * render="motDePasseMessage confirmationMessage"/> <f:attribute
         * name="composantMotDePasse" value="#{composantMotDePasse}" />
         * <f:validator validatorId="confirmationMotDePasseValidator" />
         * </h:inputSecret> <h:message id="confirmationMessage"
         * for="confirmation" errorClass="erreur" />
         */

        /*
         * Récupération de l'attribut mot de passe parmi la liste des attributs
         * du composant confirmation
         * 
         * <f:attribute name="composantMotDePasse"
         * value="#{composantMotDePasse}" />
         */

        UIInput composantMotDePasse = (UIInput) component.getAttributes().get( CHAMP_MOT_DE_PASSE );

        /*
         * Récupération de la valeur du champ de * <f:attribute
         * name="composantMotDePasse" value="#{composantMotDePasse}" />
         */

        String motDePasse = (String) composantMotDePasse.getValue();

        /*
         * Récupération de la valeur saisi dans le champ confirmation L'objet
         * value de la méthode valide pointe sur cet attribut value qui permet
         * de récupérer la valeur du champ saisi dans le formulaire mais aussi
         * de faire le lient avec methode inscrireBean.utilisateur.mot_de_passe
         * qui affecte cette valeur a l'objet utilisateur dans son attribut mot
         * de passe
         * 
         * h:inputSecret id="confirmation"
         * value="#{inscrireBean.utilisateur.mot_de_passe}" size="20"
         * maxlength="20" >
         */

        String confirmation = (String) value;

        if ( confirmation != null && !confirmation.equals( motDePasse ) ) {
            throw new ValidatorException(
                    new FacesMessage( FacesMessage.SEVERITY_ERROR, MessagesErreur.CONFIRMATION_MOTPASSE_INCORRECTE, null ) );
        }
    }

}
