
package projekti;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AccountRepository extends JpaRepository<Account, Long>{
    Account findByProfile(String profile);
    Account findByUsername(String username);
    List<Account> findAllByName(String name);
    
    @Query(value = "SELECT * FROM Account a WHERE LOWER(a.name) LIKE %:keyword%", nativeQuery = true)
    List<Account> findByKeyword(@Param("keyword") String keyword);
}
