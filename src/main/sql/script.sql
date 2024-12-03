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

DELIMITER $$
CREATE PROCEDURE insert_user(
	IN user_name varchar(50),
    IN user_email varchar(50),
    IN user_country varchar(50)
)
BEGIN
	INSERT INTO users(name, email, country) VALUES(user_name, user_email, user_country);
 END$$
DELIMITER ;



create table Permission(
      id int(11) primary key,
      name varchar(50)
);

create table User_Permission(
	user_id int(11),
	permission_id int(11)
);

insert into Permission(id, name) values(1, 'add');
insert into Permission(id, name) values(2, 'edit');
insert into Permission(id, name) values(3, 'delete');
insert into Permission(id, name) values(4, 'view');

select * from user_permission;



create table Employee ( 
            id int(3) PRIMARY KEY AUTO_INCREMENT,
            name varchar(120) NOT NULL,
            salary int(220) NOT NULL,
            created_Date datetime
);

select * from employee;























