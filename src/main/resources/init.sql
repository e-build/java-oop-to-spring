DROP TABLE IF EXISTS USERS;

CREATE TABLE USERS (
    id varchar(12) NOT NULL,
    username varchar(50) NOT NULL,
    password varchar(50) NOT NULL,
    nickname varchar(50) NOT NULL,
    PRIMARY KEY (userId);
);

INSERT INTO USERS VALUES('1', 'e-build@gmail.com', '1234qwer', 'e-build');