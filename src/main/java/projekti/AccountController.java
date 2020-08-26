package projekti;

import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ContactService contactService;

    @GetMapping("/login")
    public String showCustomLoginForm() {
        return "login";
    }

    @GetMapping("/signup")
    public String showSignUpForm(@ModelAttribute Account account) {
        return "signup";
    }

    @PostMapping("/signup")
    public String addNewAccount(@Valid @ModelAttribute Account account, BindingResult bindingResult, HttpServletRequest req) {

        if (bindingResult.hasErrors()) {
            return "signup";
        }

        if (accountRepository.findByUsername(account.getUsername()) != null) {
            bindingResult.rejectValue("username", "username.exist", "Username already in use");
            return "signup";
        }

        if (accountRepository.findByProfile(account.getProfile()) != null) {
            bindingResult.rejectValue("profile", "profile.exist", "Profile already in use");
            return "signup";
        }
        
        String name = accountService.toTitleCase(account.getName());
        account.setName(name);
        String password = account.getPassword();
        account.setPassword(passwordEncoder.encode(password));
        accountRepository.save(account);

        // Kirjataan juuri rekisteröitynyt käyttäjä automaattisesti sisään (huom! salasana syötetään salaamattomana)
        try {
            req.login(account.getUsername(), password);
        } catch (ServletException e) {
            System.out.println("Error: " + e);
            return "redirect:/login";
        }

        return "redirect:/profiles/" + account.getProfile();
    }

    
    @GetMapping("/profiles")
    public String authentication() {
        return "redirect:/profiles/" + accountService.getCurrentAccount().getProfile();
    }

    @GetMapping("/profiles/{profile}")
    public String getProfile(Model model, @PathVariable String profile) {
        
        Account a = accountService.getUser(profile);
        Account currentAccount = accountService.getCurrentAccount();
        
        model.addAttribute("account", a);
        model.addAttribute("currentAccount", currentAccount);
        model.addAttribute("skills", a.getSortedSkills());
        model.addAttribute("contactDoneOrPending", contactService.contactAlreadyDoneOrPending(currentAccount, a));
        
        return "profile";
    }
    
//    TODO - KESKEN AINAKIN POLKU
    @GetMapping("/profiles/search")
    public String search(Model model, @RequestParam String keyword) {
        
        keyword = keyword.toLowerCase();
        
        if (keyword != null) {
            model.addAttribute("accounts", accountService.findByKeyword(keyword));
        } else {
            model.addAttribute("accounts", accountService.getAllAccounts());
        }
        
        model.addAttribute("currentAccount", accountService.getCurrentAccount());
        
        return "search";
    }
    
    
}
