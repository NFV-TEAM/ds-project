package gr.hua.dit.ds.springbootdemo.rest;

import gr.hua.dit.ds.springbootdemo.config.JwtUtils;
import gr.hua.dit.ds.springbootdemo.entity.Company;
import gr.hua.dit.ds.springbootdemo.entity.Request;
import gr.hua.dit.ds.springbootdemo.entity.User;
import gr.hua.dit.ds.springbootdemo.payload.request.SubmitRequest;
import gr.hua.dit.ds.springbootdemo.repository.CompanyRepository;
import gr.hua.dit.ds.springbootdemo.repository.RequestRepository;
import gr.hua.dit.ds.springbootdemo.repository.UserRepository;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/submit")
@Hidden
public class SubmitRestController {
    @Autowired
    private CompanyRepository companyRepository;


    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;


    @PostMapping("")
    public ResponseEntity<String> submitCompany(@RequestHeader("Authorization") String token, @RequestBody SubmitRequest sumbitRequest) {

        if (companyRepository.existsByName(sumbitRequest.getCompany_name())){
            System.out.println("Request has already been submitted");
            return ResponseEntity.ok("Request has already been submitted ");
        } else {
            String repUsername = jwtUtils.getUserNameFromJwtToken(token.substring(7));
            Optional<User> optRepresentative = userRepository.findByUsername(repUsername);
            User representative = optRepresentative.get();


            System.out.println(repUsername);
            System.out.println("OK");

            Company company = new Company(
                    sumbitRequest.getCompany_name(),
                    sumbitRequest.getCompany_email(),
                    null,
                    sumbitRequest.getGoal(),
                    sumbitRequest.getArticles_of_association(),
                    sumbitRequest.getHq(),
                    representative,
                    sumbitRequest.getExecutives()
            );


            System.out.println("OK");
            System.out.println(company);


            companyRepository.save(company);

            System.out.println("OK");

            LocalDateTime currentDateTime = LocalDateTime.now();
            requestRepository.save(new Request(company,"pending",new Timestamp(System.currentTimeMillis())));


            System.out.println("Request submitted successfully");
            return ResponseEntity.ok("Request submitted successfully");

        }

    }

    @GetMapping("/whoami")
    public ResponseEntity<String> whoami(@RequestHeader("Authorization") String token){
        String username = jwtUtils.getUserNameFromJwtToken(token.substring(7));
        System.out.println(username);
        return ResponseEntity.ok(username);
    }




}

