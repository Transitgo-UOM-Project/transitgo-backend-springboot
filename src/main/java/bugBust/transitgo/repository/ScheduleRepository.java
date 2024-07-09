//ScheduleRepository.java
package bugBust.transitgo.repository;

import bugBust.transitgo.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule,Integer> {


    List<Schedule> findScheduleByBusId(int bus_id);

    List<Schedule> findScheduleByBusStop_StopID(int stop_id);

    List<Schedule> findByBusIdAndDirection(int bus_id , String direction) ;



Schedule getByscheduleId(Integer scheduleId);
}
