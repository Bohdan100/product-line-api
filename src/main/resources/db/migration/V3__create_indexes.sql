CREATE INDEX IF NOT EXISTS idx_record_line_date ON record (line_id, date);

CREATE INDEX IF NOT EXISTS idx_record_date ON record (date);

CREATE INDEX IF NOT EXISTS idx_record_author ON record (author_id);

CREATE INDEX IF NOT EXISTS idx_record_org ON record (name_of_organization);
CREATE INDEX IF NOT EXISTS idx_record_product ON record (name_of_product);

CREATE EXTENSION IF NOT EXISTS pg_trgm;
CREATE INDEX idx_record_org_trgm ON record USING gin (name_of_organization gin_trgm_ops);