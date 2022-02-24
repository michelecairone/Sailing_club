create database if not exists sailing_club;

use sailing_club;

create table person (
  cf varchar(30),
  name varchar(20),
  surname varchar(20),
  address varchar(30),
  username varchar(20),
  password varchar(80),
  category varchar(1),
  PRIMARY KEY (username,cf)
);

create table boat (
  id int(6) auto_increment,
  name varchar(30),
  length int(20),
  owner varchar(20),
  PRIMARY KEY (id),
  FOREIGN KEY (owner) REFERENCES Person (username) ON DELETE CASCADE
);

create table race (
  id int(10) auto_increment,
  name varchar(30),
  date date,
  date_expiration date,
  max_num_m int(20),
  quote float(20,5),
  PRIMARY KEY (id)
);

create table participation (
	id_race int(10),
  id_boat int(10),
  pay_date date,
  FOREIGN KEY (id_race) REFERENCES race (id) ON DELETE CASCADE,
	FOREIGN KEY (id_boat) REFERENCES boat (id) ON DELETE CASCADE
);
create table payment_fee (
	pay_type varchar(20),
  quote float(10,2)
);
create table membership_fee (
  payer_id varchar(20),
  payment_date date,
  FOREIGN KEY (payer_id) REFERENCES person (username) ON DELETE CASCADE
);
create table storage_fee (
	id_boat int(20),
  payment_date date,
  quote float(10,2),
  FOREIGN KEY (id_boat) REFERENCES boat (id) ON DELETE CASCADE
);
create table notice (
  member varchar(20),
  type_notice varchar(20),
  FOREIGN KEY (member) REFERENCES person(username) ON DELETE CASCADE
);

insert into person values ('FCQGFD85R19E931V','Michele', 'Cairone', 'Via Trento, 67 Parma', 'MCairone', 'MC456', 'S');
insert into person values ('GCCZPV41L22A597B', 'Leonardo', 'Minaudo', 'Via Palermo, 26 Parma', 'LMinaudo', 'LM123', 'S');
insert into person values ('ZCHHPG37L49E569P', 'Filip', 'Pino', 'Via del Sole, 15 Modena', 'FPino', '123stella', 'M');
insert into person values ('VNQKBA29R18D650B', 'Giovanni', 'Po', 'Via Trento, 8 Bologna', 'giovi', '123', 'M');
insert into person values ('MKVDRT91E46Z503H', 'Sara', 'Millefiori', 'Via Palermo, 105/B Milano', 'mllfiori', 'sara', 'M');

insert into payment_fee values ('membership',30.00);
insert into payment_fee values ('storage',10.00);

insert into boat values (0,"Giusta Rotta",12,"giovi");
insert into boat values (0,"Alla deriva",9,"giovi");
insert into boat values (0,"Titanic",15,"FPino");
insert into boat values (0,"L’Italia s’è desta",10,"FPino");
insert into boat values (0,"Stella Polare",8,"FPino");
insert into boat values (0," Andromeda",12,"mllfiori");

insert into race values (0,'Race1','2022-02-02', '2022-05-21', 10,359.99);
insert into race values (0, 'Race2','2022-02-28', '2022-10-21', 20,159.99);
insert into race values (0, 'Race3','2022-05-22', '2022-08-19', 50,19.99);
insert into race values (0, 'race4','2022-08-07', '2022-07-07', 500,49.99);
