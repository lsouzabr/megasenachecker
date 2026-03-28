package com.mega.megasenachecker.domain.port.out;

import com.mega.megasenachecker.domain.model.Concurso;

public interface ConcursoRepository {
    boolean existe(Integer numero);
    void salvar(Concurso concurso);
}

