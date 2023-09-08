CREATE TABLE IF NOT EXISTS customer
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    first_name VARCHAR(255)          NULL,
    last_name  VARCHAR(255)          NULL,
    birthdate  date                  NULL,
    CONSTRAINT pk_customer PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS vehicle
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    brand      VARCHAR(255)          NOT NULL,
    model      VARCHAR(255)          NOT NULL,
    model_year INT                   NOT NULL,
    vin        VARCHAR(255)          NULL,
    price      DECIMAL(10, 2)        NOT NULL,
    CONSTRAINT pk_vehicle PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS leasing_contract
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    contract_number VARCHAR(255)          NOT NULL,
    monthly_rate    DECIMAL               NOT NULL,
    customer_id     BIGINT                NOT NULL,
    vehicle_id      BIGINT                NOT NULL,
    CONSTRAINT pk_leasingcontract PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS leasing_contract
    ADD CONSTRAINT uc_leasingcontract_contract_number UNIQUE (contract_number);

ALTER TABLE IF EXISTS leasing_contract
    ADD CONSTRAINT FK_LEASINGCONTRACT_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer (id);

ALTER TABLE IF EXISTS leasing_contract
    ADD CONSTRAINT FK_LEASINGCONTRACT_ON_VEHICLE FOREIGN KEY (vehicle_id) REFERENCES vehicle (id);

ALTER TABLE IF EXISTS vehicle
    ADD CONSTRAINT check_model_year CHECK (model_year >= 1900);

INSERT INTO vehicle (brand, model, model_year, price, vin)
VALUES ('Toyota', 'Camry', 2022, 25000.00, 'ABC123'),
       ('Honda', 'Civic', 2021, 22000.00, 'XYZ789'),
       ('Ford', 'F-150', 2022, 35000.00, 'DEF456'),
       ('Chevrolet', 'Malibu', 2021, 24000.00, 'GHI789'),
       ('Volkswagen', 'Jetta', 2023, 27000.00, 'JKL012'),
       ('Nissan', 'Altima', 2022, 26000.00, 'MNO345'),
       ('Hyundai', 'Elantra', 2021, 23000.00, 'PQR678'),
       ('Kia', 'Optima', 2023, 28000.00, 'STU901'),
       ('Mazda', 'CX-5', 2022, 32000.00, 'VWX234'),
       ('Subaru', 'Outback', 2021, 29000.00, 'YZA567'),
       ('Mercedes-Benz', 'C-Class', 2023, 45000.00, 'BCD890'),
       ('BMW', '3 Series', 2022, 42000.00, 'EFG123'),
       ('Audi', 'A4', 2021, 41000.00, 'HIJ456'),
       ('Lexus', 'RX 350', 2022, 48000.00, 'KLM789'),
       ('Tesla', 'Model 3', 2023, 55000.00, 'NOP012');

INSERT INTO customer (first_name, last_name, birthdate)
VALUES ('John', 'Doe', '1990-01-15'),
       ('Jane', 'Smith', '1985-05-20'),
       ('Alice', 'Johnson', '1992-09-10'),
       ('Bob', 'Brown', '1980-03-25'),
       ('Eve', 'Wilson', '1998-11-05'),
       ('Michael', 'Clark', '1995-07-30'),
       ('Emily', 'Davis', '1987-04-12'),
       ('Daniel', 'Taylor', '1993-12-08'),
       ('Olivia', 'White', '1997-02-18'),
       ('Sophia', 'Martin', '1982-06-22');

INSERT INTO leasing_contract (contract_number, monthly_rate, customer_id, vehicle_id)
VALUES ('202309071530450123456789', 1500.00, 1, 1),
       ('202309071530450123456790', 1400.00, 2, 2),
       ('202309071530450123456791', 1600.00, 3, 3),
       ('202309071530450123456792', 1550.00, 4, 4),
       ('202309071530450123456793', 1450.00, 5, 5),
       ('202309071530450123456794', 1650.00, 6, 6),
       ('202309071530450123456795', 1600.00, 7, 7),
       ('202309071530450123456796', 1520.00, 8, 8),
       ('202309071530450123456797', 1620.00, 9, 9),
       ('202309071530450123456798', 1575.00, 10, 10);
