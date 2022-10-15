package com.alkemy.Disney.controllers;

import com.alkemy.Disney.DTO.*;
import com.alkemy.Disney.Service.*;
import com.alkemy.Disney.email.ValidatorEmail;
import com.alkemy.Disney.email.VerificationEmail;
import com.alkemy.Disney.models.Client;
import com.alkemy.Disney.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ClientController {

    public  final ClientService clientService;
    // public final MoSeCharacterService moSeCharacterService;
   // public final GenderService genderService;
    public final PasswordEncoder passwordEncoder;
    private final VerificationEmail verificationEmail;
    private final ValidatorEmail validatorEmail;

   // @GetMapping("/clients")
   // public List<ClientDTO> getClients() {
    //    return clientService.getClients().stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
   // }
    //@GetMapping("/moSeCharacter")
   // public Set<MoSeCharacterDTO> getMoSeCharacter(){
    //    return moSeCharacterService.getMoSeCharacter().stream().map(moSeCharacter -> new MoSeCharacterDTO(moSeCharacter)).collect(Collectors.toSet());
   // }
   // @GetMapping("/gender")
   //    return genderService.getGenders().stream().map(gender -> new GenderDTO(gender)).collect(Collectors.toSet());
    //}
    @PostMapping("/auth/register")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {

        if (firstName.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (lastName.isEmpty() ) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (email.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (clientService.findUserByEmail(email) != null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }
        if (!validatorEmail.test(email)) {
            return new ResponseEntity<>("invalid mail", HttpStatus.FORBIDDEN);
        }
        if(email.contains("admin@disney.com")){
            return new ResponseEntity<>("Invalid user", HttpStatus.FORBIDDEN);
        }
        Client newClient = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientService.saveClient(newClient);

        String mailSubject = "Welcome to Disney";
        String mailBody = "Hey! Thank you for registering, welcome to the world of disney!";
        verificationEmail.sendEmail(newClient.getEmail(), mailSubject, mailBody);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
