CREATE TABLE oe.categories (
    category_id SERIAL PRIMARY KEY,
    category_name varchar(15) NOT NULL,
    description text,
    picture bytea,
    created_date  timestamp DEFAULT current_timestamp,
    modified_date timestamp
);

CREATE TABLE oe.shippers (
    shipper_id SERIAL PRIMARY KEY,
    company_name varchar(40) NOT NULL,
    phone varchar(24),
	created_date  timestamp default current_timestamp,
    modified_date timestamp
);


CREATE TABLE oe.suppliers (
    supplier_id SERIAL PRIMARY KEY,
    company_name varchar(40) NOT NULL,
	created_date  timestamp default current_timestamp,
    modified_date timestamp
);