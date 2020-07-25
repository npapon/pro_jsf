package bean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean( eager = true )
@ApplicationScoped
public class App {

    @PostConstruct
    public void startup() {

        System.out.println( "Start Baby" ); // ...
    }

    @PreDestroy
    public void shutdown() {
        System.out.println( "Finish Baby" ); // ...
    }
}