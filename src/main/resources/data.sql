insert into user_table (first_name, last_name, email, phone_number, password, role) values ('Jelena','Stanarevic','jelena@gmail.com', '596262652', 'jelena','GUEST');
insert into user_table (first_name, last_name, email, phone_number, password, role) values ('Ilija','Simic','sima@gmail.com', '5558896', 'ilija','GUEST');

insert into institution (description, name, longitude, latitude) values ('opis...', 'ArenaCineplex', 1, 1);
insert into institution (description, name, longitude, latitude) values ('opissss...', 'SNP', 2, 2);

insert into auditorium (name, id_institution) values ('sala1', 1);
insert into auditorium (name, id_institution) values ('sala2', 1);
insert into auditorium (name, id_institution) values ('sala3', 1);

insert into segment (closed, label, id_auditorium) values (0, '1A', 1);
insert into segment (closed, label, id_auditorium) values (0, '1B', 1);

insert into seat (row, seat_number, id_segment) values (1, 1, 1);
insert into seat (row, seat_number, id_segment) values (1, 2, 1);
insert into seat (row, seat_number, id_segment) values (1, 3, 1);
insert into seat (row, seat_number, id_segment) values (1, 4, 1);



