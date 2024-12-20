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
@ToString(of = { "id", "login", "password", "nom", "prenom" })
@EqualsAndHashCode(of = { "login" })
public class User {
    private int id;
    private String login;
    private String password;
    private String nom;
    private String prenom;
    private String statut; 
    
    private Client client;
    private Role role;
    


}

