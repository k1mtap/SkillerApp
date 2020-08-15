
package projekti;

import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountRepository extends JpaRepository<Account, Long>{
    Account findByProfile(String profile);
    // TODO - onko findByName() turha?
    Account findByName(String name);
    Account findByUsername(String username);
}
