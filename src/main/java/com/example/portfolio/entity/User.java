package com.example.portfolio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.Setter;

@Entity
@Table(name = "users")
@Data
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 自動採番される主キー

    private String email;

    private String password;
    
    @Column(nullable = false)
    private String name; 
    
    @Override
    public String toString() {
    	return this.email;
    }

}
