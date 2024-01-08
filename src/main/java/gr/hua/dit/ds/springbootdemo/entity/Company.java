package gr.hua.dit.ds.springbootdemo.entity;
import jakarta.persistence.*;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer Id;
    @Column
    private String name;
    @Column
    private String email;
    @Column(unique = true,nullable = true)
    private String taxID;
    @Column
    private String goal;
    @Column
    private String articles_of_association;
    @Column
    private String hq;
    @OneToOne
    @JoinColumn(name = "representative_id", referencedColumnName = "id")
    private User representative;
    @Column
    private String executives;


    public Company() {
        // Default constructor is required by Hibernate
    }

    public Company(Integer id, String name, String email, String goal, String articles_of_association, String hq) {
        Id = id;
        this.name = name;
        this.email = email;
        this.taxID = taxID;
        this.goal = goal;
        this.articles_of_association = articles_of_association;
        this.hq = hq;
    }


    public Company(String name, String email, String taxID, String goal, String articles_of_association, String hq, User representative, String executives) {
        this.name = name;
        this.email = email;
        this.taxID = taxID;
        this.goal = goal;
        this.articles_of_association = articles_of_association;
        this.hq = hq;
        this.representative = representative;
        this.executives = executives;
    }

    public Company(String name, String email, String goal, String articles_of_association, String hq) {
        this.name = name;
        this.email = email;
        this.taxID = taxID;
        this.goal = goal;
        this.articles_of_association = articles_of_association;
        this.hq = hq;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTaxID() {
        return taxID;
    }

    public void setTaxID(String taxID) {
        this.taxID = taxID;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getArticles_of_association() {
        return articles_of_association;
    }

    public void setArticles_of_association(String articles_of_association) {
        this.articles_of_association = articles_of_association;
    }

    public String getHq() {
        return hq;
    }

    public void setHq(String hq) {
        this.hq = hq;
    }


    public User getRepresentative() {
        return representative;
    }

    public String getExecutives() {
        return executives;
    }

    public void setRepresentative(User representative) {
        this.representative = representative;
    }

    public void setExecutives(String executives) {
        this.executives = executives;
    }



    @Override
    public String toString() {
        return "Company{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", taxID='" + taxID + '\'' +
                ", goal='" + goal + '\'' +
                ", articles_of_association='" + articles_of_association + '\'' +
                ", hq='" + hq + '\'' +
                '}';
    }


}
