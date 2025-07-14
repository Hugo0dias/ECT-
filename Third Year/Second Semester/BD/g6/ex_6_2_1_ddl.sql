Create Schema Company

go 

Create Table Company.Employee(
Fname varchar(15),
Minit char,
Lname varchar(15),
Ssn int,
Bdate varchar(10),
Adress varchar(30),
Sex char,
Salary float,
primary key (Ssn),
Super_Ssn int,
Dno int,
Foreign Key (Super_Ssn) references Company.Employee(Ssn),
foreign key (Dno) references Company.Department(Dnumber)
)


Create Table Company.Department(
Dname varchar(30),
Dnumber int,
Mgr_start_date varchar(30),
Mgr_Ssn int,
primary key (Dnumber),
--Foreign key (Mgr_Ssn) references Company.Employee(Ssn)
)

Create Table Company.Dept_Locations(
Dlocation varchar(30),
Dnumber int,
foreign key (Dnumber) references Company.Department(Dnumber),
primary key (Dnumber, Dlocation)
)

Create Table Company.Project(
Pname varchar (30),
Pnumber int,
Plocation varchar(30),
Dnum int,
Primary key (Pnumber),
foreign key (Dnum) references Company.Department(Dnumber)
)

Create Table Company.Works_On(
Essn int,
Pno int,
[Hours] float
foreign key (Essn) references Company.Employee(Ssn),
foreign key (Pno) references Company.Project(Pnumber),
Primary key (Essn, Pno)
)

Create Table Company.Dependents(
Essn int,
Dependent_name varchar(30),
Sex char,
Bdate varchar(10),
Relationship varchar(30)
foreign key (Essn) references Company.Employee(Ssn)
primary key (Essn, Dependent_name)
)

ALTER TABLE Company.Department
ADD CONSTRAINT FK_Department_Employee FOREIGN KEY (Mgr_Ssn) REFERENCES Company.Employee(Ssn);
GO