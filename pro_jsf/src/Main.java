import javax.faces.context.FacesContext;

public class Main {

    public static void main( String[] args ) {
        // TODO Auto-generated method stub

        String version = FacesContext.class.getPackage().getImplementationVersion();

        System.out.println( version );
    }

}
