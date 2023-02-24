package com.github.receipes;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "receipes")
public class ReceipeEntity {

    @Id
    private String name;
    @Column
    private Boolean isVegetarian;
    private String ingredients;
    private String instructions;
    private long serves;
}
