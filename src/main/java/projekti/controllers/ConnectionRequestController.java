package projekti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import projekti.models.Account;
import projekti.services.AccountService;
import projekti.services.ConnectionService;

@Controller
public class ConnectionRequestController {

    @Autowired
    private ConnectionService connectionService;

    @Autowired
    private AccountService accountService;

    @PostMapping("/profiles/{username}/sendRequest")
    public String sendRequest(@PathVariable String username) {
        Account from = accountService.getActiveAccount();
        Account to = accountService.getOne(username);

        if (connectionService.requestExists(from, to) && connectionService.connectionExists(from, to)) {
            connectionService.sendRequest(from, to);
        }

        return "redirect:/profiles/";
    }

    @PostMapping("/profiles/{username}/acceptRequest")
    public String acceptRequest(@PathVariable String username) {
        Account from = accountService.getOne(username);
        Account to = accountService.getActiveAccount();

        //connect
        connectionService.connectAccounts(from, to);
        //update connection
        accountService.update(to);
        //delete request
        connectionService.deleteRequest(to, from);

        return "redirect:/profiles/" + to.getUsername();
    }

    @PostMapping("/profiles/{username}/rejectRequest")
    public String rejectRequest(@PathVariable String username) {
        Account from = accountService.getOne(username);
        Account to = accountService.getActiveAccount();
        connectionService.deleteRequest(to, from);

        return "redirect:/profiles/" + to.getUsername();
    }

    @PostMapping("/profiles/{username}/disconnect")
    public String disconnect(@PathVariable String username) {
        Account from = accountService.getActiveAccount();
        Account to = accountService.getOne(username);

        connectionService.disconnectAccounts(from, to);
        accountService.update(to);
        accountService.update(from);

        return "redirect:/profiles/" + from.getUsername();
    }
}
