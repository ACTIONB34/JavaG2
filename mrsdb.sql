CREATE DATABASE mrsdb;
USE mrsdb;

CREATE TABLE cinemas(
	cinema_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    capacity INT NOT NULL
);

CREATE TABLE movies(
	movie_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    title NVARCHAR(128) NOT NULL,
    description VARCHAR(512) NOT NULL,
    rating VARCHAR(16) NOT NULL,
    length INT NOT NULL
);

CREATE TABLE schedules(
	sched_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    sched_date DATETIME NOT NULL,
    movie_id INT NOT NULL,
    cinema_id INT NOT NULL,    
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id),
    FOREIGN KEY (cinema_id) REFERENCES cinemas(cinema_id)
);


CREATE TABLE reservations(
	reserv_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    reserv_date TIMESTAMP NOT NULL,
    seat VARCHAR(4) NOT NULL,
    guest_name NVARCHAR(128) NOT NULL,
    guest_type INT NOT NULL,
    sched_id INT NOT NULL,
    FOREIGN KEY (sched_id) REFERENCES schedules(sched_id)
);