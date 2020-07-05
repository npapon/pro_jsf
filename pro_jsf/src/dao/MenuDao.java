package dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import bean.Menu;

@Stateless
public class MenuDao {

    private static final String JPQL_SELECT_MENU = "select m from Menu m where m.actif ='A' order by m.ordre asc";

    @PersistenceContext( unitName = "bdd_PU" )
    private EntityManager       em;

    public List<Menu> rechercher() throws DAOException {

        System.out.println( "em menu " + em );
        List<Menu> menuComplet = new ArrayList<Menu>();
        Query requete = em.createQuery( JPQL_SELECT_MENU );

        try {
            menuComplet = (List<Menu>) requete.getResultList();

        } catch ( NoResultException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch ( Exception e ) {
            throw new DAOException( e );

        }
        return menuComplet;
    }

}