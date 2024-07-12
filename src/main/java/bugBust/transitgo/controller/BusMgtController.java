//BusMgtController.java
package bugBust.transitgo.controller;

import bugBust.transitgo.model.BusMgt;
import bugBust.transitgo.model.BusTimeTable;
import bugBust.transitgo.model.Schedule;
import bugBust.transitgo.repository.BusMgtRepository;
import bugBust.transitgo.repository.BusRouteRepository;
import bugBust.transitgo.repository.BusTimeTableRepository;
import bugBust.transitgo.repository.ScheduleRepository;
import bugBust.transitgo.services.BusMgtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin(origins = {"http://localhost:3000" , "http://localhost:8081"})
public class BusMgtController {
    @Autowired
    private BusMgtRepository busmgtRepository;
    @Autowired
    private BusRouteRepository busRouteRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private BusMgtService busMgtService;


    private static final Logger logger = LoggerFactory.getLogger(BusMgtService.class);
    @PostMapping("/bus")
    public ResponseEntity<BusMgt> addABus(@RequestBody BusMgt bus) {
        return new ResponseEntity<>(busMgtService.saveOrUpdateABus(bus), HttpStatus.CREATED);
    }

    @GetMapping("/buses")
    public Iterable<BusMgt> getAllBuses(){
        return busMgtService.findAll();
    }

    @GetMapping("/bus/{busid}")
    public ResponseEntity<BusMgt> getBusById(@PathVariable int busid) {
        return new ResponseEntity<>(busMgtService.findBusById(busid), HttpStatus.OK);
    }

    @GetMapping("/bussched/{busid}")
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
bus.setNoOfJourneysPerDay(newBus.getNoOfJourneysPerDay());
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




    @DeleteMapping("/bus/{id}")
    String deleteBus(@PathVariable int id) {
        busmgtRepository.deleteById(id);
        return "Bus with id " + id + " has been deleted successfully.";
    }

    @GetMapping("bus/search")
    public ResponseEntity<List<BusMgt>> searchBusSchedules(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam String direction,
            @RequestParam LocalDate date) {
        try {
            List<BusMgt> buses = busMgtService.searchBusSchedules(from, to, direction ,date);
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


    @PutMapping("/busLocation/{busid}")
    public BusMgt updateBusLocation(@RequestBody BusMgt newBus, @PathVariable int busid){
        return busmgtRepository.findById(busid)
                .map(bus -> {
                    bus.setLatitude(newBus.getLatitude());
                    bus.setLongitude(newBus.getLongitude());
                    logger.info("Location of bus {} updated to ({} , {})", bus.getRegNo(), newBus.getLatitude(), newBus.getLongitude());
                    return busmgtRepository.save(bus);
                })
                .orElseThrow(() -> new IllegalArgumentException("Bus not found with id: " + busid));
    }

}
