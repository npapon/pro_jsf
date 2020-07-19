package bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import dao.MenuDao;

//permet de pr�ciser au serveur que ce bean est dor�navant g�r� par JSF
//le nom du bean correspond au nom de la classe, la majuscule en moins :
//genererMenu
@ManagedBean
// permet de pr�ciser au serveur que ce bean a pour port�e la requ�te
@RequestScoped
public class GenererMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Menu>        menuComplet;

    // Injection de notre EJB (Session Bean Stateless)
    // Notre DAO Menu avec JPA est un simple EJB Stateless,
    // qui est inject� (initi�) automatiquement via l'annotation @EJB
    @EJB
    private MenuDao           menuDao;

    public GenererMenu() {

    }

    @PostConstruct
    public void init() {
        menuComplet = menuDao.rechercher();
    }

    public void inscrire() {

        menuDao.rechercher();
    }

    public List<Menu> getMenuComplet() {
        return menuComplet;
    }

    public void setMenuComplet( List<Menu> menuComplet ) {
        this.menuComplet = menuComplet;
    }

}
