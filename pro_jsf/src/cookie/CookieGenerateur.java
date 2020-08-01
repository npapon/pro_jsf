package cookie;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieGenerateur {

    public Cookie setCookie( Cookie cookie, String nom, String valeur, int duree ) {

        FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        cookie = new Cookie( nom, valeur );
        cookie.setPath( request.getContextPath() );
        cookie.setMaxAge( duree );
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.addCookie( cookie );
        return new Cookie( nom, valeur );
    }

}
