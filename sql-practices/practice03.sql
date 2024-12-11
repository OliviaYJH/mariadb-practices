-- 테이블 조인(JOIN) SQL 문제입니다.

-- 문제 1. 
-- 현재 급여가 많은 직원부터 직원의 사번, 이름, 그리고 연봉을 출력 하시오.
select a.emp_no, a.first_name, b.salary
from employees as a, salaries as b
where a.emp_no = b.emp_no and b.to_date = '9999-01-01'
order by b.salary desc;

-- 문제2.
-- 전체 사원의 사번, 이름, 현재 직책을 이름 순서로 출력하세요.
select a.emp_no, a.first_name, b.title
from employees a, titles b
where a.emp_no = b.emp_no and b.to_date = '9999-01-01'
order by a.first_name;

-- 문제3.
-- 전체 사원의 사번, 이름, 현재 부서를 이름 순서로 출력하세요.
select e.emp_no, e.first_name, d.dept_name
from employees e, dept_emp de, departments d
where de.to_date = '9999-01-01' and e.emp_no = de.emp_no and de.dept_no = d.dept_no
order by e.first_name;

-- 문제4.
-- 현재 근무중인 전체 사원의 사번, 이름, 연봉, 직책, 부서를 모두 이름 순서로 출력합니다.
select e.emp_no, e.first_name, s.salary, t.title, d.dept_name
from employees e, salaries s, titles t, dept_emp de, departments d
where e.emp_no = s.emp_no and e.emp_no = t.emp_no and e.emp_no = de.emp_no and t.emp_no = de.emp_no
	and s.to_date = '9999-01-01' and t.to_date = '9999-01-01' and de.to_date = '9999-01-01'
    and de.dept_no = d.dept_no
order by e.first_name;

-- 문제5.
-- 'Technique Leader'의 직책으로 과거에 근무한 적이 있는 모든 사원의 사번과 이름을 출력하세요.
-- (현재 'Technique Leader'의 직책으로 근무하는 사원은 고려하지 않습니다.)
select e.emp_no, e.first_name
from employees e, titles t
where e.hire_date < '9999-01-01' and e.emp_no = t.emp_no and t.title = 'Technique Leader';

-- 문제6.
-- 직원 이름(last_name) 중에서 S로 시작하는 직원들의 이름, 부서명, 직책을 조회하세요.
select e.first_name, e.last_name, d.dept_name, t.title
from employees e, dept_emp de, titles t, departments d
where e.emp_no = de.emp_no and e.emp_no = t.emp_no and de.emp_no = t.emp_no and de.dept_no = d.dept_no
	and substring(e.last_name, 1, 1) = 'S';

-- 문제7.
-- 현재, 직책이 Engineer인 사원 중에서 현재 급여가 40,000 이상인 사원들의 사번, 이름, 급여 그리고 타이틀을 급여가 큰 순서대로 출력하세요.
select e.emp_no, e.first_name, s.salary, t.title
from employees e, salaries s, titles t
where e.emp_no = s.emp_no and e.emp_no = t.emp_no and s.emp_no = t.emp_no and s.to_date = '9999-01-01' and t.to_date = '9999-01-01'
	and t.title = 'Engineer' and s.salary >= 40000
order by s.salary desc;

-- 문제8.
-- 현재, 평균급여가 50,000이 넘는 직책을 직책과 평균급여을 평균급여가 큰 순서대로 출력하세요.
select t.title, avg(s.salary) as avg_salary 
from titles t, salaries s
where t.emp_no = s.emp_no and t.to_date = '9999-01-01' and s.to_date = '9999-01-01'
group by t.title
having avg_salary >= 50000
order by avg_salary desc;

-- 문제9.
-- 현재, 부서별 평균급여를 평균급여가 큰 순서대로 부서명과 평균연봉을 출력 하세요.
select d.dept_name, avg(s.salary) as avg_salary
from dept_emp de, departments d, salaries s
where de.emp_no = s.emp_no and de.dept_no = d.dept_no and de.to_date = '9999-01-01'
group by d.dept_name
order by avg_salary desc;

-- 문제10.
-- 현재, 직책별 평균급여를 평균급여가 큰 직책 순서대로 직책명과 그 평균연봉을 출력 하세요.
select t.title, avg(s.salary) as avg_salary
from employees e, titles t, salaries s 
where e.emp_no = t.emp_no and e.emp_no = s.emp_no
and t.to_date='9999-01-01' and s.to_date='9999-01-01'
group by t.title 
order by avg_salary desc;
