package com.ism.repository.list;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ism.entity.Article;
import com.ism.entity.Dette;
import com.ism.entity.Paiement;
import com.ism.repository.DetteRepository;

public class DetteRepositoryList extends RepositoryListImpl<Dette> implements DetteRepository {

    private List<Dette> dettes = new ArrayList<>();

    @Override
    public Dette findById(int id) {
        return dettes.stream()
            .filter(dette -> dette.getId() == id)
            .findFirst()
            .orElse(null); 
    }

    @Override
    public List<Dette> findUnpaidDebtsByClientId(int clientId) {
        return dettes.stream()
            .filter(dette -> dette.getClient().getId() == clientId && dette.getMontantRestant() > 0)
            .collect(Collectors.toList());
    }

    @Override
    public List<Article> findArticlesByDetteId(int detteId) {
        Dette dette = findById(detteId);
        return dette != null ? dette.getArticles() : new ArrayList<>();
    }

    @Override
    public List<Paiement> findPaiementsByDetteId(int detteId) {
        Dette dette = findById(detteId);
        return dette != null ? dette.getPaiements() : new ArrayList<>();
    }

    @Override
    public void update(Dette selectedDette) {
        for (int i = 0; i < dettes.size(); i++) {
            if (dettes.get(i).getId() == selectedDette.getId()) {
                dettes.set(i, selectedDette); 
                return;
            }
        }
        throw new IllegalArgumentException("Dette avec ID " + selectedDette.getId() + " introuvable !");
    }

    @Override
    public void savePaiement(Paiement paiement) {
        Dette dette = findById(paiement.getDette().getId());
        if (dette != null) {
            dette.addPaiement(paiement);
            update(dette); 
        } else {
            throw new IllegalArgumentException("Dette avec ID " + paiement.getDette().getId() + " introuvable !");
        }
    }
}
