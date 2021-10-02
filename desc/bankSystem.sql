create database bankManagement;
use bankManagement;

create table branch(
	branch_id int NOT NULL AUTO_INCREMENT,
	city varchar(70),
	branch_code varchar(8) NOT NULL,
	PRIMARY KEY(branch_id)
);

create table bank(
	bank_id int NOT NULL AUTO_INCREMENT,
	branch_id int NOT NULL,
	bank_name varchar(100),
	bank_address varchar(200),
	PRIMARY KEY(bank_id),
	FOREIGN KEY(branch_id) REFERENCES branch(branch_id)
);

create table employee(
	employee_id varchar(12) NOT NULL,
	branch_id int NOT NULL,
	emp_firstName varchar(60),
	emp_lastName varchar(50),
	password varchar(100),
	PRIMARY KEY(employee_id),
	FOREIGN KEY(branch_id) REFERENCES branch(branch_id)
);

create table customer(
	customer_id int NOT NULL AUTO_INCREMENT,
	customer_firstName varchar(60),
	customer_lastName varchar(40),
	customer_address varchar(200),
	contact_number varchar(12),
	PRIMARY KEY(customer_id),
);

create table account(
	account_id varchar(12) NOT NULL,
	branch_id int,
	customer_id int,
	balance double,
	account_pin varchar(100),
	PRIMARY KEY(account_id),
	FOREIGN KEY(branch_id) REFERENCES branch(branch_id),
	FOREIGN KEY(customer_id) REFERENCES customer(customer_id)
);

create table current_account(
	account_id varchar(12) NOT NULL,
	current_balance double,
	date_opened timestamp,
	PRIMARY KEY(account_id),
	FOREIGN KEY(account_id) REFERENCES account(account_id)
);

create table savings_account(
	account_id varchar(12) NOT NULL,
	min_balance double,
	date_opened timestamp,
	temp_date date,
	PRIMARY KEY(account_id),
	FOREIGN KEY(account_id) REFERENCES account(account_id)
);

create table user_loginHistory(
	account_id varchar(12) NOT NULL,
	datetime timestamp NOT NULL,
	FOREIGN KEY(account_id) REFERENCES account(account_id)
);

create table loan(
	branch_id int,
	customer_id int,
	loan_no int NOT NULL AUTO_INCREMENT,
	amount double,
	loan_type varchar(20),
	loan_date date,
	loan_balance double,
	loan_paid boolean DEFAULT false,
	temp_date date,
	end_date date,
	PRIMARY KEY(loan_no),
	FOREIGN KEY(branch_id) REFERENCES branch(branch_id),
	FOREIGN KEY(customer_id) REFERENCES customer(customer_id)
);

alter table loan AUTO_INCREMENT = 10000;

create table loan_history(
	history_id int NOT NULL auto_increment,
	customer_id int NOT NULL,
	loan_no INT NOT NULL,
	loan_type varchar(20),
	action varchar(40),
	amount double,	
	datetime timestamp NOT NULL,
	PRIMARY KEY(history_id),
	FOREIGN KEY(customer_id) REFERENCES customer(customer_id),
);

create table transaction_history(
	trans_history_id int NOT NULL AUTO_INCREMENT,
	account_id varchar(12) NOT NULL,
	action varchar(50),
	account_type varchar(30),
	amount double,	
	datetime timestamp NOT NULL,
	PRIMARY KEY(trans_history_id),
	FOREIGN KEY(account_id) REFERENCES account(account_id)
);

insert into branch(city, branch_code) VALUES('Butuan City','GHBU83');
insert into bank(branch_id,bank_name,bank_address) VALUES(1,'BC Bank','Butuan City, Agusan del Norte');
