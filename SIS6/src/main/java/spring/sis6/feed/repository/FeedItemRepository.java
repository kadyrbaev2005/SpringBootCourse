package spring.sis6.feed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.sis6.feed.entity.FeedItem;
import java.util.List;
import java.util.UUID;

@Repository
public interface FeedItemRepository extends JpaRepository<FeedItem, UUID> {
    List<FeedItem> findByUserId(String userId);
}