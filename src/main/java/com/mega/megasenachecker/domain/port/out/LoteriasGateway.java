package com.mega.megasenachecker.domain.port.out;

import com.mega.megasenachecker.domain.model.Concurso;

public interface LoteriasGateway {
    Concurso buscarUltimoSorteio();
}

