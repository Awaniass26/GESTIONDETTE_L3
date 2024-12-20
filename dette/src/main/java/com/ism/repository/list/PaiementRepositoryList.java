package com.ism.repository.list;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.ism.entity.Paiement;
import com.ism.repository.PaiementRepository;

public class PaiementRepositoryList extends RepositoryListImpl<Paiement> implements PaiementRepository {
    
    private List<Paiement> paiements = new ArrayList<>();

    @Override
    public List<Paiement> listerPaiementsParDette(int detteId) {
        return paiements.stream()
            .filter(paiement -> paiement.getDette().getId() == detteId)
            .collect(Collectors.toList());
    }
}
