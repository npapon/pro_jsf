import java.util.List;

import javax.ejb.EJB;

import bean.Menu;
import dao.MenuDao;

public class Main {

    @EJB
    public static MenuDao menuDao;

    public static void main( String[] args ) {
        // TODO Auto-generated method stub

        int test;
        List<Menu> list = menuDao.rechercher();
    }

}
