DROP TABLE IF EXISTS cars;

CREATE TABLE cars(
    id INT AUTO_INCREMENT PRIMARY KEY,
    mark VARCHAR(50),
    model VARCHAR(50),
    color VARCHAR(50),
    year_of_production VARCHAR(9));
