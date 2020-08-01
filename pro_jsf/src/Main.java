import javax.ejb.EJB;
import javax.servlet.http.Cookie;

import cookie.CookieGenerateur;
import dao.MenuDao;

public class Main {

    @EJB
    public static MenuDao menuDao;

    public static Cookie setCookieTest2( String nom, String valeur ) {

        return new Cookie( nom, valeur );

    }

    public static void main( String[] args ) {
        // TODO Auto-generated method stub

        Cookie cookie = setCookieTest2( "gay", "lol" );
        Cookie cookie2 = new Cookie( "test", "test" );

        CookieGenerateur cookieGenerateur = new CookieGenerateur();

        System.out.print( cookie2.getValue() );
        System.out.print( cookie );
    }

}
