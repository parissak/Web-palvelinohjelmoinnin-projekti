package projekti.controllers;

import java.io.IOException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import projekti.models.Account;
import projekti.models.Skill;
import projekti.services.AccountPhotoService;

import projekti.services.AccountService;
import projekti.services.SkillService;
import projekti.services.ConnectionService;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private ConnectionService connectionService;

    @Autowired
    private AccountPhotoService accountPhotoService;

    //list all profiles
    @GetMapping("/profiles")
    public String list(Model model) {
        model.addAttribute("accounts", accountService.findAll());
        return "profiles";
    }

    //search by username and return matches
    @PostMapping("/profiles")
    public String findUsersByUsername(@RequestParam String username, Model model) {
        model.addAttribute("accounts", accountService.getByUsername(username));
        return "profiles";
    }

    //get one profile
    @GetMapping("/profiles/{username}")
    public String getOneProfile(@ModelAttribute Skill skill, @PathVariable String username, Model model) {
        Account account = accountService.getOne(username);
        model.addAttribute("account", account);
        model.addAttribute("skills", skillService.listSortedSkills(account));

        Account activeUser = accountService.getActiveAccount();
        if (account.getUsername().equals(activeUser.getUsername())) {
            model.addAttribute("requests", connectionService.getConnectionRequests(account));
        }
        return "wall";
    }

    //post new account
    @PostMapping("/register")
    public String postAccount(@Valid @ModelAttribute Account account, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        if (!accountService.isUniqueUsername(account.getUsername())) {
            bindingResult.rejectValue("username", "error.username", "Username exists, please choose another");
            return "register";
        }
        accountService.create(account);
        return "redirect:/profiles/mypage";
    }

    //return registerform
    @GetMapping("/register")
    public String getRegisterForm(@ModelAttribute Account account) {
        return "register";
    }

    //return wall
    @GetMapping("/profiles/mypage")
    public String getMyProfile(@ModelAttribute Account account) {
        String activeUsername = accountService.getActiveAccount().getUsername();
        return "redirect:/profiles/" + activeUsername;
    }

    //save profile photo
    @PostMapping("/profiles/profile/image")
    public String postPhoto(@RequestParam("file") MultipartFile file) throws IOException {
        Account account = accountService.getActiveAccount();

        if (file.isEmpty()) {
            return "redirect:/profiles/" + account.getUsername();
        }

        if (account.getAccountPhoto() != null) {
            accountPhotoService.deletePhoto(account);
        }

        accountPhotoService.savePhoto(account, file);

        return "redirect:/profiles/" + account.getUsername();
    }

    //get profile photo
    @GetMapping(path = "/profiles/{id}/photo", produces = "image/png")
    @ResponseBody
    public byte[] get(@PathVariable Long id) {
        return accountPhotoService.getPhoto(id);
    }
}
