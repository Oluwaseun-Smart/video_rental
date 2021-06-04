DROP TABLE IF EXISTS price;

CREATE TABLE price (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  video_type VARCHAR(250) NOT NULL,
  rate INT(250) NOT NULL
);

INSERT INTO price (video_type, rate) VALUES ('Regular', 10),('Children’s Movie', 10),('New Release', 10);
-- INSERT INTO price (video_type, rate) VALUES ('Children’s Movie', 10);
-- INSERT INTO price (video_type, rate) VALUES ('New Release', 10);