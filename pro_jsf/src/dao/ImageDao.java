package dao;

import java.sql.Timestamp;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import bean.Image;

@Stateless
public class ImageDao {
    private static final String JPQL_SELECT_PAR_EMAIL = "SELECT i FROM Image i WHERE i.email = :email";

    private static final String PARAM_EMAIL           = "email";

    @PersistenceContext( unitName = "bdd_PU" )
    private EntityManager       em;

    public Image rechercherImage( String email ) throws DAOException {
        Image imageProfil = null;

        System.out.println( "rechercherImage em imageDao " + em );
        Query requete = em.createQuery( JPQL_SELECT_PAR_EMAIL );
        requete.setParameter( PARAM_EMAIL, email );
        try {
            imageProfil = (Image) requete.getSingleResult();
        } catch ( NoResultException e ) {
            return null;
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
        return imageProfil;
    }

    public void creerImage( Image image ) throws DAOException {
        try {
            em.persist( image );
        } catch ( DAOException e ) {
        }
    }

    public void modifierImage( String email, String libelle, String emplacement, Timestamp date_modification )
            throws DAOException {

        Image imageProfil = null;
        Query requete = em.createQuery( JPQL_SELECT_PAR_EMAIL );
        requete.setParameter( PARAM_EMAIL, email );

        try {
            imageProfil = (Image) requete.getSingleResult();
        } catch ( NoResultException e ) {

        } catch ( Exception e ) {
            throw new DAOException( e );
        }
        imageProfil.setLibelle( libelle );
        imageProfil.setEmplacement( emplacement );
        imageProfil.setDate_modification( date_modification );
        em.flush();

        // update imageprofil set libelle = ?,emplacement = ?, date_modification
        // = now() where email = ?"

    }

    public void supprimerImage( String email ) throws DAOException {
        // delete from imageprofil where email = ?";

        Image imageProfil = null;
        Query requete = em.createQuery( JPQL_SELECT_PAR_EMAIL );
        requete.setParameter( PARAM_EMAIL, email );

        try {
            imageProfil = (Image) requete.getSingleResult();
        } catch ( NoResultException e ) {

        } catch ( Exception e ) {
            throw new DAOException( e );
        }

        em.remove( imageProfil );

    }

}
