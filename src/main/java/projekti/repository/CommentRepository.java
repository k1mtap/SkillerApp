package projekti.repository;

import projekti.domain.Comment;
import java.math.BigInteger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    
    // WE'RE ONLY GETTIN 10 NEWEST COMMENT FOR A MESSAGE
    @Query(value = "SELECT * FROM Comment C WHERE C.message_id = :message_id ORDER BY C.date DESC", nativeQuery = true)
    Page<Comment> findByMessageId(BigInteger message_id, Pageable pageable);
    
    @Modifying
    @Query(value = "DELETE FROM Comment C WHERE C.message_id = :id", nativeQuery = true)
    void deleteMessageComments(Long id);
    
    @Modifying
    @Query(value = "DELETE FROM Comment_Like CL WHERE CL.comment_id IN (SELECT C.id FROM Comment C WHERE C.message_id = :id)", nativeQuery = true)
    void deleteMessageCommentsLikes(Long id);
}
