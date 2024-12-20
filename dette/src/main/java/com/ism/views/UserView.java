package com.ism.views;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.ism.core.bd.DatabaseImpl;
import com.ism.entity.Role;
import com.ism.entity.User;

public class UserView {

    private Scanner scanner;

    public UserView(Scanner scanner) {
        this.scanner = scanner;
    }

    public User saisieUser() {
        User user = new User();

        System.out.println("Entrer le login");
        user.setLogin(scanner.nextLine());

        System.out.println("Entrer le password");
        user.setPassword(scanner.nextLine());

        System.out.println("Entrer le Nom");
        user.setNom(scanner.nextLine());

        System.out.println("Entrer le Prenom");
        user.setPrenom(scanner.nextLine());

        System.out.println("Choisir le rôle :");
        System.out.println("1 - Boutiquier");
        System.out.println("2 - Admin");
        int roleChoice = scanner.nextInt();
        scanner.nextLine();

        switch (roleChoice) {
            case 1:
                user.setRole(Role.BOUTIQUIER);
                break;
            case 2:
                user.setRole(Role.ADMIN);
                break;
            default:
                System.out.println("Rôle invalide. Attribution du rôle 'Client' par défaut.");
                user.setRole(Role.CLIENT);
        }

        return user;
    }

    public void afficherMenuGestionCompte() {
        System.out.println("Choisir une action :");
        System.out.println("1 - Désactiver un compte utilisateur");
        System.out.println("2 - Activer un compte utilisateur");
        System.out.print("Entrez votre choix : ");
        int choix = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Entrez le login de l'utilisateur : ");
        String login = scanner.nextLine();

        if (choix == 1) {
            desactiverCompte(login);
        } else if (choix == 2) {
            activerCompte(login);
        } else {
            System.out.println("Choix invalide. Veuillez entrer 1 pour désactiver ou 2 pour activer.");
        }
    }

    public void desactiverCompte(String login) {
    String sql = "UPDATE \"user\" SET statut = 'désactivé' WHERE login = ?";
    DatabaseImpl database = new DatabaseImpl();
    database.OpenConnection();

    try {
        database.initPreparedStatement(sql);
        database.statement.setString(1, login);
        int rows = database.statement.executeUpdate();

        if (rows > 0) {
            System.out.println("Le compte avec le login '" + login + "' a été désactivé avec succès.");
        } else {
            System.out.println("Aucun utilisateur trouvé avec ce login.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        database.CloseConnection();
    }
}

public void activerCompte(String login) {
    String sql = "UPDATE \"user\" SET statut = 'actif' WHERE login = ?";
    DatabaseImpl database = new DatabaseImpl();
    database.OpenConnection();

    try {
        database.initPreparedStatement(sql);
        database.statement.setString(1, login);
        int rows = database.statement.executeUpdate();

        if (rows > 0) {
            System.out.println("Le compte avec le login '" + login + "' a été activé avec succès.");
        } else {
            System.out.println("Aucun utilisateur trouvé avec ce login.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        database.CloseConnection();
    }
}



public void afficherComptesUtilisateurs() {
    System.out.println("Choisissez une option :");
    System.out.println("1 - Afficher les comptes actifs");
    System.out.println("2 - Afficher les comptes par rôle");
    System.out.print("Entrez votre choix : ");
    int choix = scanner.nextInt();
    scanner.nextLine();  // Consommer le saut de ligne restant

    switch (choix) {
        case 1:
            afficherComptesActifs();
                        break;
                    case 2:
                        System.out.print("Entrez le rôle (ADMIN, BOUTIQUIER, CLIENT) : ");
                        String role = scanner.nextLine();
                        afficherComptesParRole(role.toUpperCase());
                        break;
                    default:
                        System.out.println("Choix invalide.");
                }
            }
            
    public void afficherComptesActifs() {
    String sql = "SELECT * FROM \"user\" WHERE statut = 'actif'";
    DatabaseImpl database = new DatabaseImpl();
    database.OpenConnection();

    try {
        database.initPreparedStatement(sql);
        ResultSet resultSet = database.statement.executeQuery();

        System.out.println("Liste des comptes actifs :");
        while (resultSet.next()) {
            System.out.println(
                "ID : " + resultSet.getInt("id") +
                ", Nom : " + resultSet.getString("nom") +
                ", Prenom : " + resultSet.getString("prenom") +
                ", Login : " + resultSet.getString("login") +
                ", Rôle : " + resultSet.getString("role")
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        database.CloseConnection();
    }
}




public void afficherComptesParRole(String role) {
    String sql = "SELECT * FROM \"user\" WHERE role = ?";
    DatabaseImpl database = new DatabaseImpl();
    database.OpenConnection();

    try {
        database.initPreparedStatement(sql);
        database.statement.setString(1, role);
        ResultSet resultSet = database.statement.executeQuery();

        System.out.println("Liste des comptes avec le rôle " + role + " :");
        while (resultSet.next()) {
            System.out.println(
                "ID : " + resultSet.getInt("id") +
                ", Nom : " + resultSet.getString("nom") +
                ", Prenom : " + resultSet.getString("prenom") +
                ", Login : " + resultSet.getString("login") +
                ", Statut : " + resultSet.getString("statut")
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        database.CloseConnection();
    }
}

public User saisieUserForClient() {
    User user = new User();

    System.out.println("Entrer le login");
    user.setLogin(scanner.nextLine());

    System.out.println("Entrer le password");
    user.setPassword(scanner.nextLine());

    System.out.println("Entrer le Nom");
    user.setNom(scanner.nextLine());

    System.out.println("Entrer le Prenom");
    user.setPrenom(scanner.nextLine());

    user.setRole(Role.CLIENT);

    return user;
}




}
