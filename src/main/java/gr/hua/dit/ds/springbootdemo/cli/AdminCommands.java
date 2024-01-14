package gr.hua.dit.ds.springbootdemo.cli;

import gr.hua.dit.ds.springbootdemo.entity.User;
import gr.hua.dit.ds.springbootdemo.enums.UserRole;
import gr.hua.dit.ds.springbootdemo.repository.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.shell.ExitRequest;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@ShellComponent
public class AdminCommands {

    private boolean loggedIn = false;

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder encoder;

    //Uses console for inputs- if not available then utils.Scanner
    InputReader inputReader = new InputReader();


    @ShellMethod("Log in as an admin")
    public String login() {
        if (loggedIn) {
            return "Already logged in.";
        }

        String username = inputReader.readLine("Enter username: ");
        String password = inputReader.readPassword("Enter password for " + username + ": ");

        if (adminUsername.equals(username) && adminPassword.equals(password)) {
            loggedIn = true;
            return "Successfully logged in as admin.";
        } else {
            return "Invalid credentials. Please try again.";
        }
    }

    @ShellMethod("Logout")
    public String logout() {
        loggedIn = false;
        return "Logged out.";
    }

    /*
    @ShellMethod("exit application")
    public void exit() {
        System.exit(0);
    }


     */

    @ShellMethod("List registered users")
    public void list(){

        //(@ShellOption(value = "--role", defaultValue = "")String role){

        if (!loggedIn) {
            System.out.println("Please log in first.");
            return;
        }

        userRepository.findAll().forEach(user -> {
            System.out.println("|ID|:" + user.getId() + " |usrn|:" + user.getUsername() + " |email|:" + user.getEmail() + "|r|:" + user.getRole());
        });
    }

    @ShellMethod("Register new User")
    public String register(
            @ShellOption(value={"-u","--username"},help = "Username") String username,
            @ShellOption(value={"-p","--password"},help = "Password") String password,
            @ShellOption(value={"-e","--email"},help = "Email") String email,
            @ShellOption(value={"-r","--role"},help = "Role") String role
    ) {
        if (!loggedIn) {return "Please log in first.";}

        if(userRepository.existsByUsername(username)){return "a user with this username already exists";}
        if(userRepository.existsByEmail(email)){return "a user with this email address already exists";}

        UserRole userRole ;
        try {
            userRole = UserRole.valueOf(role);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid UserRole: " + role);
            return "Valid user roles : ROLE_TAXMAN, ROLE_REPRESENTATIVE";
        }

        User user = new User(username,
                email,
                encoder.encode(password),
                userRole);

        try{
            userRepository.save(user);
        } catch (ConstraintViolationException e){
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            for (ConstraintViolation<?> violation : violations) {
                System.out.println("Validation error: " + violation.getMessage());
            }
            return "User registration failed due to validation errors.";
        }

    return "Successfully registered user: "+username;
    }

    @ShellMethod("Delete registered user by id")
    public String delete(@ShellOption(value={"--id"},help = "ID") long ID){

        if (!loggedIn) {return "Please log in first.";}

        Optional<User> userToDelete = userRepository.findById(ID);

        if(userToDelete.isEmpty()){
            return "no registered user matching ID provided";
        }

        String confirmation = inputReader.readLine("confirm user to be deleted :"+ userToDelete.get()+System.lineSeparator()+"[yes/no]");

        if(confirmation.equals("yes")){
            userRepository.deleteById(ID);
            return "user "+userToDelete.get().getUsername()+" deleted ";
        } else if(confirmation.equals("no")){
            return "user deletion cancelled";

        }else return "invalid input";
    }


    @ShellMethod("Edit user data by id")
    public String edit(
            @ShellOption(value = {"-i", "--id"}, help = "User ID") Long ID,
            @ShellOption(value = {"-u", "--username"}, help = "New username", defaultValue = "") String newUsername,
            @ShellOption(value = {"-e", "--email"}, help = "New email", defaultValue = "") String newEmail,
            @ShellOption(value = {"-r", "--role"}, help = "New role", defaultValue = "") String newRole,
            @ShellOption(value = {"-p", "--password"}, help = "New password", defaultValue = "") String newPassword
    ) {
        if (!loggedIn) {return "Please log in first.";}

        // Find the user by ID
        Optional<User> optionalUser = userRepository.findById(ID);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Update user data
            if (!newUsername.isEmpty()) {
                user.setUsername(newUsername);
            }

            if (!newEmail.isEmpty()) {
                user.setEmail(newEmail);
            }
            if (!newRole.isEmpty()){
                UserRole userRole ;
                try {
                    userRole = UserRole.valueOf(newRole);
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid UserRole: " + newRole);
                    return "Valid user roles : ROLE_TAXMAN, ROLE_REPRESENTATIVE";
                }
                user.setRole(userRole);
            }
            if (!newPassword.isEmpty()){
                user.setPassword(encoder.encode(newPassword));
            }

            // Save the updated user
            userRepository.saveAndFlush(user);

            return "User data updated successfully: " + user;
        } else {
            return "User not found with ID: " + ID;
        }
    }
}

