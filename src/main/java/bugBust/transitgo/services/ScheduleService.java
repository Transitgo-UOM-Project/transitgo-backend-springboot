//ScheduleService.java
package bugBust.transitgo.services;

import bugBust.transitgo.model.Schedule;
import bugBust.transitgo.repository.ScheduleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);
    public Schedule saveOrUpdateASchedule(Schedule schedule) {

        // No existing schedule found, proceed with saving the new schedule
        return scheduleRepository.save(schedule);
    }

    public Schedule findScheduleById(int id) {
        return scheduleRepository.getByscheduleId(id);
    }



    public Iterable<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    public LocalTime[] getJourneyStartAndEndTime(int busId, String direction) {
        List<Schedule> schedules = scheduleRepository.findByBusIdAndDirection(busId, direction);

//        logger.info("schedule 1 {}" ,schedules.get(0).getScheduleId());
//        logger.info("schedule 2{}" ,schedules.get(1).getScheduleId());
//        logger.info("schedule 3 {}" ,schedules.get(2).getScheduleId());


        if (schedules.isEmpty()) {
            logger.info("empty as fuck");
            return null;
        }

        LocalTime startTime = schedules.get(0).getDepartureTime();
        LocalTime endTime = schedules.get(schedules.size() - 1).getArrivalTime();

        logger.info("bus {} of start time  - {} endtime {}", busId,startTime ,endTime);
        return new LocalTime[]{startTime, endTime};
    }


}