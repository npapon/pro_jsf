package bean;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "imageprofil" )

public class Image {
    @Column( name = "id" )
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int       id;
    @Column( name = "libelle" )
    private String    libelle;
    @Column( name = "email" )
    private String    email;
    @Column( name = "date_creation" )
    private Timestamp date_creation;
    @Column( name = "emplacement" )
    private String    emplacement;
    @Column( name = "date_modification" )
    private Timestamp date_modification;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle( String libelle ) {
        this.libelle = libelle;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    public Timestamp getDate_creation() {
        return date_creation;
    }

    public void setDate_creation( Timestamp date_creation ) {
        this.date_creation = date_creation;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public void setEmplacement( String emplacement ) {
        this.emplacement = emplacement;
    }

    public Timestamp getDate_modification() {
        return date_modification;
    }

    public void setDate_modification( Timestamp date_modification ) {
        this.date_modification = date_modification;
    }

}
