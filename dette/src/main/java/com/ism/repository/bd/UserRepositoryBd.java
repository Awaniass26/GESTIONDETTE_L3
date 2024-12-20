package com.ism.repository.bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ism.core.bd.DatabaseImpl;
import com.ism.entity.Role;
import com.ism.entity.User;
import com.ism.repository.UserRepository;

public class UserRepositoryBd extends RepositoryBdImpl<User> implements UserRepository {

    public UserRepositoryBd() {
        super(User.class);
    }

    @Override
    public boolean loginExist(String login) {
        String query = "SELECT COUNT(*) FROM user WHERE login = ?";
        int count = 0;
        return count > 0;
    }

    @Override
    public User seConnecter(String login, String password) {
        String query = "SELECT * FROM \"user\" WHERE \"login\" = ? AND \"password\" = ?";
        User user = null;

        DatabaseImpl database = new DatabaseImpl();
        database.OpenConnection();

        try {
            database.initPreparedStatement(query);
            PreparedStatement stmt = database.statement;
            stmt.setString(1, login);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));

                String roleStr = rs.getString("role"); 
                Role role = Role.valueOf(roleStr.toUpperCase());
                user.setRole(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.CloseConnection();
        }

        return user;
    }





    public void creerCompteAvecRole(User user) {
        if (user.getRole() == null || 
            (user.getRole() != Role.BOUTIQUIER && user.getRole() != Role.ADMIN)) {
            throw new IllegalArgumentException("Le rôle de l'utilisateur doit être 'Boutiquier' ou 'Admin'.");
        }

        if (userExists(user.getLogin())) {
            System.out.println("Erreur : L'utilisateur avec le login '" + user.getLogin() + "' existe déjà.");
            return; 
        }

        String sql = "INSERT INTO \"user\" (nom, prenom, login, password, role) VALUES (?, ?, ?, ?, ?)";
        DatabaseImpl database = new DatabaseImpl();
        database.OpenConnection();

        try {
            database.initPreparedStatement(sql);
            database.statement.setString(1, user.getNom());
            database.statement.setString(2, user.getPrenom());
            database.statement.setString(3, user.getLogin());
            database.statement.setString(4, user.getPassword());
            database.statement.setString(5, user.getRole().toString());
            int rows = database.statement.executeUpdate();

            if (rows > 0) {
                System.out.println("Compte utilisateur avec le rôle " + user.getRole() + " créé avec succès !");
            } else {
                System.out.println("Échec de la création du compte utilisateur.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.CloseConnection();
        }
    }



    private boolean userExists(String login) {
        String sql = "SELECT COUNT(*) FROM \"user\" WHERE login = ?";
        DatabaseImpl database = new DatabaseImpl();
        database.OpenConnection();
    
        try {
            database.initPreparedStatement(sql);
            database.statement.setString(1, login);
            ResultSet rs = database.statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;  // Retourne true si un utilisateur avec ce login existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.CloseConnection();
        }
        return false;
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
    

    

}
