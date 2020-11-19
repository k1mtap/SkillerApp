package projekti.service;

import projekti.domain.Account;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import projekti.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account getUser(String profile) {

        return accountRepository.findByProfile(profile);
    }

    public Account getUser(Long id) {

        return accountRepository.getContactById(id);
    }

    public Account getByUsername(String username) {

        return accountRepository.findByUsername(username);
    }

    public Account save(Account account) {

        return accountRepository.save(account);
    }

    public Account getCurrentAccount() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account currentUser = getByUsername(auth.getName());
        return currentUser;
    }
    
    public Boolean authorized(String profile) {
        return getCurrentAccount().getProfile().equals(profile);
    }

    public String getProfile(String profile) {

        return accountRepository.findByProfile(profile).getProfile();
    }

    public List<Account> getAllAccounts() {

        return accountRepository.findAll();
    }

    public List<Account> findByKeyword(String keyword) {

        return accountRepository.findByKeyword(keyword);
    }

    public void saveAll(Account account1, Account account2) {

        accountRepository.save(account1);
        accountRepository.save(account2);
    }
    
    public byte[] getImage(String profile) {
        
        return getUser(profile).getImage().getContent();
    }
    
    public void modifyGivenNameAndProfile(Account account) {
        
        String name = toTitleCase(account.getName());
        account.setName(name);
        
        String profile = replaceProfile(account.getProfile());
        account.setProfile(profile);
    }

    public String toTitleCase(String name) {

        String[] array = name.trim().split(" ");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < array.length; i++) {
            sb.append(Character.toUpperCase(array[i].charAt(0)))
                    .append(array[i].substring(1)).append(" ");
        }

        return sb.toString().trim();
    }
    
    public String replaceProfile(String profile) {
        
        profile = profile.replace("ä", "a");
        profile = profile.replace("ö", "o");
        
        return profile;
    }
}
