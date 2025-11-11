package au.com.telstra.simcardactivator.repository;


import au.com.telstra.simcardactivator.SimCardDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SimCardService {

    @Autowired
    private SimRepository repository;

    public SimCardDB save(SimCardDB simCard) {
        return repository.save(simCard);
    }

    public Optional<SimCardDB> findById(Long id) {
        return repository.findById(id);
    }
}
