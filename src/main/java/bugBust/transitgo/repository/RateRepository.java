package bugBust.transitgo.repository;

import bugBust.transitgo.model.RateReviews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RateRepository extends JpaRepository<RateReviews, Long> {

List<RateReviews> findByBusId(int busId);
}
