package com.ism.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
@ToString()
public class Article {
    private int id;
    private String nom;
    private double prix;
    private int quantiteStock;
    private int quantiteDemandee;
}
