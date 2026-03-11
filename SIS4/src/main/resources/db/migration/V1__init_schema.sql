CREATE TABLE IF NOT EXISTS events (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    organizer_email VARCHAR(255) NOT NULL,
    event_date TIMESTAMP NOT NULL,
    ticket_price DECIMAL(10, 2),
    age_limit INTEGER,
    location VARCHAR(255),
    max_attendees INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_events_organizer_email ON events(organizer_email);
CREATE INDEX idx_events_event_date ON events(event_date);