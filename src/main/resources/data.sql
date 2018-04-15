insert into user_table (first_name, last_name, email, phone_number, password, role) values ('Jelena','Stanarevic','jelena@gmail.com', '596262652', 'jelena','REGISTERED');
insert into user_table (first_name, last_name, email, phone_number, password, role) values ('Ilija','Simic','sima@gmail.com', '5558896', 'ilija','ADMIN_SYS');
insert into user_table (first_name, last_name, email, phone_number, password, role) values ('Marko','Markovic','marko@gmail.com', '5558896', 'marko','ADMIN_SYS');

insert into institution (description, name, longitude, latitude, is_cinema) values ('opis...', 'ArenaCineplex', 1.0, 1.0, true);
insert into institution (description, name, longitude, latitude, is_cinema) values ('opis...', 'Cinestar', 1.0, 1.0, true);
insert into institution (description, name, longitude, latitude, is_cinema) values ('opissss...', 'SNP', 2.0, 2.0, false);

insert into auditorium (name, id_institution) values ('sala1', 1);
insert into auditorium (name, id_institution) values ('sala2', 1);
insert into auditorium (name, id_institution) values ('sala3', 1);

insert into segment (row_count,seats_in_row_count,closed, label, id_auditorium) values (2, 3, 0, '1A', 1);
insert into segment (row_count,seats_in_row_count,closed, label, id_auditorium) values (3, 3, 0, '1B', 1);

insert into seat (row, seat_number, id_segment) values (1, 1, 1);
insert into seat (row, seat_number, id_segment) values (1, 2, 1);
insert into seat (row, seat_number, id_segment) values (1, 3, 1);
insert into seat (row, seat_number, id_segment) values (2, 1, 1);
insert into seat (row, seat_number, id_segment) values (2, 2, 1);
insert into seat (row, seat_number, id_segment) values (2, 3, 1);


insert into seat (row, seat_number, id_segment) values (1, 1, 2);
insert into seat (row, seat_number, id_segment) values (1, 2, 2);
insert into seat (row, seat_number, id_segment) values (1, 3, 2);
insert into seat (row, seat_number, id_segment) values (2, 1, 2);
insert into seat (row, seat_number, id_segment) values (2, 2, 2);
insert into seat (row, seat_number, id_segment) values (2, 3, 2);
insert into seat (row, seat_number, id_segment) values (3, 1, 2);
insert into seat (row, seat_number, id_segment) values (3, 2, 2);
insert into seat (row, seat_number, id_segment) values (3, 3, 2);


insert into show(name,genre,producer,duration, id_institution, description) values ('Red sparrow','thriller', 'Francis Lorens', 141, 1, 'frfrgvregvrwv');
insert into show(name,genre,producer,duration, id_institution, description) values ('LED','drama', 'Oleg Trofim', 113, 1, 'rgergergergervvb');
insert into show(name,genre,producer,duration, id_institution, description) values ('Rampage 3D','action', 'Brad Peyton', 107, 1, 'vruihveuie');
insert into show(name,genre,producer,duration, id_institution, description) values ('Gnome Alone 3D','comedy', 'Peter Lepeniotis', 89, 1, 'fjwruhfirhrue');

insert into actor(name, lastname) values ('imeGlumca1', 'prezimeGlumca1');
insert into actor(name, lastname) values ('imeGlumca2', 'prezimeGlumca2');
insert into actor(name, lastname) values ('imeGlumca3', 'prezimeGlumca3');
insert into actor(name, lastname) values ('imeGlumca4', 'prezimeGlumca4');

insert into show_actor(show_id, actor_id) values (1, 1);
insert into show_actor(show_id, actor_id) values (1, 2);
insert into show_actor(show_id, actor_id) values (1, 3);

insert into projection(id_show,date,id_auditorium) values (1,'2018-06-28 21:00:00',1);
insert into projection(id_show,date,id_auditorium) values (1,'2018-06-28 20:00:00',2);
insert into projection(id_show,date,id_auditorium) values (2,'2018-06-14 19:30:00',3);
insert into projection(id_show,date,id_auditorium) values (2,'2018-06-28 21:00:00',1);
insert into projection(id_show,date,id_auditorium) values (3,'2018-06-28 22:00:00',1);
insert into projection(id_show,date,id_auditorium) values (4,'2018-06-28 23:00:00',1);

insert into ticket(price,id_seat,id_projection,reserved) values('220.00',1,1,false);
insert into ticket(price,id_seat,id_projection,reserved) values('240.00',2,1,false);
insert into ticket(price,id_seat,id_projection,reserved) values('220.00',3,1,false);
insert into ticket(price,id_seat,id_projection,reserved) values('240.00',4,1,false);
insert into ticket(price,id_seat,id_projection,reserved) values('240.00',5,1,false);
insert into ticket(price,id_seat,id_projection,reserved) values('240.00',6,1,false);
insert into ticket(price,id_seat,id_projection,reserved) values('220.00',7,1,false);
insert into ticket(price,id_seat,id_projection,reserved) values('240.00',8,1,false);
insert into ticket(price,id_seat,id_projection,reserved) values('220.00',9,1,false);
insert into ticket(price,id_seat,id_projection,reserved) values('240.00',10,1,false);
insert into ticket(price,id_seat,id_projection,reserved) values('220.00',11,1,false);
insert into ticket(price,id_seat,id_projection,reserved) values('240.00',12,1,false);
insert into ticket(price,id_seat,id_projection,reserved) values('240.00',13,1,false);
insert into ticket(price,id_seat,id_projection,reserved) values('240.00',14,1,false);
insert into ticket(price,id_seat,id_projection,reserved) values('240.00',15,1,false);

insert into theme_props (name, description, amount, price, image_url, show_id) values ('igracka', 'opis', 20, 50, 'url', 1);
insert into theme_props (name, description, amount, price, image_url, show_id) values ('igracka', 'opis', 0, 50, 'url', 1);

insert into user_props (name, description, end_date, state, creator_id, image_url) values ('igracka', 'opis igracke', '2018-02-28', 'DENIED', 1, null);
insert into user_props (name, description, end_date, state, creator_id, image_url) values ('igracka2', 'opis igracke2', '2018-02-28', 'APPROVED', 1, null);

insert into bid (bid_state, price, bidder_id, user_props_id) values ('DEFAULT', 245, 1, 1)