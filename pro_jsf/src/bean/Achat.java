package bean;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "baact" )
public class Achat {

    @Column( name = "ideach" )
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int       id;
    @Column( name = "numaff" )
    private int       numeroAffaire;
    @Column( name = "numach" )
    private int       numeroAchat;
    @Column( name = "indachmdd" )
    private String    verrouilleV2;
    @Column( name = "staach" )
    private char      statutAchat;
    @Column( name = "userid" )
    private String    utilisateur;
    @Column( name = "dtecre" )
    private Timestamp date_creation;
    @Column( name = "dtemod" )
    private Timestamp date_modification;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public int getNumeroAffaire() {
        return numeroAffaire;
    }

    public void setNumeroAffaire( int numeroAffaire ) {
        this.numeroAffaire = numeroAffaire;
    }

    public int getNumeroAchat() {
        return numeroAchat;
    }

    public void setNumeroAchat( int numeroAchat ) {
        this.numeroAchat = numeroAchat;
    }

    public String getVerrouilleV2() {
        return verrouilleV2;
    }

    public void setVerrouilleV2( String verrouilleV2 ) {

        this.verrouilleV2 = verrouilleV2;
    }

    public char getStatutAchat() {
        return statutAchat;
    }

    public void setStatutAchat( char statutAchat ) {
        this.statutAchat = statutAchat;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur( String utilisateur ) {

        this.utilisateur = utilisateur;
    }

    public Timestamp getDate_creation() {
        return date_creation;
    }

    public void setDate_creation( Timestamp date_creation ) {
        this.date_creation = date_creation;
    }

    public Timestamp getDate_modification() {
        return date_modification;
    }

    public void setDate_modification( Timestamp date_modification ) {
        this.date_modification = date_modification;
    }

}
