package projekti.repository;

import projekti.domain.Account;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @EntityGraph(attributePaths = {"image"})
    Account findByProfile(String profile);

    @EntityGraph(attributePaths = {"image"})
    Account getContactById(Long id);
    
    @EntityGraph(attributePaths = {"image"})
    @Override
    List<Account> findAll();
    
    Account findByUsername(String username);

    @Query(value = "SELECT * FROM Account A WHERE LOWER(A.name) LIKE %:keyword%", nativeQuery = true)
    List<Account> findByKeyword(@Param("keyword") String keyword);
}
