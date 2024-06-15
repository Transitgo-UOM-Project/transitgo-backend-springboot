package bugBust.transitgo.repository;

import bugBust.transitgo.model.LostItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LostRepository extends JpaRepository<LostItems,Long> {
}
