DROP TABLE IF EXISTS message;

CREATE TABLE message(
id int unsigned auto_increment NOT NULL,
msg VARCHAR(20) NOT NULL,
primary KEY (id));

INSERT INTO message(msg) VALUES("HELLO");
INSERT INTO message(msg) VALUES("BYE");
