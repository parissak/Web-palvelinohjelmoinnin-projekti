package projekti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.models.Account;
import projekti.models.ConnectionRequest;
import projekti.services.AccountService;
import projekti.services.ConnectionService;

@Controller
public class ConnectionRequestController {

    @Autowired
    private ConnectionService connectionService;

    @Autowired
    private AccountService accountService;

    @PostMapping("/profiles/sendRequest/{username}")
    public String sendRequest(@PathVariable String username) {
        String activeUser = accountService.getActiveAccount();
        Account from = accountService.getOne(activeUser);
        Account to = accountService.getOne(username);
        connectionService.sendRequest(from, to);

        return "redirect:/profiles";
    }

    @PostMapping("/profiles/acceptRequest")
    public String acceptRequest(@RequestParam String username) {
        Account from = accountService.getOne(username);
        String activeUser = accountService.getActiveAccount();
        Account to = accountService.getOne(activeUser);

        //connect
        connectionService.connectAccounts(from, to);
        //update connection
        accountService.update(to);
        //delete request
        connectionService.deleteRequest(to, from);

        return "redirect:/profiles";
    }

    @PostMapping("/profiles/rejectRequest")
    public String rejectRequest(@RequestParam String username) {
        Account from = accountService.getOne(username);
        String activeUser = accountService.getActiveAccount();
        Account to = accountService.getOne(activeUser);
        connectionService.deleteRequest(to, from);

        return "redirect:/profiles";
    }

    @PostMapping("/profiles/disconnect/")
    public String disconnect(@RequestParam String username) {
        String activeUser = accountService.getActiveAccount();
        Account from = accountService.getOne(activeUser);
        Account to = accountService.getOne(username);
        
        connectionService.disconnectAccounts(from, to);
        accountService.update(to);
        accountService.update(from);

        return "redirect:/profiles";
    }

}
