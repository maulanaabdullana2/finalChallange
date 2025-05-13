ALTER TABLE oe.products
ALTER COLUMN discontinued TYPE BOOLEAN
USING discontinued::BOOLEAN;
