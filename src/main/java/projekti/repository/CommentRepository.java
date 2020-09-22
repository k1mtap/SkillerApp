package projekti.repository;

import projekti.domain.Comment;
import java.math.BigInteger;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    
    // GETTING ALL THE COMMENTS ORDER BY DATE, LIMITING THE COMMENTS TO 10 PER PAGE AT MESSAGESERVICE
//    @Query(value = "SELECT * FROM Comment C WHERE C.message_id = :message_id ORDER BY C.date DESC LIMIT 10", nativeQuery = true)
//    List<Comment> findByMessageId(BigInteger message_id);
    
    @Query(value = "SELECT * FROM Comment C WHERE C.message_id = :message_id ORDER BY C.date DESC LIMIT 10", nativeQuery = true)
    List<Comment> findByMessageId(Long message_id);
    
    @Modifying
    @Query(value = "DELETE FROM Comment C WHERE C.message_id = :id", nativeQuery = true)
    void deleteMessageComments(Long id);
    
    @Modifying
    @Query(value = "DELETE FROM Comment_Like CL WHERE CL.comment_id IN (SELECT C.id FROM Comment C WHERE C.message_id = :id)", nativeQuery = true)
    void deleteMessageCommentsLikes(Long id);
}
