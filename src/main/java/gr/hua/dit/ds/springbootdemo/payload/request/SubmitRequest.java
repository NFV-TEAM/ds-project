package gr.hua.dit.ds.springbootdemo.payload.request;

import gr.hua.dit.ds.springbootdemo.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SubmitRequest {
    @NotBlank
    @Size(min = 3, max = 40)
    private String company_name;

    @NotBlank
    @Size(max = 50)
    @Email
    private String company_email;

    @NotBlank
    private String goal;

    @NotBlank
    private String articles_of_association;

    @NotBlank
    private String hq;

    @NotBlank
    private String executives;

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_email() {
        return company_email;
    }

    public void setCompany_email(String company_email) {
        this.company_email = company_email;
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

    public String getExecutives() {
        return executives;
    }

    public void setExecutives(String executives) {
        this.executives = executives;
    }
}