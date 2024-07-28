CREATE DATABASE database;

/* Images table */
CREATE TABLE images (
id INTEGER PRIMARY KEY AUTOINCREMENT, /* primary key */
nameImg TEXT,
authorImg TEXT,
path TEXT,
datetime TEXT
);
/* Comments table */
CREATE TABLE comments (
id INTEGER PRIMARY KEY AUTOINCREMENT, /* primary key */
idimg INTEGER, /* foreign key */
user TEXT,
comment TEXT,
datetime TEXT
);
/* Votes table */
CREATE TABLE votes (
id INTEGER PRIMARY KEY AUTOINCREMENT, /* primary key */
idimg INTEGER, /* foreign key */
ups INTEGER,
downs INTEGER
);