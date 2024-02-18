package gr.hua.dit.ds.springbootdemo.repository;

import gr.hua.dit.ds.springbootdemo.entity.Company;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
@Hidden
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Boolean existsByName(String name);
    Boolean existsByTaxID(Long taxID);

    Optional <Company> findByRepresentative_id(Integer representative_id);

    Optional<Company> findById(Integer id);
}
