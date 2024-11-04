truncate table users cascade;
truncate table wallets cascade;
truncate table transactions cascade;

insert into users(id,key_cloak_id,firstname,lastname,email,date_created,password) values
(101, '','John', 'Doe', 'johndoe@example.com', '2024-06-04T15:03:03.7992009700', '$2a$10$LUn5.yR9nChGEVFyjubjRuON6PgrhW7ZoQkMvZS/ajFWvf1KwQ8/G'),
(102, '','Daniel', 'Gabriel', 'daniel@example.com', '2024-06-04T15:03:03.7992009700', '$2a$10$LUn5.yR9nChGEVFyjubjRuON6PgrhW7ZoQkMvZS/ajFWvf1KwQ8/G'),
(103,'751dd93b-dc55-48b6-bfa2-8a5b7e804212','philip', 'daniel', 'ajibola@gmail.com', '2024-06-04T15:03:03.7992009700', '$2a$10$LUn5.yR9nChGEVFyjubjRuON6PgrhW7ZoQkMvZS/ajFWvf1KwQ8/G');


insert into wallets(id, balance,pin,date_created) values
(101, 0.00,'0000','2024-06-04T15:03:03.7992009700'),
(102, 0.00,'0000','2024-06-04T15:03:03.7992009700'),
(103, 0.00,'0000','2024-06-04T15:03:03.7992009700');


insert into transactions(id, wallet_id, transaction_type,status, amount, date_created) values
(103,101,'TRANSFER','SUCCESSFUL',5000,'2024-06-04T15:03:03.7992009700'),
(104,101,'DEPOSIT','SUCCESSFUL',50000,'2024-06-04T15:03:03.7992009700'),
(105,101,'DEPOSIT','SUCCESSFUL',50000,'2024-06-04T15:03:03.7992009700'),
(108,103,'DEPOSIT','SUCCESSFUL',50000,'2024-06-04T15:03:03.7992009700'),
(107,103,'DEPOSIT','SUCCESSFUL',50000,'2024-06-04T15:03:03.7992009700'),
(106,101,'TRANSFER','SUCCESSFUL',5000,'2024-06-04T15:03:03.7992009700');