package bugBust.transitgo.repository;

import bugBust.transitgo.model.RateReviews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateRepository extends JpaRepository<RateReviews, Long> {
}
