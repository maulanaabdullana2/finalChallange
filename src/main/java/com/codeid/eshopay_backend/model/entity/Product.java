package com.codeid.eshopay_backend.model.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor 
@AllArgsConstructor
@Entity
@Table(name = "products", schema = "oe")
public class Product extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    public Long productId;

    @Nonnull
    @Column(name = "product_name")
    public String productName;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
    
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    
    @Nonnull
    @Column(name = "quantity_per_unit")
    public String quantityPerUnit;

    @Column(name = "unit_price")
    public Double unitPrice;

    @Column(name = "units_in_stock")
    public Long unitsInStock;

    @Column (name = "units_on_order")
    public Long unitsOnOrder;

    @Column (name = "reorder_level")
    public Long reorderLevel;

    @Column (name = "discontinued")
    public Boolean discontinued;

}


