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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/myrequest")
@Hidden
public class RepresentativeRequestRestController {
    @Autowired
    private CompanyRepository companyRepository;


    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("")
    public ResponseEntity<Object> checkMyRequest(@RequestHeader("Authorization") String token, @RequestBody Map<String, String> requestBody) {

        int repId = Integer.parseInt(requestBody.get("id"));

        Optional<Company> company = companyRepository.findByRepresentative_id(repId);

        if (!company.isPresent()) {
            return ResponseEntity.notFound().build(); // Company not found
        }

        Optional<Request> request = requestRepository.findByCompany_id(company.get().getId());

        if (!request.isPresent()) {
            return ResponseEntity.notFound().build(); // Request not found
        }

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("request", request.get());

        return ResponseEntity.ok(responseData);
    }
        }







