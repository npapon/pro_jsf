package dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import bean.Utilisateur;
import constante.Cryptage;

@Stateless
public class UtilisateurDao {
    private static final String JPQL_SELECT_PAR_EMAIL = "SELECT u FROM Utilisateur u WHERE u.email=:email";
    private static final String PARAM_EMAIL           = "email";
    private static final String JPQL_SELECT_PAR_LOGIN = "SELECT u FROM Utilisateur u WHERE u.login=:login";
    private static final String PARAM_LOGIN           = "login";

    @PersistenceContext( unitName = "bdd_PU" )
    private EntityManager       em;

    public void creer( Utilisateur utilisateur ) throws DAOException {
        try {
            em.persist( utilisateur );
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }

    public Utilisateur trouver( String email ) throws DAOException {
        Utilisateur utilisateur = null;
        System.out.println( "trouver utilisateur em " + em );
        Query requete = em.createQuery( JPQL_SELECT_PAR_EMAIL );

        requete.setParameter( PARAM_EMAIL, email );
        try {
            utilisateur = (Utilisateur) requete.getSingleResult();
        } catch ( NoResultException e ) {
            return null;
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
        return utilisateur;
    }

    public Utilisateur rechercherSession( String login, String motDePasse ) throws DAOException {
        Utilisateur session = null;

        System.out.println( "rechercher session em " + em );
        Query requete = em.createQuery( JPQL_SELECT_PAR_LOGIN );
        requete.setParameter( PARAM_LOGIN, login );
        try {
            session = (Utilisateur) requete.getSingleResult();
        }

        catch ( NoResultException e ) {
            return null;
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
        // TODO Auto-generated method stub
        // on vérifie que le mot de passe crypté en base et celui tapé danbs le
        // champ mot de passe :
        ConfigurablePasswordEncryptor configurablePasswordEncryptor = new ConfigurablePasswordEncryptor();
        configurablePasswordEncryptor.setAlgorithm( Cryptage.ALGO_CHIFFREMENT_SHA_256 );
        configurablePasswordEncryptor.setPlainDigest( false );
        if ( session != null && configurablePasswordEncryptor.checkPassword( motDePasse, session.getMot_de_passe() ) ) {

            return session;
        } else {

            return null;
        }
    }

    public void supprimerUtilisateur( String email ) throws DAOException {
        // delete from utilisateur where email = ?";

        Utilisateur utilisateur = null;
        System.out.println( "em supprimer user " + em );
        Query requete = em.createQuery( JPQL_SELECT_PAR_EMAIL );
        requete.setParameter( PARAM_EMAIL, email );
        try {
            utilisateur = (Utilisateur) requete.getSingleResult();
        } catch ( NoResultException e ) {

        } catch ( Exception e ) {
            throw new DAOException( e );
        }

        em.remove( utilisateur );

    }
}