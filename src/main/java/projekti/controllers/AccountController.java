package projekti.controllers;

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
import projekti.models.Account;
import projekti.models.Skill;

import projekti.services.AccountService;
import projekti.services.SkillService;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private SkillService skillService;


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
        return "wall";
    }

    //post new account
    @PostMapping("/register")
    public String postAccount(@Valid @ModelAttribute Account account, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        accountService.create(account);
        return "redirect:/register";
    }

    //return registerform
    @GetMapping("/register")
    public String getRegisterForm(@ModelAttribute Account account) {
        return "register";
    }

}
