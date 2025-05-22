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
@Table (name = "users",schema = "person")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    public Long userId;
    @Column(name = "user_name")
    public String userName;
    @Column(name = "user_email")
    public String userEmail;
    @Column(name = "user_password")
    public String userPassword;
    @Column(name = "user_handphone")
    public Long userPhone;
    @Column(name = "location_id")
    public Long locationId;

}
