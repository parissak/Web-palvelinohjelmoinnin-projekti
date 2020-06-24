package projekti.controllers;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.models.Account;
import projekti.models.Message;
import projekti.models.MessageComment;
import projekti.services.AccountService;
import projekti.services.MessageService;

@Controller
public class MessageController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    //return users messages
    @GetMapping("/profiles/{username}/messages")
    public String getProfileMessages(Model model) {
        Account account = accountService.getActiveAccount();
        model.addAttribute("posts", messageService.findAssociatedTopSortedMessages(account));
        model.addAttribute("account", account);
        return "posts";
    }

    //post new message
    @PostMapping("/profiles/username/messages/sendMessage")
    public String postMessage(@RequestParam String messageText) {
        Account account = accountService.getActiveAccount();
        messageService.saveMessage(messageText, account);

        return "redirect:/profiles/" + account.getUsername() + "/messages";
    }

    //like message
    @PostMapping("/profiles/username/messages/{messageId}/like")
    public String likeMessage(@PathVariable Long messageId) {
        Account account = accountService.getActiveAccount();
        Message message = messageService.getOneMessage(messageId);

        if (!message.getLiker().contains(account)) {
            message.getLiker().add(account);
            messageService.updateMessage(message);
        }

        return "redirect:/profiles/" + account.getUsername() + "/messages";
    }

    //comment message
    @PostMapping("/profiles/username/messages/{messageId}/comment")
    public String commentMessage(@PathVariable Long messageId, @RequestParam String description) {
        Account account = accountService.getActiveAccount();
        Message message = messageService.getOneMessage(messageId);
        messageService.saveComment(description, account, message);

        return "redirect:/profiles/" + account.getUsername() + "/messages";
    }

    //redirect to active users messages
    @GetMapping("/profiles/profile/messages")
    public String getMyProfile(@ModelAttribute Account account) {
        String activeUsername = accountService.getActiveAccount().getUsername();
        return "redirect:/profiles/" + activeUsername + "/messages";
    }
}
