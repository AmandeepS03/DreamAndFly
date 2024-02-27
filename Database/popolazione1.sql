/*ruolo (0,utente) (1,gestore capsule) (2,gestore account) (3,gestore prenotazioni)*/

INSERT INTO user_account (email, nome, cognome, passw, telefono, ruolo) 
VALUES 
    ('gestore.account@dreamandfly.com', 'Maria', 'Esposito', 'GestoreAccount1!', '123456789', 2),
    ('gestore.capsule1@dreamandfly.com', 'Armando', 'Bianchi', 'GestoreCapsule1!', '3257777777', 1),
    ('mario.rossi@gmail.com', 'Mario', 'Rossi', 'MyCapsula2023!', '3257777777',0),
    ('gestore.prenotazioni1@dreamandfly.com', 'Gennaro', 'Salerno', 'GestorePrenotazioni1!', '555666777', 3);
    
INSERT INTO capsula (id, prezzo_orario, tipologia) 
VALUES 
    (1, 9, 'Deluxe'),
    (2, 5, 'Standard'),
    (3, 7, 'Superior'),
    (4, 9, 'Deluxe');
    
INSERT INTO fascia_oraria (numero, orario_inizio, orario_fine) 
VALUES 
    (1, '00:00', '01:00'),
    (2, '01:00', '02:00'),
    (3, '02:00', '03:00'),
    (4, '03:00', '04:00'),
    (5, '04:00', '05:00'),
    (6, '05:00', '06:00'),
    (7, '06:00', '07:00'),
    (8, '07:00', '08:00'),
    (9, '08:00', '09:00'),
    (10, '09:00', '10:00'),
    (11, '10:00', '11:00'),
    (12, '11:00', '12:00'),
    (13, '12:00', '13:00'),
    (14, '13:00', '14:00'),
    (15, '14:00', '15:00'),
    (16, '15:00', '16:00'),
    (17, '16:00', '17:00'),
    (18, '17:00', '18:00'),
    (19, '18:00', '19:00'),
    (20, '19:00', '20:00'),
    (21, '20:00', '21:00'),
    (22, '21:00', '22:00'),
    (23, '22:00', '23:00'),
    (24, '23:00', '00:00');
    
INSERT INTO e_prenotabile (data_prenotabile, capsula_id, fascia_oraria_numero) 
VALUES 
    ('2024-02-27', 1, 1),
    ('2024-02-27', 2, 2);
    
INSERT INTO prenotazione ( orario_inizio, orario_fine, data_inizio, data_fine, prezzo_totale, data_effettuazione, user_account_email, capsula_id) 
VALUES 
    ( '08:00', '10:00', '2024-02-27', '2024-02-27', 25.0, '2024-02-26', 'mario.rossi@gmail.com', 1),
    ( '10:00', '12:00', '2024-02-28', '2024-02-28', 30.0, '2024-02-26', 'mario.rossi@gmail.com', 2);
    