//BusMgtController.java
package bugBust.transitgo.controller;

import bugBust.transitgo.model.BusMgt;
import bugBust.transitgo.model.Schedule;
import bugBust.transitgo.repository.BusMgtRepository;
import bugBust.transitgo.repository.BusRouteRepository;
import bugBust.transitgo.repository.ScheduleRepository;
import bugBust.transitgo.scheduledTask.BusStatusUpdateTask;
import bugBust.transitgo.services.BusMgtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin("http://localhost:3000")
public class BusMgtController {
    @Autowired
    private BusMgtRepository busmgtRepository;
    @Autowired
    private BusRouteRepository busRouteRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private BusMgtService busMgtService;

    private BusStatusUpdateTask busStatusUpdateTask;

    private static final Logger logger = LoggerFactory.getLogger(BusMgtService.class);
    @PostMapping("bus")
    public ResponseEntity<BusMgt> addABus(@RequestBody BusMgt bus) {
        return new ResponseEntity<BusMgt>(busMgtService.saveOrUpdateABus(bus), HttpStatus.CREATED);
    }

    @GetMapping("/buses")
    public Iterable<BusMgt> getAllBuses(){
        return busMgtService.findAll();
    }

    @GetMapping("bus/{busid}")
    public ResponseEntity<BusMgt> getBusById(@PathVariable int busid) {
        return new ResponseEntity<BusMgt>(busMgtService.findBusById(busid), HttpStatus.OK);
    }

    @GetMapping("bussched/{busid}")
    public List<Schedule> getScheduleByBusId(@PathVariable int busid) {
        // Retrieve existing schedules associated with the bus
        List<Schedule> existingSchedules = scheduleRepository.findScheduleByBusId(busid);
        return existingSchedules;
    }

    @PutMapping("/bus/{busid}")
    BusMgt updateBus(@RequestBody BusMgt newBus, @PathVariable int busid) {
        return busmgtRepository.findById(busid)
                .map(bus -> {
                    // Update the bus details

                    bus.setRegNo(newBus.getRegNo());
              bus.setBusroute(newBus.getBusroute());

                    // Save and return the updated bus
                    return busmgtRepository.save(bus);
                })
                .orElseThrow(() -> new IllegalArgumentException("Bus not found with id: " + busid));
    }
    @PutMapping("/busStatus/{busid}")
    BusMgt updateBusStatus(@RequestBody BusMgt newBus, @PathVariable int busid) {
        return busmgtRepository.findById(busid)
                .map(bus -> {
                    // Update the bus status details
                         String oldStatus = bus.getStatus();
                    bus.setStatus(newBus.getStatus());
                    logger.info("status of {} changed from {} to {}" , bus.getRegNo(),oldStatus, newBus.getStatus());
                    // Save and return the updated bus
                    return busmgtRepository.save(bus);
                })
                .orElseThrow(() -> new IllegalArgumentException("Bus not found with id: " + busid));
    }

    public BusMgtController(BusStatusUpdateTask busStatusUpdateTask) {
        this.busStatusUpdateTask = busStatusUpdateTask;
    }
    @DeleteMapping("/bus/{id}")
    String deleteBus(@PathVariable int id) {
        busmgtRepository.deleteById(id);
        return "Bus with id " + id + " has been deleted successfully.";
    }

    @GetMapping("bus/search")
    public ResponseEntity<List<BusMgt>> searchBusSchedules(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam String direction) {
        try {
            List<BusMgt> buses = busMgtService.searchBusSchedules(from, to, direction);
            return ResponseEntity.ok(buses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/bus/{busid}/stops")
    public ResponseEntity<List<Schedule>> getBusStopsAndTimes(@PathVariable int busid) {
        List<Schedule> schedules = scheduleRepository.findScheduleByBusId(busid);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }
}
