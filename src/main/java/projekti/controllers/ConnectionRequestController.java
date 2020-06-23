package projekti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.models.Account;
import projekti.models.ConnectionRequest;
import projekti.services.AccountService;
import projekti.repositories.ConnectionRequestRepository;

@Controller
public class ConnectionRequestController {

    @Autowired
    ConnectionRequestRepository connectionRepository;

    @Autowired
    AccountService accountService;

    @PostMapping("/profiles/sendRequest/{username}")
    public String sendRequest(@PathVariable String username) {
        ConnectionRequest connection = new ConnectionRequest();
        String activeUser = accountService.getActiveAccount();
        Account from = accountService.getOne(activeUser);
        Account to = accountService.getOne(username);
        connection.setAccountFrom(from);
        connection.setAccountTo(to);
        connectionRepository.save(connection);
        return "redirect:/profiles";
    }

    @PostMapping("/profiles/acceptRequest")
    public String acceptRequest(@RequestParam String username) {
        Account from = accountService.getOne(username);
        String activeUser = accountService.getActiveAccount();
        Account to = accountService.getOne(activeUser);
        ConnectionRequest connection = connectionRepository.findByAccountToAndAccountFrom(to, from);

        //connect two accounts
        to.getConnections().add(from);
        from.getConnections().add(to);
        accountService.update(to);
        connectionRepository.delete(connection);

        return "redirect:/profiles";
    }

    @PostMapping("/profiles/rejectRequest")
    public String rejectRequest(@RequestParam String username) {
        Account from = accountService.getOne(username);
        String activeUser = accountService.getActiveAccount();
        Account to = accountService.getOne(activeUser);
        ConnectionRequest connection = connectionRepository.findByAccountToAndAccountFrom(to, from);
        connectionRepository.delete(connection);

        return "redirect:/profiles";
    }

    @PostMapping("/profiles/disconnect/")
    public String disconnect(@RequestParam String username) {
        String activeUser = accountService.getActiveAccount();
        Account from = accountService.getOne(activeUser);
        Account to = accountService.getOne(username);
        to.getConnections().remove(from);
        from.getConnections().remove(to);
        accountService.update(to);
        accountService.update(from);

        return "redirect:/profiles";
    }

}
