-- 서브쿼리(SUBQUERY) SQL 문제입니다.

-- 단 조회결과는 급여의 내림차순으로 정렬되어 나타나야 합니다. 

-- 문제1.
-- 현재 전체 사원의 평균 급여보다 많은 급여를 받는 사원은 몇 명이나 있습니까?
select count(*)
from employees e
	join salaries s
    on e.emp_no = s.emp_no
where s.to_date = '9999-01-01' and s.salary > (select avg(salary) from salaries where to_date = '9999-01-01');

-- 문제2. 
-- 현재, 각 부서별로 최고의 급여를 받는 사원의 사번, 이름, 부서 급여을 조회하세요. 단 조회결과는 급여의 내림차순으로 정렬합니다.

select e.emp_no, e.first_name, s.salary
from employees e, dept_emp de, salaries s, departments d
where e.emp_no = de.emp_no 
	and d.dept_no = de.dept_no
    and e.emp_no = s.emp_no
    and (de.dept_no, s.salary) in (
		select de.dept_no, max(s.salary)
		from dept_emp de, salaries s
		where de.emp_no = s.emp_no and de.to_date = '9999-01-01'
		group by de.dept_no
    )
order by s.salary desc;

-- 부서별 최고 급여 찾기
select de.dept_no, max(s.salary)
from dept_emp de, salaries s
where de.emp_no = s.emp_no and de.to_date = '9999-01-01'
group by de.dept_no;

-- 문제3.
-- 현재, 사원 자신들의 부서의 평균급여보다 급여가 많은 사원들의 사번, 이름 그리고 급여를 조회하세요 
select e.emp_no, e.first_name, s.salary
from employees e, salaries s, dept_emp de, (
							select avg(s.salary) as avg_salary, de.dept_no
                            from employees e, salaries s, dept_emp de
                            where e.emp_no = s.emp_no
								and e.emp_no = de.emp_no
								and s.to_date = '9999-01-01'
                                and de.to_date = '9999-01-01'
							group by de.dept_no) d
where e.emp_no = s.emp_no
	and e.emp_no = de.emp_no
    and de.dept_no = d.dept_no
    and s.to_date = '9999-01-01'
    and de.to_date = '9999-01-01'
    and s.salary > d.avg_salary;
	
-- 문제4.
-- 현재, 사원들의 사번, 이름, 그리고 매니저 이름과 부서 이름을 출력해 보세요.
select e.emp_no, e.first_name, ee.manager, d.dept_name
from employees e, dept_emp de, departments d, (
			select dm.dept_no, e.first_name as manager
            from employees e, dept_manager dm
            where e.emp_no = dm.emp_no
				and dm.to_date = '9999-01-01') ee
where e.emp_no = de.emp_no and de.to_date = '9999-01-01' 
    and d.dept_no = de.dept_no
    and ee.dept_no = de.dept_no;

-- 문제5.
-- 현재, 평균급여가 가장 높은 부서의 사원들의 사번, 이름, 직책 그리고 급여를 조회하고 급여 순으로 출력하세요.
select e.emp_no, e.first_name, t.title, s.salary 
from dept_emp de, employees e, titles t, salaries s, 
	(select d.dept_name, d.dept_no as dept_no, avg(s.salary)
	from salaries s, dept_emp de, departments d
	where s.emp_no = de.emp_no and de.dept_no = d.dept_no
	group by d.dept_name
	order by avg(s.salary) desc
	limit 1) as m
where de.dept_no = m.dept_no and de.emp_no = e.emp_no and t.emp_no = e.emp_no and t.to_date = '9999-01-01' and de.to_date = '9999-01-01'
	and e.emp_no = s.emp_no and s.to_date = '9999-01-01' and s.emp_no = de.emp_no and s.emp_no = t.emp_no
order by s.salary desc;


-- 평균 급여가 가장 높은 부서
select d.dept_name, d.dept_no, avg(s.salary)
from salaries s, dept_emp de, departments d
where s.emp_no = de.emp_no and de.dept_no = d.dept_no
group by d.dept_name
order by avg(s.salary) desc
limit 1;


-- 문제6.
-- 현재, 평균 급여가 가장 높은 부서의 이름 그리고 평균급여를 출력하세요.
select d.dept_name, avg(s.salary)
from salaries s, dept_emp de, departments d
where s.emp_no = de.emp_no 
	and de.dept_no = d.dept_no
	and s.to_date = '9999-01-01'
    and de.to_date = '9999-01-01'
group by d.dept_name
order by avg(s.salary) desc
limit 1;

-- 문제7.
-- 현재, 평균 급여가 가장 높은 직책의 타이틀 그리고 평균급여를 출력하세요.
select t.title, avg(s.salary)
from salaries s, titles t
where s.emp_no = t.emp_no
	and s.to_date = '9999-01-01'
    and t.to_date = '9999-01-01'
group by t.title
order by avg(s.salary) desc
limit 1;

