package gr.hua.dit.ds.springbootdemo.rest;

        import gr.hua.dit.ds.springbootdemo.config.JwtUtils;
        import gr.hua.dit.ds.springbootdemo.entity.Company;
        import gr.hua.dit.ds.springbootdemo.entity.Request;
        import gr.hua.dit.ds.springbootdemo.repository.CompanyRepository;
        import gr.hua.dit.ds.springbootdemo.repository.RequestRepository;
        import gr.hua.dit.ds.springbootdemo.service.TaxIDService;
        import io.swagger.v3.oas.annotations.Hidden;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.ResponseEntity;
        import org.springframework.security.access.prepost.PreAuthorize;
        import org.springframework.web.bind.annotation.*;

        import java.sql.Timestamp;
        import java.time.LocalDateTime;
        import java.util.List;
        import java.util.Map;



@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/request")
@Hidden
public class RequestRestController {


    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private RequestRepository requestRepository;

    //@PreAuthorize("hasRole("R")")
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/pending")
    public List<Request> getPendingReq(){
        return requestRepository.findByState("pending");

    }

    @Autowired
    private TaxIDService taxIDService;

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/{id}")
    public Request getReqByID(@PathVariable Integer id) {
        return requestRepository.findById(id);
    }


    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/review/{id}")
    public ResponseEntity<String> reviewReqById(@PathVariable Integer id,@RequestBody Map<String, String> requestBody){
        Request requestToChange = requestRepository.findById(id);
        String action = requestBody.get("action");

        //check that request is on pending list
        if (!requestRepository.findByState("pending").contains(requestToChange)){
            return ResponseEntity.badRequest().body("Action has already been taken for request #"+id);
        }

        //check for correct action
        if (!"approved".equals(action) && !"denied".equals(action)) {
            return ResponseEntity.badRequest().body("Invalid action");
        }

        requestToChange.setState(action);
        Integer companyID = requestToChange.getCompany().getId();
        Company company = companyRepository.findById(companyID).get();
        if(action.equals("approved")){
            taxIDService.setUniqueTaxID(company);
        }

        requestRepository.saveAndFlush(requestToChange); // Merge changes with managed entity

        return ResponseEntity.ok("Request status updated to: " + action);
    }


}

