CREATE TABLE User (
    ID BIGINT NOT NULL AUTO_INCREMENT,
    Name VARCHAR(255) NOT NULL,
    Surname VARCHAR(255),
    PersonID VARCHAR(255) NOT NULL UNIQUE,
    Uuid VARCHAR(255) NOT NULL UNIQUE,
    PRIMARY KEY (ID)
);

SELECT ID, Name, Surname, PersonID, Uuid
FROM users.`user`;