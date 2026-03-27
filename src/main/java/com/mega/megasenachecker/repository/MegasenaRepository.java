package com.mega.megasenachecker.repository;

import com.mega.megasenachecker.model.MegasenaDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MegasenaRepository extends MongoRepository<MegasenaDocument, String> {

    Optional<MegasenaDocument> findByNumero(Integer numero);

    boolean existsByNumero(Integer numero);

}
