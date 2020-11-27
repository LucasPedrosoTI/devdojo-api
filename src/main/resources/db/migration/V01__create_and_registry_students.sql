CREATE TABLE student (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

INSERT INTO student (name) VALUES 
('Lucas Pedroso'),
('Henrique Vergara'),
('Clécio Silva');
