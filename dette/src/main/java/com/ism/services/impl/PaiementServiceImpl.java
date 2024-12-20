package com.ism.services.impl;

import java.util.List;

import com.ism.entity.Paiement;
import com.ism.repository.PaiementRepository;
import com.ism.repository.bd.PaiementRepositoryBd;
import com.ism.services.PaiementService;

public class PaiementServiceImpl extends ServiceImpl<Paiement> implements PaiementService {

    private final PaiementRepository paiementRepository;
    
        public PaiementServiceImpl(PaiementRepositoryBd repository) {
            super(repository);
            this.paiementRepository = (PaiementRepository) repository;
    }


    public List<Paiement> getPaiementsParDette(int detteId) {
    return paiementRepository.listerPaiementsParDette(detteId);
}

}
