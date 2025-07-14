use Guiao_4
go
Create Schema Gestao_Voos
go

create table Gestao_Voos.Airport(
Code varchar(15),
City varchar(20),
State varchar (20),
Name varchar(30),
primary key (Code)
)

create table Gestao_Voos.Airplane_Type(
Type_Name varchar(20),
Company varchar(20),
Max_Seats int,
primary key (Type_Name),
Check (Max_Seats > 0)
)

create table Gestao_Voos.Airplane(
Airplane_ID varchar(15),
Total_N_Seats int,
Type_Name_Air varchar(20),
foreign key (Type_Name_Air) references Gestao_Voos.Airplane_Type (Type_Name),
primary key (Airplane_ID),
Check (Total_N_Seats > 0)
)

create table Gestao_Voos.Flight(
Number int,
Airline varchar(20),
Weekdays varchar(50),
primary key (Number)
)

create table Gestao_Voos.FlightLeg(
Leg_Number int,
Schelued_Dep_time varchar(8),
Schelued_Arr_time varchar(8),
Flight_Number int,
Airport_code varchar(15),
foreign key (Flight_Number) references Gestao_Voos.Flight(Number),
foreign key (Airport_code) references Gestao_Voos.Airport(Code),
primary key (Leg_Number, Flight_Number)
)

create table Gestao_Voos.Leg_Instance(
Dep_Time varchar(15),
Arr_Time varchar(15),
No_Seats_Avail int,
Leg_Num_Ins int,
Flight_Num_Ins int,
AirPlane_ID_Inst varchar(15),
Airport_code_Inst varchar(15),
Date_Inst varchar(8),
foreign key (Leg_Num_Ins, Flight_Num_Ins) references Gestao_Voos.FlightLeg(Leg_Number, Flight_Number),
foreign key (AirPlane_ID_Inst) references Gestao_Voos.Airplane(Airplane_ID),
foreign key (Airport_code_Inst) references Gestao_Voos.Airport(Code),
primary key (Leg_Num_Ins, Flight_Num_Ins, Date_Inst)
)

create table Gestao_Voos.Seat(
Customer_Name varchar(30),
Cphone int,
Leg_Num_Ins_Seat int,
Flight_Num_Ins_Seat int,
Date_Inst_Seat varchar(8),
Seat_No int,
foreign key (Leg_Num_Ins_Seat, Flight_Num_Ins_Seat,Date_Inst_Seat) references Gestao_Voos.Leg_Instance(Leg_Num_Ins, Flight_Num_Ins, Date_Inst),
primary key (Leg_Num_Ins_Seat, Flight_Num_Ins_Seat, Date_Inst_Seat, Seat_No),
Check (len(Cphone) > 5) -- Numeros de telefone devem ter 6 ou mais numeros
)

create table Gestao_Voos.Fare(
Code int,
Amount float,
Restrictions varchar(50),
Flight_Number int,
foreign key (Flight_Number) references Gestao_Voos.Flight(Number),
primary key (Flight_Number, Code),
Check (Amount > 0.0)
)

create table Gestao_Voos.CanLand(
Type_Name_Air varchar(20),
Airport_code varchar(15),
foreign key (Type_Name_Air) references Gestao_Voos.Airplane_Type (Type_Name),
foreign key (Airport_code) references Gestao_Voos.Airport(Code),
primary key (Type_Name_Air, Airport_code)
)