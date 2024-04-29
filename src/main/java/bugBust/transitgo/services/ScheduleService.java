package bugBust.transitgo.services;


import bugBust.transitgo.model.Schedule;
import bugBust.transitgo.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    public Schedule saveOrUpdateASchedule(Schedule schedule) {
        // Check if a schedule already exists for the combination of bus and bus stop
        Schedule existingSchedule = scheduleRepository.findByBusAndBusStop(schedule.getBus(), schedule.getBusStop());
        if (existingSchedule != null) {
            // Schedule already exists, throw an exception or handle accordingly
            throw new IllegalArgumentException("Schedule already exists for the combination of bus and bus stop");
        }

        // No existing schedule found, proceed with saving the new schedule
        return scheduleRepository.save(schedule);
    }

    public Schedule findScheduleById(int id) {
        return scheduleRepository.getByscheduleId(id);
    }

    public Iterable<Schedule> findAll() {
        return scheduleRepository.findAll();
    }
}