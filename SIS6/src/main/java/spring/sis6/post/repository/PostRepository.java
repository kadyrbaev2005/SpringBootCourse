package spring.sis6.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.sis6.post.entity.Post;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
}