package projekti;

import java.util.List;
import java.util.Objects;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import projekti.domain.Account;
import projekti.repository.AccountRepository;
import projekti.service.AccountService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProjektiTest extends org.fluentlenium.adapter.junit.FluentTest {

    @LocalServerPort
    private Integer port;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;

    @Before
    public void init() {
        if (accountService.getUser("pekka") == null) {
            Account pekka = new Account();
            pekka.setUsername("pekka");
            pekka.setPassword(passwordEncoder.encode("pekka"));
            pekka.setName("Pekka Poika");
            pekka.setProfile("pekka");
            accountRepository.save(pekka);
        }
    }

    @Test
    public void signUpAndReturnOk() throws Exception {
        MvcResult res = mockMvc.perform(post("http://localhost:" + port + "/signup")
                .param("username", "kayttis")
                .param("password", "salasana")
                .param("name", "Timo Werner")
                .param("profile", "Werner"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profiles")).andReturn();
    }

    @Test
    public void canSignupAndAutomaticallyLoginToProfilePage() {
        int accounts = accountRepository.findAll().size();
        goTo("http://localhost:" + port + "/signup");
        $("#username").fill().with("testi");
        $("#password").fill().with("salasana");
        $("#name").fill().with("Testailija");
        $("#profile").fill().with("testi");
        $("form").first().submit();
        assertTrue(accountRepository.findAll().size() == accounts + 1);
        assertTrue(pageSource().contains("Testailija"));
    }

    @Test
    public void noAuthOnWrongPassword() {
        goTo("http://localhost:" + port + "/login");
        enterDetailsAndSubmit("pekka", "väärä");
        assertThat(pageSource()).doesNotContain("Pekka Poika");
    }

    @Test
    public void authSuccessfulOnCorrectPassword() {
        goTo("http://localhost:" + port + "/login");
        enterDetailsAndSubmit("pekka", "pekka");
        assertThat(pageSource()).contains("Pekka Poika");
    }

    @Test
    public void searchingUserReturnsAccounts() {
        List<Account> accounts = accountService.findByKeyword("kka");
        assertTrue(Objects.equals(accountService.getUser("pekka").getId(), accounts.get(0).getId()));
    }

    private void enterDetailsAndSubmit(String username, String password) {
        $(By.name("username")).fill().with(username);
        $(By.name("password")).fill().with(password);
        $("form").first().submit();
    }

}
