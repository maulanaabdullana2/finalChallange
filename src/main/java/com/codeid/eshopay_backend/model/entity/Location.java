package com.codeid.eshopay_backend.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor 
@AllArgsConstructor
@Entity
@Builder
@Table(name="locations",schema = "hr")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="location_id")
    private Long locationId;
    @Column(name="street_address")
    private String streetAddress;
    @Column(name="postal_code")
    private String postalCode;
    @Column(name="city")
    private String city;
    @Column (name="state_province")
    private String stateProvince;
    @Column (name="country_id")
    private String countryId;
}
