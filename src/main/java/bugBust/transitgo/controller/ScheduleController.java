package bugBust.transitgo.controller;

import bugBust.transitgo.model.BusMgt;
import bugBust.transitgo.model.Schedule;
import bugBust.transitgo.repository.ScheduleRepository;
import bugBust.transitgo.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:3000")
public class ScheduleController {
    @Autowired
private ScheduleRepository scheduleRepository;
    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("schedule")
    public ResponseEntity<Schedule> createSchedule(@RequestBody Schedule schedule) {
        Schedule createdSchedule = scheduleService.saveOrUpdateASchedule(schedule);
        return new ResponseEntity<>(createdSchedule, HttpStatus.CREATED);
    }

    @GetMapping("schedule/{id}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable("id") int id) {
        Schedule schedule = scheduleService.findScheduleById(id);
        if (schedule != null) {
            return new ResponseEntity<>(schedule, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("schedules")
    public ResponseEntity<Iterable<Schedule>> getAllSchedules() {
        Iterable<Schedule> schedules = scheduleService.findAll();
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    @PutMapping("/schedule/{id}")
    Schedule updateSchedule(@RequestBody Schedule newSchedule, @PathVariable int id) {
        return scheduleRepository.findById(id)
                .map(schedule -> {
                    schedule.setArrivalTime(newSchedule.getArrivalTime());
                    schedule.setDepartureTime(newSchedule.getDepartureTime());
                    return scheduleRepository.save(schedule);
                })
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
    }

}
