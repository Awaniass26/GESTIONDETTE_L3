package com.ism.repository.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ism.core.bd.DatabaseImpl;
import com.ism.entity.Article;
import com.ism.entity.Client;
import com.ism.entity.Dette;
import com.ism.entity.Paiement;
import com.ism.entity.User;
import com.ism.repository.DetteRepository;

public class DetteRepositoryBd extends RepositoryBdImpl<Dette> implements DetteRepository {

    public DetteRepositoryBd() {
        super(Dette.class);
    }

    @Override
    public Dette findById(int id) {
        String sql = "SELECT * FROM dette WHERE id = ?";
        Dette dette = null;

        try {
            initPreparedStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = this.executeSelect();

            if (rs.next()) {
                dette = new Dette();
                dette.setId(rs.getInt("id"));
                dette.setDate(rs.getDate("date"));
                dette.setMontant(rs.getDouble("montant"));
                dette.setMontantVerse(rs.getDouble("montantverse"));
                dette.setMontantRestant(rs.getDouble("montantrestant"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.CloseConnection();
        }

        return dette;
    }

   
    
@Override
public List<Dette> findUnpaidDebtsByClientId(int clientId) {
    String sql = "SELECT * FROM dette WHERE client_id = ? AND montantrestant > 0";
    List<Dette> dettes = new ArrayList<>();

    try {
        initPreparedStatement(sql);
        statement.setInt(1, clientId);
        ResultSet rs = this.executeSelect();

        while (rs.next()) {
            Dette dette = new Dette();
            dette.setId(rs.getInt("id"));
            dette.setDate(rs.getDate("date"));
            dette.setMontant(rs.getDouble("montant"));
            dette.setMontantVerse(rs.getDouble("montantverse"));
            dette.setMontantRestant(rs.getDouble("montantrestant"));
            dettes.add(dette);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        this.CloseConnection();
    }

    return dettes;
}




public List<Article> findArticlesByDetteId(int detteId) {
    String sql = "SELECT * FROM article WHERE dette_id = ?";
    List<Article> articles = new ArrayList<>();

    try {
        initPreparedStatement(sql);
        statement.setInt(1, detteId);
        ResultSet rs = this.executeSelect();

        while (rs.next()) {
            Article article = new Article();
            article.setId(rs.getInt("id"));
            article.setNom(rs.getString("nom"));
            articles.add(article);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        this.CloseConnection();
    }

    return articles;
}




public List<Paiement> findPaiementsByDetteId(int detteId) {
    String sql = "SELECT * FROM paiement WHERE dette_id = ?";
    List<Paiement> paiements = new ArrayList<>();

    try {
        initPreparedStatement(sql);
        statement.setInt(1, detteId);
        ResultSet rs = this.executeSelect();

        while (rs.next()) {
            Paiement paiement = new Paiement();
            paiement.setId(rs.getInt("id"));
            paiement.setMontant(rs.getDouble("montant"));
            paiements.add(paiement);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        this.CloseConnection();
    }

    return paiements;
}


public void savePaiement(Paiement paiement) {
    String sql = "INSERT INTO \"paiement\" (montant, date, dette_id) VALUES (?, ?, ?)";

    try {
        initPreparedStatement(sql);
        statement.setDouble(1, paiement.getMontant());
        statement.setDate(2, new java.sql.Date(paiement.getDate().getTime()));  
        statement.setInt(3, paiement.getDette().getId());
        statement.executeUpdate();
        System.out.println("Paiement enregistré avec succès !");
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        this.CloseConnection();
    }
}



@Override
public void update(Dette selectedDette) {
    String sql = "UPDATE dette SET montant = ?, montantverse = ?, montantrestant = ?, date = ? WHERE id = ?";

    try {
        initPreparedStatement(sql);
        statement.setDouble(1, selectedDette.getMontant());
        statement.setDouble(2, selectedDette.getMontantVerse());
        statement.setDouble(3, selectedDette.getMontantRestant());
        statement.setDate(4, new java.sql.Date(selectedDette.getDate().getTime())); 
        statement.setInt(5, selectedDette.getId()); 

        int rowsUpdated = statement.executeUpdate(); 

        if (rowsUpdated > 0) {
            System.out.println("Dette mis a jour avec succes!!");
        } else {
            System.out.println("Aucune dette pour ce client.");
        }
    } catch (SQLException e) {
        System.err.println("error: " + e.getMessage());
    } finally {
        this.CloseConnection();
    }
}




public void archiverDettesSoldees() {
    String sql = "UPDATE dette SET archived = TRUE WHERE montantrestant = 0 AND archived = FALSE";
    DatabaseImpl database = new DatabaseImpl();
    database.OpenConnection();

    try {
        database.initPreparedStatement(sql);
        int rows = database.statement.executeUpdate();
        System.out.println(rows + " dettes soldées ont été archivées.");
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        database.CloseConnection();
    }
}








public List<Dette> listerDettesNonSoldees(User user) {
    String sql = "SELECT * FROM dette WHERE client_id = ? AND montantrestant > 0";
    List<Dette> dettesNonSoldees = new ArrayList<>();
    DatabaseImpl database = new DatabaseImpl();
    database.OpenConnection();

    try {
        database.initPreparedStatement(sql);
        // Utiliser l'ID du client connecté (user.getId())
        database.statement.setInt(1, user.getId());
        ResultSet rs = database.statement.executeQuery();
        while (rs.next()) {
            Dette dette = new Dette();
            dette.setId(rs.getInt("id"));
            dette.setMontant(rs.getDouble("montant"));
            dette.setMontantRestant(rs.getDouble("montantrestant"));

            // Associer le client à la dette
            Client client = new Client();
            client.setId(user.getId());  // Associer l'ID du client connecté
            dette.setClient(client);

            dettesNonSoldees.add(dette);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        database.CloseConnection();
    }

    return dettesNonSoldees;
}


    
}
