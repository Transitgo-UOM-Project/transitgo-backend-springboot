//ScheduleRepository.java
package bugBust.transitgo.repository;

import bugBust.transitgo.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule,Integer> {
    // Define a custom query method to find a schedule by bus and bus stop


    List<Schedule> findScheduleByBusId(int bus_id);

    List<Schedule> findByBusIdAndDirection(int bus_id , String direction) ;

Schedule getByscheduleId(Integer scheduleId);
}
