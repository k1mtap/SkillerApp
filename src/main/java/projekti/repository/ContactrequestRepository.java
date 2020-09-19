package projekti.repository;

import projekti.domain.Contactrequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ContactrequestRepository extends JpaRepository<Contactrequest, Long>{
    
//    TODO - tarkista toimiiko oikeasti, koita lisätä sama käyttäjä uudestaan
    @Query(value = "SELECT * FROM Contactrequest c WHERE (c.ASKING_ACCOUNT_ID = :account1_id AND c.TARGET_ACCOUNT_ID = :account2_id) OR (c.ASKING_ACCOUNT_ID = :account2_id AND c.TARGET_ACCOUNT_ID = :account1_id)", nativeQuery = true)
    Contactrequest findByAccounts(Long account1_id, Long account2_id);
}
