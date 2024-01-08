package gr.hua.dit.ds.springbootdemo.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;
    @OneToOne
    @JoinColumn(name = "company_id", referencedColumnName = "Id")
    private Company company;
    @Column
    private String state;
    @Column
    private Timestamp timestamp;


    public Request() {
    }



    public Request(Company company, String state, Timestamp timestamp) {
        this.id = id;
        this.company = company;
        this.state = state;
        this.timestamp = timestamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", company=" + company +
                ", state='" + state + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
