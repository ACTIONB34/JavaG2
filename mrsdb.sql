CREATE DATABASE mrsdb;
USE mrsdb;

CREATE TABLE cinemas(
	cinema_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    cinema_num  INT NOT NULL,
    capacity INT DEFAULT 40 NOT NULL
);
ALTER TABLE cinemas AUTO_INCREMENT = 1201;

CREATE TABLE movies(
	movie_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    title NVARCHAR(128) NOT NULL,	
    description VARCHAR(512) NOT NULL,
    rating VARCHAR(16) NOT NULL,
    length INT NOT NULL
);
ALTER TABLE movies AUTO_INCREMENT = 34001;

CREATE TABLE schedules(
	sched_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    sched_date DATETIME NOT NULL,
    movie_id INT NOT NULL,
    cinema_id INT NOT NULL,    
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id),
    FOREIGN KEY (cinema_id) REFERENCES cinemas(cinema_id)
);
ALTER TABLE schedules AUTO_INCREMENT = 56001;

CREATE TABLE reservations(
	reserv_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    reserv_date TIMESTAMP NOT NULL,
    seat VARCHAR(4) NOT NULL,
    guest_name NVARCHAR(128) NOT NULL,
    guest_type INT NOT NULL,
    sched_id INT NOT NULL,
    FOREIGN KEY (sched_id) REFERENCES schedules(sched_id)
);
ALTER TABLE reservations AUTO_INCREMENT = 780001;

INSERT INTO cinemas(cinema_num) VALUES (1), (2), (3), (4);

INSERT INTO movies(title, description, rating, length) VALUES ("Harry Potter and the Deathly Hallows - Part 1", "As Harry, Ron, and Hermione race against time and evil to destroy the Horcruxes, they uncover the existence of the three most powerful objects in the wizarding world: the Deathly Hallows.", "PG-13", 146),
("On Vodka, Beer and Regrets", "(to be added)", "PG-13", 94),
("La Famille Belier", "A girl, who lives with her deaf parents, discovers that she has the gift of singing.", "PG-13", 106),
("Avengers: Endgame", "After the devastating events of Avengers: Infinity War (2018), the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos' actions and restore balance to the universe.", "PG-13", 181),
("Harry Potter and the Deathly Hallows - Part 2", "Harry, Ron, and Hermione search for Voldemort's remaining Horcruxes in their effort to destroy the Dark Lord as the final battle rages on at Hogwarts.", "PG-13", 130),
("Argo", "Acting under the cover of a Hollywood producer scouting a location for a science fiction film, a CIA agent launches a dangerous operation to rescue six Americans in Tehran during the U.S. hostage crisis in Iran in 1979.", "R", 130),
("100 Tula Para Kay Stella", "Throughout his four years in college, Fidel, a stuttering student, tries to finish 100 poems dedicated to Stella, an aspiring but frustrated rock star, to win her heart.", "PG-13", 123);


INSERT INTO schedules(sched_date, movie_id, cinema_id) VALUES 
("2020-04-01 12:00:00", 34001, 1201),
("2020-04-01 14:45:00", 34001, 1201),
("2020-04-01 17:30:00", 34001, 1201),
("2020-04-01 12:00:00", 34002, 1202),
("2020-04-01 13:50:00", 34002, 1202),
("2020-04-01 15:40:00", 34002, 1202),
("2020-04-01 17:30:00", 34002, 1202),
("2020-04-01 12:00:00", 34003, 1203),
("2020-04-01 14:05:00", 34003, 1203),
("2020-04-01 16:10:00", 34003, 1203),
("2020-04-01 12:00:00", 34004, 1204),
("2020-04-01 15:20:00", 34004, 1204),
("2020-04-02 12:00:00", 34001, 1201),
("2020-04-02 14:45:00", 34001, 1201),
("2020-04-02 17:30:00", 34001, 1201),
("2020-04-02 12:00:00", 34002, 1202),
("2020-04-02 13:50:00", 34002, 1202),
("2020-04-02 15:40:00", 34002, 1202),
("2020-04-02 17:30:00", 34002, 1202),
("2020-04-02 12:00:00", 34003, 1203),
("2020-04-02 14:05:00", 34003, 1203),
("2020-04-02 16:10:00", 34003, 1203),
("2020-04-02 12:00:00", 34004, 1204),
("2020-04-02 15:20:00", 34004, 1204),
("2020-04-03 12:00:00", 34001, 1201),
("2020-04-03 14:45:00", 34001, 1201),
("2020-04-03 17:30:00", 34001, 1201),
("2020-04-03 12:00:00", 34002, 1202),
("2020-04-03 13:50:00", 34002, 1202),
("2020-04-03 15:40:00", 34002, 1202),
("2020-04-03 17:30:00", 34002, 1202),
("2020-04-03 12:00:00", 34003, 1203),
("2020-04-03 14:05:00", 34003, 1203),
("2020-04-03 16:10:00", 34003, 1203),
("2020-04-03 12:00:00", 34004, 1204),
("2020-04-03 15:20:00", 34004, 1204),
("2020-04-04 12:00:00", 34005, 1201),
("2020-04-04 14:25:00", 34005, 1201),
("2020-04-04 16:50:00", 34005, 1201),
("2020-04-04 12:30:00", 34002, 1202),
("2020-04-04 14:20:00", 34002, 1202),
("2020-04-04 16:10:00", 34002, 1202),
("2020-04-04 17:50:00", 34002, 1202),
("2020-04-04 12:30:00", 34006, 1203),
("2020-04-04 14:55:00", 34006, 1203),
("2020-04-04 05:20:00", 34006, 1203),
("2020-04-04 13:00:00", 34004, 1204),
("2020-04-04 16:35:00", 34004, 1204),
("2020-04-05 12:00:00", 34005, 1201),
("2020-04-05 14:25:00", 34005, 1201),
("2020-04-05 16:50:00", 34005, 1201),
("2020-04-05 12:30:00", 34002, 1202),
("2020-04-05 14:20:00", 34002, 1202),
("2020-04-05 16:10:00", 34002, 1202),
("2020-04-05 17:50:00", 34002, 1202),
("2020-04-05 12:30:00", 34006, 1203),
("2020-04-05 14:55:00", 34006, 1203),
("2020-04-05 17:20:00", 34006, 1203),
("2020-04-05 13:00:00", 34004, 1204),
("2020-04-05 16:35:00", 34004, 1204),
("2020-04-06 12:00:00", 34007, 1202),
("2020-04-06 14:15:00", 34007, 1202),
("2020-04-06 16:30:00", 34007, 1202),
("2020-04-06 12:00:00", 34006, 1203),
("2020-04-06 14:25:00", 34006, 1203),
("2020-04-06 16:50:00", 34006, 1203),
("2020-04-07 12:00:00", 34007, 1202),
("2020-04-07 14:15:00", 34007, 1202),
("2020-04-07 16:30:00", 34007, 1202),
("2020-04-07 12:00:00", 34006, 1203),
("2020-04-07 14:25:00", 34006, 1203),
("2020-04-07 16:50:00", 34006, 1203);