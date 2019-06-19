DROP SCHEMA IF EXISTS webp_vasic;
CREATE SCHEMA webp_vasic DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE webp_vasic;


CREATE TABLE aerodrom(
   	id INT PRIMARY KEY AUTO_INCREMENT,
    naziv VARCHAR(100) NOT NULL
);


CREATE TABLE let (
	id INT PRIMARY KEY AUTO_INCREMENT,
    broj VARCHAR(8) not null,
    datum_polaska DATETIME NOT NULL,
    datum_dolaska DATETIME NOT NULL,
    polazni_aerodrom_id INT NOT NULL,
    dolazni_aerodrom_id INT NOT NULL,
    broj_sedista INT UNSIGNED NOT NULL,
    cena FLOAT unsigned NOT NULL,
    izbrisan BOOLEAN NOT NULL,
    FOREIGN KEY(polazni_aerodrom_id) REFERENCES aerodrom(id),
	FOREIGN KEY(dolazni_aerodrom_id) REFERENCES aerodrom(id)
);

CREATE TABLE korisnik (
    id INT PRIMARY KEY AUTO_INCREMENT,
    korisnicko_ime VARCHAR(50) NOT NULL UNIQUE,
    lozinka VARCHAR(50) NOT NULL,
    datum_registracije DATETIME NOT NULL,
    admin BOOLEAN NOT NULL,
    blokiran BOOLEAN NOT NULL,
    izbrisan BOOLEAN NOT NULL
);

CREATE TABLE rezervacija (
    id INT PRIMARY KEY AUTO_INCREMENT,
    polazni_let_id INT NOT NULL,
    povratni_let_id INT,
    sediste_polazni_let INT NOT NULL,
    sediste_povratni_let INT,
    datum_rezervacije DATETIME NOT NULL,
    datum_prodaje_karte DATETIME,
    korisnicko_ime VARCHAR(50) NOT NULL,
    ime_putnika VARCHAR(50) NOT NULL,
    prezime_putnika VARCHAR(50) NOT NULL,
    ukupna_cena FLOAT NOT NULL,
    FOREIGN KEY(polazni_let_id) REFERENCES let(id),
    FOREIGN KEY(povratni_let_id) REFERENCES let(id),
    FOREIGN KEY(korisnicko_ime) REFERENCES korisnik(korisnicko_ime)
);

INSERT INTO aerodrom(naziv) VALUES ("Beograd"); 
INSERT INTO aerodrom(naziv) VALUES ("Novi Sad"); 
INSERT INTO aerodrom(naziv) VALUES ("Nis"); 
INSERT INTO aerodrom(naziv) VALUES ("Zagreb"); 
INSERT INTO aerodrom(naziv) VALUES ("Budimpesta");
INSERT INTO aerodrom(naziv) VALUES ("Sarajevo"); 
INSERT INTO aerodrom(naziv) VALUES ("Podgorica"); 



INSERT INTO korisnik(korisnicko_ime,lozinka,datum_registracije,admin,blokiran,izbrisan) VALUES ("admin","admin",now(),1,0,0);
INSERT INTO korisnik(korisnicko_ime,lozinka,datum_registracije,admin,blokiran,izbrisan) VALUES ("vasic","12345",now(),0,0,0);
INSERT INTO korisnik(korisnicko_ime,lozinka,datum_registracije,admin,blokiran,izbrisan) VALUES ("aleksandar","12345",now(),0,0,0);


-- Prosli letovi

INSERT INTO let(broj,datum_polaska,datum_dolaska,polazni_aerodrom_id,dolazni_aerodrom_id,broj_sedista,cena,izbrisan) 
VALUES ("BENO1111",'2019-02-21 10:00','2019-02-21 10:45',1,2,45,4000,0); 
INSERT INTO let(broj,datum_polaska,datum_dolaska,polazni_aerodrom_id,dolazni_aerodrom_id,broj_sedista,cena,izbrisan) 
VALUES ("BENO1121",'2019-03-21 10:00','2019-03-21 10:45',1,2,45,4000,0); 
INSERT INTO let(broj,datum_polaska,datum_dolaska,polazni_aerodrom_id,dolazni_aerodrom_id,broj_sedista,cena,izbrisan) 
VALUES ("NOZG2111",'2019-03-21 11:00','2019-03-21 12:45',2,4,55,12000,0); 
INSERT INTO let(broj,datum_polaska,datum_dolaska,polazni_aerodrom_id,dolazni_aerodrom_id,broj_sedista,cena,izbrisan) 
VALUES ("ZGNO1112",'2019-03-22 10:00','2019-03-22 11:45',4,2,65,11000,0); 
INSERT INTO let(broj,datum_polaska,datum_dolaska,polazni_aerodrom_id,dolazni_aerodrom_id,broj_sedista,cena,izbrisan) 
VALUES ("NOBE1113",'2019-02-22 10:00','2019-02-22 10:45',2,1,40,5000,0); 
INSERT INTO let(broj,datum_polaska,datum_dolaska,polazni_aerodrom_id,dolazni_aerodrom_id,broj_sedista,cena,izbrisan) 
VALUES ("BUNI",'2019-05-22 10:00','2019-05-22 12:45',2,1,40,19000,0); 

INSERT INTO rezervacija(polazni_let_id,povratni_let_id,sediste_polazni_let,sediste_povratni_let,datum_rezervacije,datum_prodaje_karte,korisnicko_ime,ime_putnika,prezime_putnika,ukupna_cena)
VALUES(1,null,11,0,'2019-02-18 10:00','2019-02-18 10:00',"vasic","Aleksandar","Vasic",4000);
INSERT INTO rezervacija(polazni_let_id,povratni_let_id,sediste_polazni_let,sediste_povratni_let,datum_rezervacije,datum_prodaje_karte,korisnicko_ime,ime_putnika,prezime_putnika,ukupna_cena)
VALUES(1,null,12,0,'2019-02-18 11:00','2019-02-18 11:00',"vasic","Dusan","Vasic",4000);
INSERT INTO rezervacija(polazni_let_id,povratni_let_id,sediste_polazni_let,sediste_povratni_let,datum_rezervacije,datum_prodaje_karte,korisnicko_ime,ime_putnika,prezime_putnika,ukupna_cena)
VALUES(1,5,13,13,'2019-02-18 11:00','2019-02-18 11:00',"aleksandar","Jasmina","Vasic",9000);


INSERT INTO rezervacija(polazni_let_id,povratni_let_id,sediste_polazni_let,sediste_povratni_let,datum_rezervacije,datum_prodaje_karte,korisnicko_ime,ime_putnika,prezime_putnika,ukupna_cena)
VALUES(2,null,1,0,'2019-03-20 10:00','2019-03-20 10:00',"vasic","Aleksandar","Vasic",4000);
INSERT INTO rezervacija(polazni_let_id,povratni_let_id,sediste_polazni_let,sediste_povratni_let,datum_rezervacije,datum_prodaje_karte,korisnicko_ime,ime_putnika,prezime_putnika,ukupna_cena)
VALUES(2,null,3,0,'2019-03-19 11:00','2019-03-19 11:00',"aleksandar","Dusan","Vasic",4000);


INSERT INTO rezervacija(polazni_let_id,povratni_let_id,sediste_polazni_let,sediste_povratni_let,datum_rezervacije,datum_prodaje_karte,korisnicko_ime,ime_putnika,prezime_putnika,ukupna_cena)
VALUES(3,null,22,0,'2019-02-18 10:00','2019-02-18 10:00',"vasic","Aleksandar","Vasic",12000);
INSERT INTO rezervacija(polazni_let_id,povratni_let_id,sediste_polazni_let,sediste_povratni_let,datum_rezervacije,datum_prodaje_karte,korisnicko_ime,ime_putnika,prezime_putnika,ukupna_cena)
VALUES(3,4,9,4,'2019-02-18 11:00','2019-02-18 11:00',"vasic","Dusan","Vasic",23000);
INSERT INTO rezervacija(polazni_let_id,povratni_let_id,sediste_polazni_let,sediste_povratni_let,datum_rezervacije,datum_prodaje_karte,korisnicko_ime,ime_putnika,prezime_putnika,ukupna_cena)
VALUES(3,4,7,6,'2019-02-18 11:00','2019-02-18 11:00',"aleksandar","Jasmina","Vasic",23000);

INSERT INTO rezervacija(polazni_let_id,povratni_let_id,sediste_polazni_let,sediste_povratni_let,datum_rezervacije,datum_prodaje_karte,korisnicko_ime,ime_putnika,prezime_putnika,ukupna_cena)
VALUES(4,null,20,0,'2019-02-18 10:00','2019-02-18 10:00',"vasic","Aleksandar","Vasic",11000);
INSERT INTO rezervacija(polazni_let_id,povratni_let_id,sediste_polazni_let,sediste_povratni_let,datum_rezervacije,datum_prodaje_karte,korisnicko_ime,ime_putnika,prezime_putnika,ukupna_cena)
VALUES(4,3,31,32,'2019-02-18 11:00','2019-02-18 11:00',"vasic","Dusan","Vasic",23000);
INSERT INTO rezervacija(polazni_let_id,povratni_let_id,sediste_polazni_let,sediste_povratni_let,datum_rezervacije,datum_prodaje_karte,korisnicko_ime,ime_putnika,prezime_putnika,ukupna_cena)
VALUES(4,3,27,26,'2019-02-18 11:00','2019-02-18 11:00',"aleksandar","Jasmina","Vasic",23000);

INSERT INTO rezervacija(polazni_let_id,povratni_let_id,sediste_polazni_let,sediste_povratni_let,datum_rezervacije,datum_prodaje_karte,korisnicko_ime,ime_putnika,prezime_putnika,ukupna_cena)
VALUES(5,null,13,0,'2019-02-18 10:00','2019-02-18 10:00',"vasic","Aleksandar","Vasic",5000);
INSERT INTO rezervacija(polazni_let_id,povratni_let_id,sediste_polazni_let,sediste_povratni_let,datum_rezervacije,datum_prodaje_karte,korisnicko_ime,ime_putnika,prezime_putnika,ukupna_cena)
VALUES(5,null,17,0,'2019-02-18 11:00','2019-02-18 11:00',"vasic","Dusan","Vasic",5000);

INSERT INTO rezervacija(polazni_let_id,povratni_let_id,sediste_polazni_let,sediste_povratni_let,datum_rezervacije,datum_prodaje_karte,korisnicko_ime,ime_putnika,prezime_putnika,ukupna_cena)
VALUES(6,null,20,0,'2019-02-18 10:00','2019-02-18 10:00',"vasic","Aleksandar","Vasic",19000);
INSERT INTO rezervacija(polazni_let_id,povratni_let_id,sediste_polazni_let,sediste_povratni_let,datum_rezervacije,datum_prodaje_karte,korisnicko_ime,ime_putnika,prezime_putnika,ukupna_cena)
VALUES(6,null,31,0,'2019-02-18 11:00','2019-02-18 11:00',"vasic","Dusan","Vasic",19000);


INSERT INTO let(broj,datum_polaska,datum_dolaska,polazni_aerodrom_id,dolazni_aerodrom_id,broj_sedista,cena,izbrisan) 
VALUES ("BENO1111",'2019-09-21 10:00','2019-09-21 10:45',1,2,45,4000,0); 
INSERT INTO let(broj,datum_polaska,datum_dolaska,polazni_aerodrom_id,dolazni_aerodrom_id,broj_sedista,cena,izbrisan) 
VALUES ("BENO1121",'2019-09-21 10:00','2019-09-21 10:45',1,2,45,4000,0); 
INSERT INTO let(broj,datum_polaska,datum_dolaska,polazni_aerodrom_id,dolazni_aerodrom_id,broj_sedista,cena,izbrisan) 
VALUES ("NOZG2111",'2019-09-21 11:00','2019-09-21 12:45',2,4,55,12000,0); 
INSERT INTO let(broj,datum_polaska,datum_dolaska,polazni_aerodrom_id,dolazni_aerodrom_id,broj_sedista,cena,izbrisan) 
VALUES ("ZGNO1112",'2019-09-22 10:00','2019-09-22 11:45',4,2,65,11000,0); 
INSERT INTO let(broj,datum_polaska,datum_dolaska,polazni_aerodrom_id,dolazni_aerodrom_id,broj_sedista,cena,izbrisan) 
VALUES ("NOBE1113",'2019-09-22 10:00','2019-09-22 10:45',2,1,40,5000,0); 
INSERT INTO let(broj,datum_polaska,datum_dolaska,polazni_aerodrom_id,dolazni_aerodrom_id,broj_sedista,cena,izbrisan) 
VALUES ("BUNI1234",'2019-09-22 10:00','2019-09-22 12:45',2,1,40,19000,0);












