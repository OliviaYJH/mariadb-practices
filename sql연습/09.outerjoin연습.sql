--
-- outer join
--

-- insert into department values(null, '개발');
-- insert into department values(null, '총무');
-- insert into department values(null, '영업');
-- insert into department values(null, '기획');

select * from department;

-- insert into employee values(null, '둘리', 1);
-- insert into employee values(null, '마이콜', 2);
-- insert into employee values(null, '또치', 3);
-- insert into employee values(null, '길동', null);

select * from employee;

-- inner join
select *
from employee a 
    join department b 
    on a.department_id = b.id;
    
select a.name as '이름', b.name as '부서'
from employee a join department b on a.department_id = b.id;

-- left (outer) join
select a.name as '이름', ifnull(b.name, '없음') as '부서'
from employee a left join department b on a.department_id = b.id;

-- right (outer) join
select a.name as '이름', b.name as '부서'
from employee a right join department b on a.department_id = b.id;

-- full (outer) join
-- mariadb는 지원 안함

