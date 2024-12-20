package com.ism.services;

import com.ism.entity.Dette;

public interface DetteService extends Service<Dette> {
    Dette findById(int id);

    void updateCumulMontantDus(Dette selectedDette);
}
