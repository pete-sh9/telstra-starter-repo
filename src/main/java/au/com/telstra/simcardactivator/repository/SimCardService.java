package au.com.telstra.simcardactivator.repository;


import au.com.telstra.simcardactivator.SimCardDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SimCardService {

    private final SimRepository repository;

    public SimCardService (SimRepository repository) {
        this.repository = repository;
    }

    public SimCardDB save(SimCardDB simCard) {
        return repository.save(simCard);
    }

    public Optional<SimCardDB> findById(Long id) {
        return repository.findById(id);
    }
}
