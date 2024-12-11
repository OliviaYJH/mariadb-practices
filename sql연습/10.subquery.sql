-- 
-- Subquery
-- 

-- 
-- 1) select 절
-- 

select (select 1 + 1 from dual) from dual;
-- insert into t1 values(null, (select max(no) from t1))

-- 
-- 2) from 절의 서브쿼리
-- 
select now() as n, sysdate() as s, 3 + 1 as r from dual;
select a.n, a.r
from (select now() as n, sysdate() as s, 3 + 1 as r from dual) a;

-- 
-- 3) where 절의 서브쿼리 
-- 

-- 예) 현재, Fai Bale이 근무하는 부서에서 근부하는 직원의 사번, 전체 이름을 출력해보세요. 
select b.dept_no
	from employees a, dept_emp b
where a.emp_no = b.emp_no
	and b.to_date = '9999-01-01'
    and concat(a.first_name, ' ', a.last_name) = 'Fai Bale';
    
-- d004
select a.emp_no, a.first_name
from employees a, dept_emp b
where a.emp_no = b.emp_no
	and b.to_date = '9999-01-01'
    and b.dept_no = (
		select b.dept_no
    	from employees a, dept_emp b
		where a.emp_no = b.emp_no
			and b.to_date = '9999-01-01'
			and concat(a.first_name, ' ', a.last_name) = 'Fai Bale'
    );
    
    
-- 3-1) 단일행 연산자: =. >, <, >=, <=, <>, != 
-- 실습문제 1
-- 현재, 전체 사원의 평균 연봉보다 적은 급여를 받는 사원의 이름과 급여를 출력하세요. 
select a.first_name, b.salary
from employees a, salaries b
where a.emp_no = b.emp_no
	and b.to_date = '9999-01-01'
    and b.salary < (
		select avg(salary)
		from salaries
		where to_date = '9999-01-01')
order by b.salary desc;

-- 실습문제 2
-- 현재, 직책별 평균 급여 중에 가장 적은 평균 급여의 직책 이름과 급여를 출력하세요.
-- 1) 직책별 평균 급여
select b.title, avg(a.salary)
from salaries a, titles b
where a.emp_no = b.emp_no
	and a.to_date = '9999-01-01'
    and b.to_date = '9999-01-01'
group by b.title;

-- 2) 직책별 가장 적은 평균 급여
select a.title, min(a.avg_salary)
from (
	select b.title, avg(a.salary) as avg_salary
	from salaries a, titles b
	where a.emp_no = b.emp_no
		and a.to_date = '9999-01-01'
		and b.to_date = '9999-01-01'
	group by b.title
) a;

-- 3) sol1: where절 subquery(=)
select b.title, avg(a.salary)
from salaries a, titles b
where a.emp_no = b.emp_no
	and a.to_date = '9999-01-01'
	and b.to_date = '9999-01-01'
group by b.title
having avg(a.salary) = ( 
						select min(avg_salary)
						from ( 
							select b.title, avg(a.salary) as avg_salary
							from salaries a, titles b
							where a.emp_no = b.emp_no
								and a.to_date = '9999-01-01'
								and b.to_date = '9999-01-01'
							group by b.title
                            ) a
                        );

-- 4) sol2: top-k
select b.title, avg(a.salary)
from salaries a, titles b
where a.emp_no = b.emp_no
	and a.to_date = '9999-01-01'
	and b.to_date = '9999-01-01'
group by b.title
order by avg(a.salary) asc
limit 0, 1;

-- 3-2) 복수행 연산자: in, not in, 비교 연산자 any, 비교 연산자 ALL
-- 1. =any: in
-- 2. >any, >= any: 최소값
-- 3. <any, <= any: 최댓값
-- 4. <>any, !=any: not in

-- all 사용법
-- 1. =all: (x)
-- 2. >all, >= all: 최댓값
-- 3. <all, <=all: 최소값 
-- 4. <>all, !=all: 

-- 실습문제 3
-- 현재 급여가 50000 이상인 직원의 이름과 급여를 출력하세요. 
-- sol1
select e.first_name, s.salary
from employees as e, salaries as s
where e.emp_no = s.emp_no and s.to_date = '9999-01-01' and s.salary > 50000 order by s.salary;

-- sol2 
select e.first_name, s.salary
from employees as e, salaries as s
where e.emp_no = s.emp_no and s.to_date = '9999-01-01'
	and (e.emp_no, s.salary) in (
		select emp_no, salary 
        from salaries
        where to_date = '9999-01-01' and salary > 50000
    )
order by s.salary;

select e.first_name, s.salary
from employees as e, salaries as s
where e.emp_no = s.emp_no and s.to_date = '9999-01-01'
	and (e.emp_no, s.salary) = any (
		select emp_no, salary 
        from salaries
        where to_date = '9999-01-01' and salary > 50000
    )
order by s.salary;


-- 실습문제 4:
-- 현재, 각 부서별 최고급여를 받고 있는 직원의 이름, 부서 이름, 급여를 출력해 보세요.

select a.dept_no, max(b.salary) as max_salary
from dept_emp a, salaries b
where a.emp_no = b.emp_no
	and a.to_date = '9999-01-01'
    and b.to_date = '9999-01-01'
group by a.dept_no;

-- sol01: where in
select c.dept_name, a.first_name, d.salary
from employees a, dept_emp b, departments c, salaries d
where a.emp_no = b.emp_no
	and b.dept_no = c.dept_no
    and a.emp_no = d.emp_no
    and b.to_date = '9999-01-01'
    and d.to_date = '9999-01-01'
    and (b.dept_no, d.salary) in (
		select a.dept_no, max(b.salary)
		from dept_emp a, salaries b
		where a.emp_no = b.emp_no
			and a.to_date = '9999-01-01'
			and b.to_date = '9999-01-01'
		group by a.dept_no
);

-- sol2: from절 subquery & join 사용 
select c.dept_name, a.first_name, d.salary
from employees a, 
	dept_emp b, 
    departments c, 
    salaries d, (
	select a.dept_no, max(b.salary) as max_salary
	from dept_emp a, salaries b
	where a.emp_no = b.emp_no
		and a.to_date = '9999-01-01'
		and b.to_date = '9999-01-01'
	group by a.dept_no
	) as e
where a.emp_no = b.emp_no
	and b.dept_no = c.dept_no
	and a.emp_no = d.emp_no
	and b.dept_no = e.dept_no
	and b.to_date = '9999-01-01'
	and d.to_date = '9999-01-01'
	and d.salary = e.max_salary;






