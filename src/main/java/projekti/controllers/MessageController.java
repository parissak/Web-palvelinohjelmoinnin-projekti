package projekti.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
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
import projekti.repositories.MessageRepository;
import projekti.services.AccountService;

@Controller
public class MessageController {

    @Autowired
    private AccountService accountService;

    @Autowired
    MessageRepository messageRepository;

    //return users messages
    @GetMapping("/profiles/profile/messages")
    public String getProfileMessages(Model model) {
        String username = accountService.getActiveAccount();
        Account account = accountService.getOne(username);
        List<Message> posts = messageRepository.findTop25ByPosterOrPosterInOrderByTimestampDesc(account, account.getConnections());

        model.addAttribute("posts", posts);
        model.addAttribute("account", account);
        return "posts";
    }

    //post new message
    @PostMapping("/profiles/{username}/messages")
    public String postMessage(@PathVariable String username, @RequestParam String messageText) {
        Account account = accountService.getOne(username);
        Message message = new Message();
        message.setMessage(messageText);
        message.setTimestamp(LocalDateTime.now());
        message.setPoster(account);
        messageRepository.save(message);
        return "redirect:/profiles/profile/messages";
    }

    @PostMapping("/profiles/username/messages/{messageId}/like")
    public String likeMessage(@PathVariable Long messageId) {
        String username = accountService.getActiveAccount();
        Account account = accountService.getOne(username);
        Message message = messageRepository.getOne(messageId);
        
        if (!message.getLiker().contains(account)) {
            message.getLiker().add(account);
            messageRepository.save(message);
        }

        return "redirect:/profiles/profile/messages";
    }

}
