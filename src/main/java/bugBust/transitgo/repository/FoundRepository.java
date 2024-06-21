package bugBust.transitgo.repository;

import bugBust.transitgo.model.FoundItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FoundRepository extends JpaRepository<FoundItems, Long> {
    Optional<FoundItems> findById(Long id);
}
