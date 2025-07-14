# BD: Guião 8


## ​8.1
 
### *a)*

use Company

GO
CREATE PROCEDURE Ex1 @Ssn INT
AS
BEGIN
 DECLARE @ssn_id INT;
 SELECT @ssn_id = Ssn FROM Employee WHERE Ssn = @Ssn;

 DELETE FROM Dependent WHERE Essn = @ssn_id;
 DELETE FROM Works_on WHERE Essn = @ssn_id; 

 UPDATE Employee SET Super_ssn = NULL WHERE Employee.Super_ssn = @ssn_id;
 UPDATE Department SET Mgr_ssn = NULL WHERE Department.Mgr_ssn = @ssn_id;

 DELETE FROM Employee WHERE Ssn = @ssn_id;
END;
--


SELECT * FROM Employee

EXEC Ex1 12652121;

SELECT * FROM Employee


### *b)*

```
GO
ALTER PROC Exb @oldSsn INT OUTPUT
AS
BEGIN

 SELECT Employee.Fname, Employee.Minit, Employee.Lname, Employee.Ssn, Employee.Bdate, DATEDIFF(YEAR, Department.Mgr_start_date, GETDATE()) AS oldYears
 FROM Employee JOIN Department ON Department.Mgr_ssn = Employee.Ssn;

 SELECT TOP(1) @oldSsn = Employee.ssn
    FROM Employee JOIN Department ON Employee.Ssn = Department.Mgr_ssn
    ORDER BY Department.Mgr_start_date;

    SELECT Employee.Fname, Employee.Minit, Employee.Lname, Employee.Ssn, Employee.Bdate, DATEDIFF(YEAR, Department.Mgr_start_date, GETDATE()) AS oldYears
    FROM Employee JOIN Department ON Employee.Ssn = Department.Mgr_ssn
    WHERE Employee.Ssn = @oldSsn;
END;

---------

GO
USE COMPANY
GO

DECLARE @oldSsn INT
EXEC Exb @oldSsn OUTPUT
PRINT @oldSsn
```

### *c)*

```
GO
CREATE TRIGGER Exc ON Department
AFTER INSERT, UPDATE
AS
BEGIN
 SET NOCOUNT ON;

 IF EXISTS (SELECT Mgr_ssn FROM Department WHERE Mgr_ssn IS NOT NULL 
 GROUP BY Mgr_ssn HAVING COUNT(Mgr_ssn) > 1)
 BEGIN
  RAISERROR ('Error: Employee is already manager of another department.', 16, 1);
  ROLLBACK TRANSACTION;
 END
END;


-------

GO
USE COMPANY;
GO

Select * from Department;
Select * from Employee;

INSERT INTO Department VALUES ('Tecnologia', 11, 21312332, '2012-08-02');

```

### *d)*

```
GO
CREATE TRIGGER Exd ON Employee
AFTER INSERT, UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    UPDATE E
    SET E.Salary = M.Salary - 1
    FROM Employee AS E
    JOIN inserted AS I ON E.Ssn = I.Ssn
    JOIN Employee AS M ON M.Ssn = I.Super_ssn
    WHERE E.Salary > M.Salary;

    IF EXISTS (
        SELECT 1
        FROM Employee AS E
        JOIN Department AS D ON E.Dno = D.Dnumber
        JOIN Employee AS M ON D.Mgr_ssn = M.Ssn
        WHERE E.Salary > M.Salary
    )
    BEGIN
        UPDATE E
        SET E.Salary = M.Salary - 1
        FROM Employee AS E
        JOIN Department AS D ON E.Dno = D.Dnumber
        JOIN Employee AS M ON D.Mgr_ssn = M.Ssn
        WHERE E.Salary > M.Salary;
    END
END;
GO


--------
GO
USE COMPANY;
GO

SELECT E.Fname, E.Lname, E.Salary
FROM Employee E
JOIN Department D ON E.Ssn = D.Mgr_ssn
WHERE D.Dnumber = 1;

INSERT INTO Employee
VALUES ('Test1', 'T', 'Employee', 999999998, '1990-01-01', 'Test Address', 'F', 1300, 21312332, 1);

SELECT Fname, Lname, Salary
FROM Employee
WHERE Ssn = 999999998;


```

### *e)*

```
CREATE FUNCTION getProjects ( @ssn INT ) RETURNS TABLE
AS 
 RETURN ( SELECT Pname, Plocation
     FROM Project
     JOIN Works_on ON Project.Pnumber = Works_on.Pno
     JOIN Employee ON Works_on.Essn = Employee.Ssn
     WHERE Employee.Ssn = @ssn);

GO
SELECT * FROM getProjects(321233765)
```

### *f)*

```
CREATE FUNCTION getFunctionaries ( @dno INT ) RETURNS TABLE
AS 
 RETURN ( SELECT *
     FROM Employee
     JOIN Department ON Employee.Dno = Department.Dnumber
     WHERE Department.Dnumber = @dno
     AND Employee.Salary > (SELECT AVG(Salary)
             FROM Employee
             WHERE Employee.Dno = @dno)
 );

GO
SELECT * FROM getFunctionaries(2)
```

### *g)*

```
DROP FUNCTION IF EXISTS dbo.employeeDeptHighAverage
GO
CREATE FUNCTION dbo.employeeDeptHighAverage (@dno INT)
RETURNS @budgetInfo TABLE (
    Pname        VARCHAR(255),
    Pnumber      INT,
    Plocation    VARCHAR(255),
    Dnum         INT,
    Budget       DECIMAL(10,2),
    TotalBudget  DECIMAL(10,2)
)
AS
BEGIN
    DECLARE @Pname VARCHAR(255), @Pnumber INT, @Plocation VARCHAR(255), @Dnum INT, @Budget DECIMAL(10,2), @TotalBudget DECIMAL(10,2) = 0;

    DECLARE budgetCursor CURSOR FOR
        SELECT Pname, Pnumber, Plocation, Dnum
            FROM Project
            WHERE Dnum = @dno;

    OPEN budgetCursor;

    FETCH NEXT FROM budgetCursor INTO @Pname, @Pnumber, @Plocation, @Dnum;
    
    WHILE @@FETCH_STATUS = 0
    BEGIN
        SELECT @Budget = SUM((Salary / 160.0) * Hours * 4) 
            FROM Works_on
            JOIN Employee ON Works_on.Essn = Employee.Ssn
            WHERE Works_on.Pno = @Pnumber;

        SET @TotalBudget = @TotalBudget + ISNULL(@Budget, 0);

        INSERT INTO @budgetInfo(Pname, Pnumber, Plocation, Dnum, Budget, TotalBudget)
        VALUES (@Pname, @Pnumber, @Plocation, @Dnum, ISNULL(@Budget, 0), @TotalBudget);

        FETCH NEXT FROM budgetCursor INTO @Pname, @Pnumber, @Plocation, @Dnum;
    END;

    CLOSE budgetCursor;
    DEALLOCATE budgetCursor;

    RETURN;
END;
GO

SELECT * FROM dbo.employeeDeptHighAverage(3);
```

### *h)*

```
IF (EXISTS (SELECT * 
    FROM INFORMATION_SCHEMA.TABLES 
                WHERE TABLE_SCHEMA = 'dbo' AND TABLE_NAME = 'department_deleted'))
BEGIN
    CREATE TABLE dbo.department_deleted (
        Dname VARCHAR(255),
        Dnumber INT PRIMARY KEY,
        Mgr_ssn CHAR(9),
        Mgr_start_date DATE
    );
END
GO

ALTER TRIGGER trg_AfterDeleteDepartment
ON dbo.DEPARTMENT
AFTER DELETE
AS
BEGIN
  
    INSERT INTO dbo.department_deleted (Dname, Dnumber, Mgr_ssn, Mgr_start_date)
    SELECT Dname, Dnumber, Mgr_ssn, Mgr_start_date
  FROM deleted;
END;
GO
```

### *h)*

```
IF (EXISTS (SELECT * 
    FROM INFORMATION_SCHEMA.TABLES 
                WHERE TABLE_SCHEMA = 'dbo' AND TABLE_NAME = 'department_deleted'))
BEGIN
    CREATE TABLE dbo.department_deleted (
        Dname VARCHAR(255),
        Dnumber INT PRIMARY KEY,
        Mgr_ssn CHAR(9),
        Mgr_start_date DATE
    );
END
GO

CREATE TRIGGER trg_InsteadOfDeleteDepartment
ON dbo.DEPARTMENT
INSTEAD OF DELETE
AS
BEGIN
    INSERT INTO dbo.department_deleted (Dname, Dnumber, Mgr_ssn, Mgr_start_date)
    SELECT Dname, Dnumber, Mgr_ssn, Mgr_start_date 
  FROM deleted;
    
    DELETE 
  FROM dbo.DEPARTMENT
  WHERE Dnumber IN (SELECT Dnumber FROM deleted);
END;
GO
```

### *i)*

```
Mais valias:
    Stored Procedures: Extensibilidade, performance, segurança e integridade de dados e reutilização de código

    UDFs: Extensibilidade, reutilização de código e simplicidade

Diferenças:
    - Valores de retorno: Stored Procedures podem retornar zero, um ou múltiplos resultados, e também parâmetros de saída. UDFs devem retornar exatamente um valor e não podem usar parâmetros de saída.
    - Consultas SQL: UDFs podem ser incorporadas dentro de consultas SQL, enquanto Stored Procedures não podem.
```
