package com.example.feedservice.repository;

import com.example.feedservice.entity.FeedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FeedItemRepository extends JpaRepository<FeedItem, Long> {
    List<FeedItem> findByUserIdOrderByCreatedAtDesc(String userId);
}