package projekti;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ContactRepository extends JpaRepository<Contact, Long>{
    
//    TODO - tarkista toimiiko oikeasti, koita lisätä sama käyttäjä uudestaan
    @Query(value = "SELECT * FROM Contact c WHERE (c.ASKING_ACCOUNT_ID = :account1_id AND c.TARGET_ACCOUNT_ID = :account2_id) OR (c.ASKING_ACCOUNT_ID = :account2_id AND c.TARGET_ACCOUNT_ID = :account1_id)", nativeQuery = true)
    Contact findByAccounts(Long account1_id, Long account2_id);
}
