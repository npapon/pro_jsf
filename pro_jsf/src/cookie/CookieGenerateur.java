package cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookieGenerateur {

    public Cookie creerCookie( HttpServletResponse response, String nom, String valeur, int duree ) {
        Cookie cookie = new Cookie( nom, valeur );
        cookie.setMaxAge( duree );
        response.addCookie( cookie );
        return cookie;
    }

}
