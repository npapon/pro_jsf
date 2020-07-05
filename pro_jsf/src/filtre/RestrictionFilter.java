package filtre;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import constante.Adressesinternes;
import constante.AttributsServlet;

@WebFilter( urlPatterns = { "/profil", "/effacermoncompte", "/libererachatv2", "/syntesescriptachatliberev2" } )
public class RestrictionFilter implements Filter {

    public void init( FilterConfig config ) throws ServletException {

    }

    public void doFilter( ServletRequest req, ServletResponse resp, FilterChain chain ) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession session = request.getSession();

        if ( session.getAttribute( AttributsServlet.SESSIONACTIVE ) == null ) {
            response.sendRedirect( Adressesinternes.CONNEXION_COURT );
        }

        else {
            chain.doFilter( req, resp );
        }
    }

    public void destroy() {

    }

}
