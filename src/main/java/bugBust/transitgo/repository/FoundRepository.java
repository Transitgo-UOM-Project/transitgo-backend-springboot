package bugBust.transitgo.repository;

import bugBust.transitgo.model.FoundItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoundRepository extends JpaRepository<FoundItems, Long> {
}
