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

import constante.Adressesinternes;

@WebFilter( urlPatterns = { "/profil.xhtml", "/libererachatv2.xhtml" } )
public class RestrictionFilter implements Filter {

    public void init( FilterConfig config ) throws ServletException {

    }

    public void doFilter( ServletRequest req, ServletResponse resp, FilterChain chain ) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        System.out.println( "COCO" + request.getSession( false ) );
        System.out.println( "coconecte" + request.getSession( false ).getAttribute( "connecte" ) );

        if ( request.getSession( false ).getAttribute( "connecte" ) == null ) {
            response.sendRedirect( Adressesinternes.CONNEXION_COURT );
        }

        else {
            chain.doFilter( req, resp );
        }
    }

    public void destroy() {

    }

}
