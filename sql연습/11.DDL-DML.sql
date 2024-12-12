--
-- DDL/DML 연습
-- 

-- 테이블 삭제
drop table member; 

-- 테이블 생성
create table member(
	id int not null auto_increment,
    email varchar(200) not null,
    password varchar(64) not null,
    name varchar(50) not null, 
    department varchar(100),
    
    -- constraint
    primary key(id) 
);

-- 테이블 확인 
desc member;

-- 칼럼 추가
alter table member add column juminbunho char(13) not null after email;

-- 칼럼 삭제
alter table member drop juminbunho;

-- 칼럼 속성이나 이름 변경 
alter table member change column department dept varchar(100) not null;

-- 칼럼 추가 
alter table member add column profile text;

-- 칼럼 삭제 
alter table member drop juminbunho;

-- insert
insert
	into member
values (null, 'olivia@gmail.com', password('1234'), '유정현', '개발팀', null);

select * from member;

-- update
update member
	set email = 'olivia3@gmail.com', password=password('12345')
where id = 2; -- where문 작성 안하면 모두 변경됨 

insert
	into member(id, email, name, password, dept)
values (null, 'olivia2@gmail.com', '유정현2', password('1234'), '인사팀');

-- delete
delete 
	from member
where id = 3;

-- transaction(tx) 
select id, email from member;

select @@autocommit; -- 1

insert into member 
values(null, 'olivia2@gmail.com', password('123'), '유정현2', '개발팀2', null);

-- tx:begin
set autocommit = 0;
select @@autocommit; -- 0

insert into member 
values(null, 'olivia3@gmail.com', password('123'), '유정현3', '개발팀3', null); -- 실제 DB에는 x

-- tx:end
commit; -- 실제 DB에 반영됨 
-- rollback; -- 전체 모두 
