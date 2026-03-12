CREATE TABLE feed_items (
    id BIGSERIAL PRIMARY KEY,
    post_id UUID NOT NULL,
    user_id VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    hashtags TEXT,
    created_at TIMESTAMP NOT NULL,
    received_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_feed_items_user_id ON feed_items(user_id);
CREATE INDEX idx_feed_items_created_at ON feed_items(created_at DESC);