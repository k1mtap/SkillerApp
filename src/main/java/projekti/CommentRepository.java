package projekti;

import java.math.BigInteger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    
//    @Query(value = "SELECT * FROM Comment C WHERE C.message_id = :message_id ORDER BY C.date DESC", nativeQuery = true)
//    Page<Comment> findByMessageId(BigInteger message_id, Pageable pageable);
    
    @Modifying
    @Query(value = "DELETE FROM Comment C WHERE C.message_id = :id", nativeQuery = true)
    void deleteMessageComments(Long id);
}
