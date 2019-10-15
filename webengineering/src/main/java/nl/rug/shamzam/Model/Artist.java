package nl.rug.shamzam.Model;

import javax.persistence.*;

@Entity
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int dbId;

    private String id;
    private float familiarity;
    private float hotness;
    private float latitude;
    private int location;
    private float longitude;
    private String name;
    private float similar;
    private String terms;

    public String getName(){return this.name;}
    public String getTerms(){return this.terms;}
    public float getHottness(){return this.hotness;}
    public String getId(){return this.id;}
    public int getDbId(){return this.dbId;}
}
