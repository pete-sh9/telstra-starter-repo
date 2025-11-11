package au.com.telstra.simcardactivator.repository;

import au.com.telstra.simcardactivator.SimCardDB;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SimRepository extends JpaRepository<SimCardDB, Long> {
    Optional<SimCardDB> findByIccid(String iccid);
}
