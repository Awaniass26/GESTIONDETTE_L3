package com.ism.repository.bd;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ism.core.bd.DatabaseImpl;
import com.ism.entity.Client;
import com.ism.entity.User;
import com.ism.repository.ClientRepository;

public class ClientRepositoryBd extends RepositoryBdImpl<Client> implements ClientRepository {

    public ClientRepositoryBd() {
        super(Client.class);
    }

    @Override
    public Client readClientBySurname(String surname) {
        String sql = "SELECT * FROM client WHERE surname = ?";
        Client client = null;
        try {
            initPreparedStatement(sql);
            statement.setString(1, surname);
            ResultSet rs = this.executeSelect();
            if (rs.next()) {
                client = new Client();
                client.setId(rs.getInt("id"));
                client.setSurname(rs.getString("surname"));
                client.setTelephone(rs.getString("telephone"));
                client.setAdresse(rs.getString("adresse"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.CloseConnection();
        }
        return client;
    }

    @Override
    public Client readClientByTelephone(String telephone) {
        String sql = "SELECT * FROM client WHERE telephone = ?";
        Client client = null;
        try {
            initPreparedStatement(sql);
            statement.setString(1, telephone);
            ResultSet rs = this.executeSelect();
            if (rs.next()) {
                client = new Client();
                client.setId(rs.getInt("id"));
                client.setSurname(rs.getString("surname"));
                client.setTelephone(rs.getString("telephone"));
                client.setAdresse(rs.getString("adresse"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.CloseConnection();
        }
        return client;
    }

    @Override
    public Client findById(int id) {
        Client client = null;
        String sql = "SELECT * FROM client WHERE id = ?";
        try {
            initPreparedStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                client = new Client();
                client.setId(rs.getInt("id"));
                client.setSurname(rs.getString("surname"));
                client.setTelephone(rs.getString("telephone"));
                client.setAdresse(rs.getString("adresse"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CloseConnection();
        }
        return client;
    }


    public void createClientWithAccount(Client client, User user) {
        try {
            String userSql = "INSERT INTO \"user\" (nom, prenom, login, password, role) VALUES (?, ?, ?, ?,?)";
            initPreparedStatement(userSql);
            statement.setString(1, user.getNom());
            statement.setString(2, user.getPrenom());
            statement.setString(3, user.getLogin());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getRole().toString()); 

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                int userId = rs.getInt(1);
                user.setId(userId);

                String clientSql = "INSERT INTO client (surname, telephone, adresse, user_id) VALUES (?, ?, ?, ?)";
                initPreparedStatement(clientSql);
                statement.setString(1, client.getSurname());
                statement.setString(2, client.getTelephone());
                statement.setString(3, client.getAdresse());
                statement.setInt(4, userId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CloseConnection();
        }
    }



    public void creerComptePourClient(int clientId, User user) {
        String sqlCheckClient = "SELECT user_id FROM client WHERE id = ?";
        String sqlUpdateClient = "UPDATE client SET user_id = ? WHERE id = ?";
        
        if (user.getRole() == null) {
            throw new IllegalArgumentException("Le rôle de l'utilisateur doit être spécifié.");
        }
        
        DatabaseImpl database = new DatabaseImpl();
        database.OpenConnection();
        
        try {
            database.initPreparedStatement(sqlCheckClient);
            database.statement.setInt(1, clientId);
            ResultSet rs = database.executeSelect();
            
            if (rs.next() && rs.getInt("user_id") == 0) {
                String sqlInsertUser = "INSERT INTO \"user\" (nom, prenom, login, password, role) VALUES (?, ?, ?, ?, ?)";
                database.initPreparedStatement(sqlInsertUser);
                database.statement.setString(1, user.getNom());
                database.statement.setString(2, user.getPrenom());
                database.statement.setString(3, user.getLogin());
                database.statement.setString(4, user.getPassword());
                database.statement.setString(5, user.getRole().toString()); 
                database.statement.executeUpdate();
                
                ResultSet rsUser = database.statement.getGeneratedKeys();
                if (rsUser.next()) {
                    int userId = rsUser.getInt(1);
                    
                    database.initPreparedStatement(sqlUpdateClient);
                    database.statement.setInt(1, userId);
                    database.statement.setInt(2, clientId);
                    database.statement.executeUpdate();
                }
            } else {
                throw new IllegalArgumentException("Le client a déjà un compte utilisateur.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.CloseConnection();
        }
    }

    public Client findByUser(User userr) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByUser'");
    }


    public Client obtenirClientConnecte(int userId) {
        if (userId <= 0) {
            System.out.println("Aucun utilisateur connecté ou ID invalide.");
            return null;
        }

        Client client = null;
        String sql = "SELECT c.id, c.surname, c.telephone, c.adresse, c.cumulMontantDus, c.user_id " +
                     "FROM client c WHERE c.user_id = ?";

        try {
            initPreparedStatement(sql);
            statement.setInt(1, userId);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                client = new Client();
                client.setId(rs.getInt("id"));
                client.setSurname(rs.getString("surname"));
                client.setTelephone(rs.getString("telephone"));
                client.setAdresse(rs.getString("adresse"));
                client.setCumulMontantDus(rs.getDouble("cumulMontantDus"));

                User user = new User();
                user.setId(rs.getInt("user_id"));
                client.setUser(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CloseConnection();
        }

        if (client == null) {
            System.out.println("Aucun client trouvé pour l'utilisateur connecté.");
        }

        return client;
    }

    

    
}



