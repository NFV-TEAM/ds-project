package gr.hua.dit.ds.springbootdemo.repository;

import gr.hua.dit.ds.springbootdemo.entity.Request;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Hidden
public interface RequestRepository extends JpaRepository<Request, Long> {


    List<Request> findByState(String state);

    Request findById(Integer id);

    Optional <Request> findByCompany_id(Integer company_id);
}
