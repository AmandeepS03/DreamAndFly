create database DreamAndFly;

create table user_account (
		email varchar(150) PRIMARY KEY	not null,
		nome varchar(30) not null,
        cognome varchar(30) not null,
        passw varchar(500) not null,
        telefono varchar(15) not null,
        ruolo int default 0 not null
        
);

create table capsula(
	id int primary key not null,
    prezzo_orario float not null,
    tipologia varchar(20) not null
);

create table fascia_oraria(
	numero int primary key not null,
    orario_inizio varchar(10) not null,
    orario_fine varchar(10) not null
);

create table e_prenotabile(
	data date not null,
	capsula_id int not null,
    fascia_oraria_numero int not null,
    primary key (capsula_id,fascia_oraria_numero),
	foreign key(capsula_id) references capsula(id) ON UPDATE CASCADE ON DELETE CASCADE,
	foreign key(fascia_oraria_numero) references fascia_oraria(numero) ON UPDATE CASCADE ON DELETE CASCADE


);

create table prenotazione (
	codice_di_accesso varchar(9) primary key not null,
    orario_inizio varchar(10) not null,
    orario_fine varchar(10) not null,
    data_inizio varchar(15) not null,
    data_fine varchar(15) not null,
    prezzo_totale float not null,
    data_effettuazione varchar(15) not null,
    validita boolean default 1 not null,
    rimborso float default 0 not null,
    user_account_email varchar(150) not null,
    capsula_id int not null,
	foreign key(user_account_email) references user_account(email) ON UPDATE CASCADE ON DELETE CASCADE,
	foreign key(capsula_id) references capsula(id) ON UPDATE CASCADE ON DELETE CASCADE

);



