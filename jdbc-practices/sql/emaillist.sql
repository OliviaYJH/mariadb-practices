CREATE TABLE IF NOT EXISTS `webdb`.`emaillist` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

desc emaillist;

-- insert
insert
	into emaillist
values (null, '둘', '리', 'dooly@gmail.com');

-- list
select id, first_name, last_name, email 
from emaillist
order by id desc;

-- delete
delete 
from emaillist 
where id = 1;
