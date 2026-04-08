CREATE TABLE provinces (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    revolt_risk NUMERIC(6, 2) NOT NULL DEFAULT 0
);

CREATE TABLE peasants (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    balance NUMERIC(19, 2) NOT NULL,
    province_id BIGINT NOT NULL,
    CONSTRAINT fk_peasant_province FOREIGN KEY (province_id) REFERENCES provinces (id)
);

CREATE TABLE tax_records (
    id BIGSERIAL PRIMARY KEY,
    amount NUMERIC(19, 2) NOT NULL,
    collected_at TIMESTAMPTZ NOT NULL,
    king_mood_snapshot VARCHAR(32) NOT NULL,
    weather_snapshot VARCHAR(32) NOT NULL,
    peasant_id BIGINT NOT NULL,
    province_id BIGINT NOT NULL,
    CONSTRAINT fk_tax_peasant FOREIGN KEY (peasant_id) REFERENCES peasants (id),
    CONSTRAINT fk_tax_province FOREIGN KEY (province_id) REFERENCES provinces (id)
);

CREATE INDEX idx_peasants_province ON peasants (province_id);
CREATE INDEX idx_tax_records_peasant ON tax_records (peasant_id);
CREATE INDEX idx_tax_records_province ON tax_records (province_id);
