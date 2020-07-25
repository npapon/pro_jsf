package cookie;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieGenerateur {

    public Cookie creerCookie( HttpServletResponse response, String nom, String valeur, int duree ) {
        Cookie cookie = new Cookie( nom, valeur );
        cookie.setMaxAge( duree );
        response.addCookie( cookie );
        return cookie;
    }

    public void setCookie( Cookie cookie, String nom, String valeur, int duree, FacesContext facesContext,
            HttpServletRequest request, HttpServletResponse response ) {

        facesContext = FacesContext.getCurrentInstance();

        request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        cookie = new Cookie( nom, valeur );
        cookie.setPath( request.getContextPath() );
        cookie.setMaxAge( duree );
        response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.addCookie( cookie );
        System.out.println( cookie.getValue() );
    }

    public void setCookieTest( Cookie cookie, String nom, String valeur, int duree ) {

        cookie = new Cookie( nom, valeur );

        cookie.setMaxAge( duree );

        System.out.println( cookie.getValue() );
    }

}
