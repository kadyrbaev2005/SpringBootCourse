CREATE TABLE posts (
    id UUID PRIMARY KEY,
    user_id VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    hashtags TEXT,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_posts_user_id ON posts(user_id);
CREATE INDEX idx_posts_created_at ON posts(created_at DESC);