package projekti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
        Account from = accountService.getOne("sender");
        Account to = accountService.getOne("test");
        connection.setAccountFrom(from);
        connection.setAccountTo(to);
        connectionRepository.save(connection);

        return "redirect:/profiles";
    }

    @PostMapping("/profiles/acceptRequest")
    public String acceptRequest(@RequestParam String username) {
        Account from = accountService.getOne("sender");
        Account to = accountService.getOne("test");
        ConnectionRequest connection = connectionRepository.findByAccountToAndAccountFrom(to, from);

        //connect two accounts
        to.getConnections().add(from);
        from.getConnections().add(to);
        accountService.save(to);
        connectionRepository.delete(connection);

        return "redirect:/profiles";
    }

    @PostMapping("/profiles/rejectRequest")
    public String rejectRequest(@RequestParam String username) {
        Account from = accountService.getOne("sender");
        Account to = accountService.getOne("test");
        ConnectionRequest connection = connectionRepository.findByAccountToAndAccountFrom(to, from);
        connectionRepository.delete(connection);

        return "redirect:/profiles";
    }

    @PostMapping("/profiles/disconnect/")
    public String disconnect(@RequestParam String username) {
        Account from = accountService.getOne("sender");
        Account to = accountService.getOne("test");
        to.getConnections().remove(from);
        from.getConnections().remove(to);
        accountService.save(to);
        accountService.save(from);

        return "redirect:/profiles";
    }

}
