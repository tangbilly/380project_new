CREATE TABLE biddingusers (
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    PRIMARY KEY (username)
);

CREATE TABLE user_roles (
    user_role_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    username VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_role_id),
    FOREIGN KEY (username) REFERENCES biddingusers(username)
);


INSERT INTO biddingusers VALUES ('edthin', 'edthinpw');
INSERT INTO user_roles(username, role) VALUES ('edthin', 'ROLE_USER');
INSERT INTO user_roles(username, role) VALUES ('edthin', 'ROLE_ADMIN');
