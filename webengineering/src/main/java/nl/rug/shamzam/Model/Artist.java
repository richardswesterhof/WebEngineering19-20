package nl.rug.shamzam.Model;

import javax.persistence.*;

@Entity
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int dbId;

    private String id;
    private float familiarity;
    private float hottness;
    private float latitude;
    private int location;
    private float longitude;
    private String name;
    private float similar;
    private String terms;
}
