package projekti;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepository extends JpaRepository<Message, Long>{
    
    @Query(value = "SELECT * FROM Message m WHERE m.account_id = :account_id OR m.account_id IN :accounts_id", nativeQuery = true)
    Page<Message> findAllContactMessages(Long account_id, List<Long> accounts_id, Pageable pageable);
//  Page<Message> sittenkin? https://docs.spring.io/spring-data/rest/docs/2.0.0.M1/reference/html/paging-chapter.html
}
