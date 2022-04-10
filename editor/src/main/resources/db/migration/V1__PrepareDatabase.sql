CREATE TABLE IF NOT EXISTS topics(
    topic VARCHAR(255),
    name VARCHAR(100),
    example JSON,
    PRIMARY KEY (topic, name)
);
