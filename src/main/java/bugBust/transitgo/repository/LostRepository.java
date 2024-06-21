package bugBust.transitgo.repository;

import bugBust.transitgo.model.LostItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LostRepository extends JpaRepository<LostItems,Long> {
    Optional<LostItems> findById(Long id);
}
