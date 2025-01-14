CREATE TABLE IF NOT EXISTS line (
   id BIGSERIAL PRIMARY KEY,
   line_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS "user" (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_role (
    user_id BIGSERIAL NOT NULL,
    role VARCHAR(255) NOT NULL,
    PRIMARY KEY (user_id, role),
    CONSTRAINT fk_user_role_user FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS record (
    id BIGSERIAL PRIMARY KEY,
    date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    name_of_organization VARCHAR(255) NOT NULL,
    name_of_product VARCHAR(255) NOT NULL,
    variant VARCHAR(255) NOT NULL,
    side VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    line_id BIGSERIAL NOT NULL,
    author_id BIGSERIAL NOT NULL,
    CONSTRAINT fk_record_line FOREIGN KEY (line_id) REFERENCES line(id) ON DELETE CASCADE,
    CONSTRAINT fk_record_user FOREIGN KEY (author_id) REFERENCES "user"(id) ON DELETE CASCADE
);