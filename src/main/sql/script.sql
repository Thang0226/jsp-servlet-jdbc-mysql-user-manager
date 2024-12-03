CREATE DATABASE demo;

USE demo;

CREATE TABLE users ( 
      id INT(3) AUTO_INCREMENT PRIMARY KEY, 
      name VARCHAR(120) NOT NULL, 
      email VARCHAR(220) NOT NULL, 
      country VARCHAR(120)
);

INSERT INTO users(name, email, country) VALUES('Minh','minh@codegym.vn','Viet Nam');
INSERT INTO users(name, email, country) VALUES('Kante','kante@che.uk','Kenya');
INSERT INTO users(name, email, country) VALUES('Thang','thang@che.uk','Russia');
INSERT INTO users(name, email, country) VALUES('Dung','dung@che.uk','Japan');
INSERT INTO users(name, email, country) VALUES('Tri','tri@che.uk','Indonesia');

SELECT * FROM users
WHERE country = 'Vietnam';

SELECT * FROM users
ORDER BY name;

DELIMITER $$
CREATE PROCEDURE get_user_by_id(IN user_id INT)
BEGIN
 SELECT users.name, users.email, users.country
 FROM users
 where users.id = user_id;
 END$$
DELIMITER ;
