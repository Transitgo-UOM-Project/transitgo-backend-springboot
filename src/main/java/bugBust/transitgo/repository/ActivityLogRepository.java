package bugBust.transitgo.repository;

import bugBust.transitgo.model.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
    List<ActivityLog> findByUserIdOrderByDateTimeDesc(Long userid);

    void deleteByActivityIdAndActivityType(Long activityId, String ActivityType);

    void deleteByUserId(Long id);

   Optional<ActivityLog> findByActivityIdAndActivityType(Long activityId, String ActivityType);

}
