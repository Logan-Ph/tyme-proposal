-- Create database
CREATE DATABASE IF NOT EXISTS tour_booking_db;
USE tour_booking_db;

-- 1. Create the customers table
CREATE TABLE customers (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    membership_tier VARCHAR(50) DEFAULT 'Normal',
    CONSTRAINT unique_email UNIQUE KEY (email)
) ENGINE=InnoDB;

-- 2. Create the tours table
CREATE TABLE tours (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(19,2) NOT NULL,
    availability INT NOT NULL DEFAULT 0,
    category VARCHAR(50) NOT NULL,
    CONSTRAINT chk_availability CHECK (availability >= 0)
) ENGINE=InnoDB;

-- 3. Create the vouchers table
CREATE TABLE vouchers (
    id VARCHAR(255) PRIMARY KEY,
    code VARCHAR(50) NOT NULL,
    discount_type VARCHAR(50) NOT NULL,
    discount_value DECIMAL(19,2) NOT NULL,
    expiration_date DATE NOT NULL,
    is_used BOOLEAN DEFAULT FALSE,
    CONSTRAINT unique_code UNIQUE KEY (code)
) ENGINE=InnoDB;

-- 4. Create the bookings table without payment_id initially
CREATE TABLE bookings (
    id VARCHAR(255) PRIMARY KEY,
    customer_id VARCHAR(255) NOT NULL,
    tour_id VARCHAR(255) NOT NULL,
    booking_date DATETIME NOT NULL,
    status VARCHAR(50) NOT NULL,
    voucher_id VARCHAR(255) DEFAULT NULL,
    CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_tour FOREIGN KEY (tour_id) REFERENCES tours(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_voucher FOREIGN KEY (voucher_id) REFERENCES vouchers(id) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB;

-- 5. Create the payments table
CREATE TABLE payments (
    id VARCHAR(255) PRIMARY KEY,
    booking_id VARCHAR(255) NOT NULL,
    amount DECIMAL(19,2) NOT NULL,
    payment_date DATETIME NOT NULL,
    status VARCHAR(50) NOT NULL,
    CONSTRAINT fk_booking FOREIGN KEY (booking_id) REFERENCES bookings(id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

-- 6. Add payment_id to bookings with foreign key
ALTER TABLE bookings
ADD COLUMN payment_id VARCHAR(255) DEFAULT NULL,
ADD CONSTRAINT fk_payment FOREIGN KEY (payment_id) REFERENCES payments(id) ON DELETE SET NULL ON UPDATE CASCADE;


-- Add indexes for performance optimization
-- For customers
CREATE INDEX idx_membership_tier ON customers(membership_tier);

-- For tours
CREATE INDEX idx_category ON tours(category);
CREATE INDEX idx_availability ON tours(availability);
CREATE INDEX idx_price ON tours(price);

-- For vouchers
CREATE INDEX idx_expiration_date ON vouchers(expiration_date);

-- For bookings
CREATE INDEX idx_customer_id ON bookings(customer_id);
CREATE INDEX idx_tour_id ON bookings(tour_id);
CREATE INDEX idx_booking_date ON bookings(booking_date);
CREATE INDEX idx_status ON bookings(status);
CREATE INDEX idx_payment_id ON bookings(payment_id);

-- For payments
CREATE INDEX idx_booking_id ON payments(booking_id);
CREATE INDEX idx_payment_date ON payments(payment_date);
CREATE INDEX idx_payment_status ON payments(status);

-- Add constraints for data integrity
ALTER TABLE bookings
ADD CONSTRAINT chk_booking_status CHECK (status IN ('PENDING', 'CONFIRMED', 'CANCELED'));

ALTER TABLE payments
ADD CONSTRAINT chk_payment_status CHECK (status IN ('PENDING', 'SUCCESS', 'FAILED'));

ALTER TABLE tours
ADD CONSTRAINT chk_category CHECK (category IN ('THEME_PARK', 'ADVENTURE', 'BEACH', 'MOUNTAIN', 'WILDLIFE', 'HISTORICAL', 'CITY_TOUR', 'ECO_TOURISM', 'NIGHTLIFE', 'PHOTOGRAPHY'));

ALTER TABLE vouchers
ADD CONSTRAINT chk_discount_type CHECK (discount_type IN ('FIXED', 'PERCENTAGE'));
