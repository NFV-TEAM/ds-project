package gr.hua.dit.ds.springbootdemo.rest;

import gr.hua.dit.ds.springbootdemo.config.JwtUtils;
import gr.hua.dit.ds.springbootdemo.entity.Company;
import gr.hua.dit.ds.springbootdemo.entity.Request;
import gr.hua.dit.ds.springbootdemo.repository.CompanyRepository;
import gr.hua.dit.ds.springbootdemo.repository.RequestRepository;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class SumbitRestController {

    @RestController
    @RequestMapping("/api/request")
    @Hidden
    public class SubmitRestController {

        @Autowired
        private RequestRepository requestRepository;
        @Autowired
        private CompanyRepository companyRepository;

        @Autowired
        private JwtUtils jwtUtils;


    @PostMapping("/submit")
    public ResponseEntity<String> submitCompany(@RequestBody Company company) {

        if (companyRepository.existsByName(company.getName())){
            System.out.println("Request has already been submitted");
            return ResponseEntity.ok("Request has already been submitted ");
        } else {
            companyRepository.save(company);
            LocalDateTime currentDateTime = LocalDateTime.now();
            requestRepository.save(new Request(company,"pending",new Timestamp(System.currentTimeMillis())));
            System.out.println("Request submitted successfully");
            return ResponseEntity.ok("Request submitted successfully");

        }

    }
    /*
    @GetMapping("/whoami")
    public ResponseEntity<String> whoami(@RequestHeader("Authorization") String token){
        String username = jwtUtils.getUserNameFromJwtToken(token.substring(7));
        System.out.println(username);
        return ResponseEntity.ok(username);
    }


    */
}}
