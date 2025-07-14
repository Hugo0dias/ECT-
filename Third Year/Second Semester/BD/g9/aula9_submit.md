# BD: Guião 9


## ​9.1. Complete a seguinte tabela.
Complete the following table.

| #    | Query                                                                                                      | Rows  | Cost  | Pag. Reads | Time (ms) | Index used | Index Op.            | Discussion |
| :--- | :--------------------------------------------------------------------------------------------------------- | :---- | :---- | :--------- | :-------- | :--------- | :------------------- | :--------- |
| 1    | SELECT * from Production.WorkOrder                                                                         | 72591 | 0.484 | 531        | 1171      | …          | Clustered Index Scan |            |
| 2    | SELECT * from Production.WorkOrder where WorkOrderID=1234                                                  |   1       | 0.00328       |       26      |   79        |    WorkOrderID (PK)        |    Clustered Index Seek   |   Foi criado automaticamente um clustered index associado à primary key WorkOrderID.         |
| 3.1  | SELECT * FROM Production.WorkOrder WHERE WorkOrderID between 10000 and 10010                               |   11    | 0.00329     |     26       |     128      |     WorkOrderID (PK)       |    Clustered Index Seek   |   Rows = 11, sendo esta a dimensão do intervalo pretendido.         |
| 3.2  | SELECT * FROM Production.WorkOrder WHERE WorkOrderID between 1 and 72591                                   |   72591    |  0.473     |     801       |     7602      |   WorkOrderID (PK)         |        Clustered Index Seek              |          |
| 4    | SELECT * FROM Production.WorkOrder WHERE StartDate = '2012-05-14'                                         |   72591    |   0.473    |     762       |      307     |    WorkOrderID (PK)        |        Clustered Index Scan              |    Não existe um índice associado a StartDate. Portanto, o "Cost" foi alto.        |
| 5    | SELECT * FROM Production.WorkOrder WHERE ProductID = 757                                                   |   9    |   0.0368    |     260       |     177      |     ProductID       |   Non Clustered Index Seek     |  Feito com "NONCLUSTERED INDEX" associado a ProductID. Daí o custo ser baixo.  |
| 6.1  | SELECT WorkOrderID, StartDate FROM Production.WorkOrder WHERE ProductID = 757                              |   9    |   0.00324    |     252       |     3      |     ProductID Covered (StartDate)       |       Non Clustered Index Seek        |        Como a procura foi feita em função de ProductID o Custo foi baixo.      |
| 6.2  | SELECT WorkOrderID, StartDate FROM Production.WorkOrder WHERE ProductID = 945                              |  1105     |   0.00598    |    253       |     315     |   ProductID Covered (StartDate)  |       Non Clustered Index Seek      |      Como a procura foi feita em função de ProductID o Custo foi baixo.     |
| 6.3  | SELECT WorkOrderID FROM Production.WorkOrder WHERE ProductID = 945 AND StartDate = '2011-12-04'    |   1    |   0.00598    |     244      |      23     |    ProductID Covered (StartDate)        |    Non Clustered Index Seek    |     Como a procura foi feita em função de ProductID o Custo foi baixo.        |
| 7    | SELECT WorkOrderID, StartDate FROM Production.WorkOrder  WHERE ProductID = 945 AND StartDate = '2011-12-04' |   1    |   0.0167   |      93      |     71      |     ProductID and StartDate       |      Non Clustered Index Seek   |      "NONCLUSTERED INDEX" associado a ProductID e outro associado a StartDate torna custo baixo.       |
| 8    | SELECT WorkOrderID, StartDate FROM Production.WorkOrder WHERE ProductID = 945 AND StartDate = '2011-12-04' |   1    |   0.00328    |     69    |     5      |     Composite (ProductID, StartDate)       |        Non Clustered Index Seek       |     "NONCLUSTERED INDEX" composto associado a ProductID e a StartDate o que torna custo baixo.       |
## ​9.2.

### a)

```
CREATE TABLE mytemp (
    rid BIGINT /*IDENTITY (1, 1)*/ NOT NULL,
    at1 INT NULL,
    at2 INT NULL,
    at3 INT NULL,
    lixo varchar(100) NULL,
    PRIMARY KEY (rid) -- por defeito cria uma unique clustered index
);
```

### b)

```
Page fullness : 68,94 %
Total Fragmentation : 99,06 %
```

### c)

```
Clustered Index (Factor = 65)
    Duration : 82810 ms

Clustered Index (Factor = 80)
    Duration : 89253 ms

Clustered Index (Factor = 90)
    Duration : 89300 ms
```

### d)

```
Testado com o Factor = 90, o tempo aumentou para 94253 ms
```

### e)

```
Com multiplos indexes o tempo aumentou drasticamente : 111026 ms
```

## ​9.3.

```
i. Sendo chave primária cria automaticamente um clustered index
ii. CREATE INDEX ixEmployeeFLname ON EMPLOYEE (Fname, Lname);
iii. CREATE INDEX ixEmployeeDno ON EMPLOYEE (Dno);
iv. CREATE INDEX ixWorksOnPno on WORKS_ON (Pno);
v. Sendo chave primária cria automaticamente um clustered index
vi. CREATE INDEX ixProjectDnum ON PROJECT (Dnum);
```