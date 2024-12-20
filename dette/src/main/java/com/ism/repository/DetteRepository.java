package com.ism.repository;

import java.util.List;

import com.ism.core.repository.Repository;
import com.ism.entity.Article;
import com.ism.entity.Dette;
import com.ism.entity.Paiement;

public interface DetteRepository extends Repository<Dette> {
    Dette findById(int id);
    List<Dette> findUnpaidDebtsByClientId(int clientId);
    List<Article> findArticlesByDetteId(int detteId); 
    List<Paiement> findPaiementsByDetteId(int detteId);
    void update(Dette selectedDette);
    void savePaiement(Paiement paiement);
}
