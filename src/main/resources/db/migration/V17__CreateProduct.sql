CREATE TABLE oe.products (
    product_id serial PRIMARY KEY,
    product_name varchar(255) NOT NULL,
    supplier_id INTEGER NOT NULL,
    category_id INTEGER NOT NULL,
    quantity_per_unit varchar(20) NOT NULL,
    unit_price numeric(10, 2),
    units_in_stock INTEGER,
    units_on_order INTEGER,
    reorder_level INTEGER,
    discontinued BOOLEAN,
    created_date TIMESTAMP,
    modified_date TIMESTAMP,

    -- Foreign key ke tabel suppliers
    CONSTRAINT fk_supplier
        FOREIGN KEY (supplier_id)
        REFERENCES oe.suppliers(supplier_id),

    -- Foreign key ke tabel categories
    CONSTRAINT fk_category
        FOREIGN KEY (category_id)
        REFERENCES oe.categories(category_id)
);
