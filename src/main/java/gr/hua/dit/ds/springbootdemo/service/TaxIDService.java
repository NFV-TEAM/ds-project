package gr.hua.dit.ds.springbootdemo.service;

import gr.hua.dit.ds.springbootdemo.entity.Company;
import gr.hua.dit.ds.springbootdemo.repository.CompanyRepository;
import jakarta.persistence.Transient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
public class TaxIDService {
    @Transient
    private static final int TAX_ID_LENGTH = 9;

    @Autowired
    private CompanyRepository companyRepository;

    // Other dependencies...

    public void setUniqueTaxID(Company company) {
        Random random = new Random();
        Long generatedTaxID;

        do {
            StringBuilder taxIDBuilder = new StringBuilder();

            for (int i = 0; i < TAX_ID_LENGTH; i++) {
                taxIDBuilder.append(random.nextInt(10));
            }

            generatedTaxID = Long.parseLong(taxIDBuilder.toString());
        } while (companyRepository.existsByTaxID(generatedTaxID));

        company.setTaxID(generatedTaxID);
    }


}
