package com.codecraft.Crud_app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String asigneedTo;
    private String title;
    private String description;
    private int status;
}
