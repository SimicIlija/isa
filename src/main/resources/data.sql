insert into user_table (first_name, last_name, email, phone_number, password, role) values ('Jelena','Stanarevic','js.lenchi@gmail.com', '596262652', 'jelena','REGISTERED');
insert into user_table (first_name, last_name, email, phone_number, password, role) values ('Ilija','Simic','sima@gmail.com', '5558896', 'ilija','ADMIN_SYS');
insert into user_table (first_name, last_name, email, phone_number, password, role) values ('Marko','Markovic','marko@gmail.com', '5558896', 'marko','ADMIN_SYS');
insert into user_table (first_name, last_name, email, phone_number, password, role) values ('Smiljana','Dragoljevic','smiljana@gmail.com', '797679', 'smiljana','ADMIN_INST');

insert into institution_admin (id, password_changed) VALUES (4, true);
insert into system_admin (id) VALUES (3);

insert into institution (description, name, longitude, latitude, is_cinema) values ('opis...', 'ArenaCineplex', 19.853547, 45.254313, true);
insert into institution (description, name, longitude, latitude, is_cinema) values ('opis...', 'Cinestar', 19.826031, 45.276086, true);
insert into institution (description, name, longitude, latitude, is_cinema) values ('opissss...', 'SNP', 19.843085, 45.255192, false);
insert into institution (description, name, longitude, latitude, is_cinema) values ('opissss...', 'Pozoriste mladih', 19.843085, 45.255192, false);


insert into admin_institution (admin_id, institution_id) values (4, 1);
insert into admin_institution (admin_id, institution_id) values (4, 2);

insert into auditorium (name, id_institution) values ('sala1', 1);
insert into auditorium (name, id_institution) values ('sala2', 1);
insert into auditorium (name, id_institution) values ('sala3', 1);

insert into auditorium (name, id_institution) values ('aud1', 2);
insert into auditorium (name, id_institution) values ('aud2', 2);

insert into auditorium (name, id_institution) values ('vip', 3);

insert into auditorium (name, id_institution) values ('aud', 4);

insert into segment (row_count,seats_in_row_count,closed, label, id_auditorium) values (2, 3, 0, '1A', 1);
insert into segment (row_count,seats_in_row_count,closed, label, id_auditorium) values (3, 3, 0, '1B', 1);

insert into segment (row_count,seats_in_row_count,closed, label, id_auditorium) values (1, 2, 0, 'a', 4);
insert into segment (row_count,seats_in_row_count,closed, label, id_auditorium) values (1, 1, 0, 'b', 4);

insert into seat (row, seat_number, id_segment) values (1, 1, 1);
insert into seat (row, seat_number, id_segment) values (1, 2, 1);
insert into seat (row, seat_number, id_segment) values (1, 4, 1);
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

insert into seat (row, seat_number, id_segment) values (1, 1, 3);
insert into seat (row, seat_number, id_segment) values (1, 2, 3);
insert into seat (row, seat_number, id_segment) values (1, 1, 4);


insert into show(name,genre,producer,duration, poster_file_name, id_institution, description) values ('Red sparrow','thriller', 'Francis Lorens', 141, 'https://goo.gl/images/DcM4U2', 1, 'frfrgvregvrwv');
insert into show(name,genre,producer,duration, poster_file_name, id_institution, description) values ('LED','drama', 'Oleg Trofim', 113, 'https://goo.gl/images/8MFZ9e', 1, 'rgergergergervvb');
insert into show(name,genre,producer,duration, id_institution, description) values ('Rampage 3D','action', 'Brad Peyton', 107, 1, 'vruihveuie');
insert into show(name,genre,producer,duration, id_institution, description) values ('Gnome Alone 3D','comedy', 'Peter Lepeniotis', 89, 1, 'fjwruhfirhrue');

insert into show(name,genre,producer,duration, id_institution, description) values ('Show1','action', 'Producer1', 107, 2, 'vruihveuie');
insert into show(name,genre,producer,duration, id_institution, description) values ('show2','comedy', 'Producer2', 89, 2, 'fjwruhfirhrue');

insert into actor(name, lastname, id_show) values ('imeGlumca1', 'prezimeGlumca1', 1);
insert into actor(name, lastname, id_show) values ('imeGlumca2', 'prezimeGlumca2', 1);
insert into actor(name, lastname, id_show) values ('imeGlumca3', 'prezimeGlumca3', 1);
insert into actor(name, lastname, id_show) values ('imeGlumca4', 'prezimeGlumca4', 1);

insert into projection(id_show,date,id_auditorium) values (1,'2018-07-28 21:00:00',1);
insert into projection(id_show,date,id_auditorium) values (1,'2018-07-28 20:00:00',2);
insert into projection(id_show,date,id_auditorium) values (2,'2018-04-18 10:30:00',3);
insert into projection(id_show,date,id_auditorium) values (2,'2018-06-28 21:00:00',1);
insert into projection(id_show,date,id_auditorium) values (3,'2018-06-28 22:00:00',1);
insert into projection(id_show,date,id_auditorium) values (4,'2018-06-28 23:00:00',1);

insert into projection(id_show,date,id_auditorium) values (5,'2018-06-28 22:00:00',4);
insert into projection(id_show,date,id_auditorium) values (5,'2018-06-28 23:00:00',4);

insert into ticket(price,id_seat,id_projection,reserved) values('220.00',1,1,true);
insert into ticket(price,id_seat,id_projection,reserved) values('240.00',2,1,false);
insert into ticket(price,id_seat,id_projection,reserved) values('220.00',3,1,false);
insert into ticket(price,id_seat,id_projection,reserved) values('220.00',4,1,false);
insert into ticket(price,id_seat,id_projection,reserved) values('220.00',5,1,false);
insert into ticket(price,id_seat,id_projection,reserved) values('220.00',6,1,false);
insert into ticket(price,id_seat,id_projection,reserved) values('200.00',7,1,false);
insert into ticket(price,id_seat,id_projection,reserved) values('200.00',8,1,false);
insert into ticket(price,id_seat,id_projection,reserved) values('200.00',9,1,false);
insert into ticket(price,id_seat,id_projection,reserved) values('200.00',10,1,false);
insert into ticket(price,id_seat,id_projection,reserved) values('200.00',11,1,false);
insert into ticket(price,id_seat,id_projection,reserved) values('200.00',12,1,false);
insert into ticket(price,id_seat,id_projection,reserved) values('200.00',13,1,false);
insert into ticket(price,id_seat,id_projection,reserved) values('200.00',14,1,false);
insert into ticket(price,id_seat,id_projection,reserved) values('200.00',15,1,false);
insert into ticket(price,id_seat,id_projection,reserved) values('200.00',16,7,false);
insert into ticket(price,id_seat,id_projection,reserved) values('200.00',17,7,false);
insert into ticket(price,id_seat,id_projection,reserved) values('200.00',18,7,false);

insert into theme_props (name, description, amount, price, image_url, show_id) values ('igracka', 'opis', 20, 50, 'url', 1);
insert into theme_props (name, description, amount, price, image_url, show_id) values ('igracka', 'opis', 0, 50, 'url', 1);

insert into user_props (name, description, end_date, state, creator_id, image_url) values ('igracka', 'opis igracke', '2018-02-28', 'DENIED', 1, null);
insert into user_props (name, description, end_date, state, creator_id, image_url) values ('igracka2', 'opis igracke2', '2018-02-28', 'APPROVED', 1, null);


insert into bid (bid_state, price, bidder_id, user_props_id) values ('DEFAULT', 245, 1, 1);

insert into reservation (date, id_projection, id_reserver) values ('2018-01-28 21:00:00', 1, 4);
insert into reservation (date, id_projection, id_reserver) values ('2018-05-25 21:00:00', 1, 4);
insert into reservation (date, id_projection, id_reserver) values ('2018-01-22 21:00:00', 1, 4);
insert into reservation (date, id_projection, id_reserver) values ('2018-02-21 21:00:00', 1, 1);

insert into reservation (date, id_projection, id_reserver) values ('2018-01-22 21:00:00', 7, 4);
insert into reservation (date, id_projection, id_reserver) values ('2018-02-21 21:00:00', 7, 4);

update ticket set id_reservation=1 where id = 1;
update ticket set id_reservation=2 where id = 2;
update ticket set id_reservation=3 where id = 3;
update ticket set id_reservation=4 where id = 4;

update ticket set id_reservation=5 where id = 5;
update ticket set id_reservation=6 where id = 6;

insert into projection_rating (id_projection, id_user, projection_rating, institution_rating) values (1, 4, 5, 2);
insert into projection_rating (id_projection, id_user, projection_rating, institution_rating) values (3, 4, 5, 3);
insert into projection_rating (id_projection, id_user, projection_rating, institution_rating) values (2, 4, 5, 1);
insert into projection_rating (id_projection, id_user, projection_rating, institution_rating) values (2, 1, 5, 4);

insert into projection_rating (id_projection, id_user, projection_rating, institution_rating) values (7, 4, 5, 1);
insert into projection_rating (id_projection, id_user, projection_rating, institution_rating) values (7, 1, 5, 4);

insert into bid (bid_state, price, bidder_id, user_props_id) values ('DEFAULT', 245, 1, 2);

insert into on_sale_ticket (ticket_id, discount) VALUES (1, 20);
insert into on_sale_ticket (ticket_id, discount) VALUES (2, 30);
