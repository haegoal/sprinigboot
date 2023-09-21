package com.example.demo.entity;

import javax.persistence.*;

@Entity
public class DemoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // autoincrement
    private Long id;

    @Column
    private String name;


}
