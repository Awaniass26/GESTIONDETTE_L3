package com.ism.repository.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ism.core.bd.DatabaseImpl;
import com.ism.entity.Paiement;
import com.ism.repository.PaiementRepository;

public class PaiementRepositoryBd extends RepositoryBdImpl<Paiement> implements PaiementRepository{
    
     public PaiementRepositoryBd() {
        super(Paiement.class);
    }


    public List<Paiement> listerPaiementsParDette(int detteId) {
    String sql = "SELECT * FROM paiement WHERE dette_id = ?";
    List<Paiement> paiements = new ArrayList<>();
    DatabaseImpl database = new DatabaseImpl();
    database.OpenConnection();

    try {
        database.initPreparedStatement(sql);
        database.statement.setInt(1, detteId);
        ResultSet rs = database.statement.executeQuery();
        while (rs.next()) {
            Paiement paiement = new Paiement();
            paiement.setId(rs.getInt("id"));
            paiement.setMontant(rs.getDouble("montant"));
            paiement.setDate(rs.getDate("date"));
            paiement.setDette_id(detteId);
            paiements.add(paiement);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        database.CloseConnection();
    }

    return paiements;
}


}
