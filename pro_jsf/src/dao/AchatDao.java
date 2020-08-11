package dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import bean.Achat;

@Stateless
public class AchatDao {

    private static final String JPQL_SELECT_ACHAT_BLOQUE  = "SELECT b FROM Achat b where b.verrouilleV2 in ('O','N')";
    private static final String JPQL_SELECT_ACHAT         = "SELECT b FROM Achat b where b.id = :ideach";
    private static final String JPQL_UPDATE_ACHAT_BLOQUES = "UPDATE Achat a set a.verrouilleV2 = null, a.utilisateur = :userid, a.date_modification = :dtemod where a.id = :ideach";

    private static final String PARAM_IDEACH              = "ideach";
    private static final String PARAM_USERID              = "userid";
    private static final String PARAM_DTEMOD              = "dtemod";

    @PersistenceContext( unitName = "bdd_PU" )
    private EntityManager       em;

    // select * from baact where INDACHMDD in ('O','N')
    public List<Achat> rechercherAchatsBloquesV2() throws DAOException {
        Achat achat = null;
        List<Achat> achatsBloquesV2 = new ArrayList<Achat>();

        Query requete = em.createQuery( JPQL_SELECT_ACHAT_BLOQUE );

        try {
            achatsBloquesV2 = requete.getResultList();
        } catch ( NoResultException e ) {
            return null;
        } catch ( Exception e ) {
            throw new DAOException( e );
        }

        return achatsBloquesV2;
    }

    // "update baact set INDACHMDD = null, USERID= ? ,DTEMOD = now() where
    // IDEACH = ?"
    public void modifierAchatBloqueV2( int identifiantAchat, String nomUtilisateur, Timestamp dateModification )
            throws DAOException {

        Achat achat = null;

        try {
            achat = (Achat) em.find( Achat.class, identifiantAchat );
            System.out.println( "passe par là achat.setUtilisateur" );
            achat.setUtilisateur( nomUtilisateur );
            System.out.println( " et le nom utilisateur est " + achat.getUtilisateur() );
            System.out.println( "passe par là achat.dateModification" );
            achat.setDate_modification( dateModification );
            System.out.println( " et la date de modif  est " + achat.getDate_modification() );
            System.out.println( "passe par là achat.setVerrouilleV2" );
            achat.setVerrouilleV2( null );
            System.out.println( " et la verrou vaut " + achat.getVerrouilleV2() );

            System.out.println( "achat modifié" + achat.toString() );
            em.flush();

        } catch ( NoResultException e ) {
        } catch ( Exception e ) {
            throw new DAOException( e );
        }

    }

    public void modifierAchatBloqueV3( int identifiantAchat, String nomUtilisateur, Timestamp dateModification )
            throws DAOException {

        Achat achat = null;

        Query query = em
                .createQuery(
                        JPQL_UPDATE_ACHAT_BLOQUES );

        query.setParameter( PARAM_USERID, nomUtilisateur );
        query.setParameter( PARAM_IDEACH, identifiantAchat );

        query.setParameter( PARAM_DTEMOD, dateModification );
        int rowsUpdated = query.executeUpdate();
        System.out.println( "entities Updated: " + rowsUpdated );

    }

}
