ALTER TABLE events 
ADD COLUMN IF NOT EXISTS description TEXT;

COMMENT ON COLUMN events.description IS 'Detailed description of the event';