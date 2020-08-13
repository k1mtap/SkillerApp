package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String showLandingPage() {
        return "index";
    }

    @GetMapping("/profiles/{profile}")
    public String showLoginForm(Model model, @PathVariable String profile) {
        model.addAttribute("profile", accountRepository.findByProfile(profile).getProfile());
        return "profile";
    }

    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("accounts", accountRepository.findAll());
        return "signup";
    }

    // TODO - @Valid ja @ModelAttribute Account account, sekä bindingresults. Miten salasanan salaaminen?
    @PostMapping("/signup")
    public String addNewAccount(@RequestParam String username, @RequestParam String password, @RequestParam String name, @RequestParam String profile) {
        if (accountRepository.findByProfile(username) != null) {
            // TODO - käyttäjänimi on jo olemassa
            return "redirect:/signup";
        }

        Account a = new Account(username, passwordEncoder.encode(password), name, profile);
        accountRepository.save(a);
        // TODO - redirect kuntoon: accounts/{profile}
        return "redirect:/profiles/" + a.getProfile();
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
}
