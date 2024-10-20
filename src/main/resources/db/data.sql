truncate table users cascade;
truncate table wallets cascade;
truncate table transactions cascade;

insert into users(id,firstname,lastname,email,date_created,password) values
(101, 'John', 'Doe', 'johndoe@example.com', '2024-06-04T15:03:03.7992009700', 'password'),
(102, 'Daniel', 'Gabriel', 'daniel@example.com', '2024-06-04T15:03:03.7992009700', 'password');


insert into wallets(id, balance,date_created) values
(101, 0.00,'2024-06-04T15:03:03.7992009700'),
(102, 0.00,'2024-06-04T15:03:03.7992009700');


insert into transactions(id, wallet_id, transaction_type,status, amount, date_created) values
(103,101,'TRANSFER','SUCCESSFUL',5000,'2024-06-04T15:03:03.7992009700'),
(104,101,'DEPOSIT','SUCCESSFUL',50000,'2024-06-04T15:03:03.7992009700'),
(105,101,'DEPOSIT','SUCCESSFUL',50000,'2024-06-04T15:03:03.7992009700'),
(106,101,'TRANSFER','SUCCESSFUL',5000,'2024-06-04T15:03:03.7992009700');