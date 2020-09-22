package projekti.repository;

import projekti.domain.Message;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepository extends JpaRepository<Message, Long>{
    
    // GET ALL MESSAGES ON PAGE WHERE THE SENDER ACCOUNT_ID IS EITHER THE CURRENT USER OR SOME OF THE USER'S CONTACTS, ORDER BY DATE
    @Query(value = 
            "SELECT * FROM Message M WHERE M.account_id = :account_id OR M.account_id IN "
                    + "(SELECT C.account2_id FROM Contacts C WHERE C.account1_id = :account_id) "
                    + "ORDER BY M.date DESC", nativeQuery = true)
    Page<Message> findAllContactMessages(Long account_id, Pageable pageable);
    
    // GET ALL THE IDS FROM THE MESSAGES SHOWING ON THE PAGE
//    @Query(value = 
//            "SELECT M.id FROM Message M WHERE M.account_id = :account_id OR M.account_id IN "
//                    + "(SELECT C.account2_id FROM Contacts C WHERE C.account1_id = :account_id)", nativeQuery = true)
//    List<BigInteger> findAllContactMessagesIds(Long account_id);
    
//    @Query(value = 
//            "SELECT COUNT(liked_by_account_id ) FROM Message_Like WHERE message_id = :message_id", nativeQuery = true)
//    Integer getLikesForMessage(BigInteger message_id);
    
    @Query(value = 
            "SELECT COUNT(liked_by_account_id ) FROM Message_Like WHERE message_id = :message_id", nativeQuery = true)
    Integer getLikesForMessage(Long message_id);
    
}