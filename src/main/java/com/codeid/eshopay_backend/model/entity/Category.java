package com.codeid.eshopay_backend.model.entity;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Data
@NoArgsConstructor 
@AllArgsConstructor 
@RequiredArgsConstructor
@Entity
@Table(name = "categories", schema = "oe")
public class Category extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Nonnull
    @Column(name = "category_name")
    private String categoryName;

    @Nonnull
    @Column(name = "description")
    private String categoryDescription;
    
}
