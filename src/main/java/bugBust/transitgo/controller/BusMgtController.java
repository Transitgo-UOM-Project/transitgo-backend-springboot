//BusMgtController.java

package bugBust.transitgo.controller;

import bugBust.transitgo.model.BusMgt;
import bugBust.transitgo.model.Schedule;
import bugBust.transitgo.repository.BusMgtRepository;
import bugBust.transitgo.repository.BusRouteRepository;
import bugBust.transitgo.repository.ScheduleRepository;
import bugBust.transitgo.services.BusMgtService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


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

                    // Check if the bus route has changed
                    if (!Objects.equals(bus.getRouteNo(), newBus.getRouteNo())) {
                        // Retrieve existing schedules associated with the bus
                        List<Schedule> existingSchedules = scheduleRepository.findScheduleByBusId(busid);
                        // Delete existing schedules
                        scheduleRepository.deleteAll(existingSchedules);

                        //add new schedules

                    }
                    bus.setRegNo(newBus.getRegNo());
                    bus.setBusroute(newBus.getBusroute());



                    // Save and return the updated bus
                    return busmgtRepository.save(bus);
                })
                .orElseThrow(() -> new IllegalArgumentException("Bus not found with id: " + busid));
    }


    @DeleteMapping("/bus/{id}")
    String deleteBus(@PathVariable int id){

        busmgtRepository.deleteById(id);
        return  "bus with id "+id+" has been deleted success.";
    }



}
