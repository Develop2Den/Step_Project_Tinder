CREATE TABLE users(
                      id SERIAL PRIMARY KEY ,
                      name TEXT NOT NULL ,
                      photo_url TEXT NOT NULL
);

CREATE TABLE likes (
                       id SERIAL PRIMARY KEY,
                       user_id1 INT NOT NULL,
                       user_id2 INT NOT NULL,
                       timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE messages (
                          id SERIAL PRIMARY KEY,
                          sender_id INT REFERENCES users(id) NOT NULL,
                          receiver_id INT REFERENCES users(id) NOT NULL,
                          content TEXT NOT NULL,
                          timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
