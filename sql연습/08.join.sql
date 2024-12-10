--
-- join
--

-- 예제: 이름이 'parto'인 직원의 현재 직책을 구하세요.
select emp_no from employees where concat(first_name, ' ', last_name) = 'parto hitomi';

-- 11052
select title
	from titles
where to_date = '9999-01-01' and emp_no = 11052;

select a.emp_no, a.first_name, a.last_name, b.title
from employees a, titles b
where a.emp_no = b.emp_no -- join condition 
 and concat(a.first_name, ' ', a.last_name) = 'parto hitomi'; -- row condition