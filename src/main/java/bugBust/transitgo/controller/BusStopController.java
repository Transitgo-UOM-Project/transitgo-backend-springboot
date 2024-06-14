//BusStopController.java
package bugBust.transitgo.controller;


import bugBust.transitgo.model.BusStop;
import bugBust.transitgo.model.Schedule;
import bugBust.transitgo.repository.BusStopRepository;
import bugBust.transitgo.repository.ScheduleRepository;
import bugBust.transitgo.services.BusStopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin("http://localhost:3000")
public class BusStopController {
    @Autowired
    private BusStopRepository busstopRepository;

    @Autowired
    private BusStopService busStopService;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @PostMapping("busstop")
    public ResponseEntity<BusStop> addABusstop(@RequestBody BusStop busstop) {
        return new ResponseEntity<BusStop>(busStopService.saveOrUpdateABusStop(busstop), HttpStatus.CREATED);
    }

    @GetMapping("/busstops")
    public Iterable<BusStop> getAllBuses(){
        return busStopService.findAll();
    }

    @GetMapping("busstop/{busstopId}")
    public ResponseEntity<BusStop> getBookingById(@PathVariable int busstopId) {
        return new ResponseEntity<BusStop>(busStopService.findBusStopById(busstopId), HttpStatus.OK);
    }

    @PutMapping("/busstop/{id}")
    BusStop updateBusStop(@RequestBody BusStop newBusStop, @PathVariable int id) {
        return busstopRepository.findById(id)
                .map(busStop -> {

                    busStop.setName(newBusStop.getName());
                    busStop.setOrderIndex(newBusStop.getOrderIndex());

                    return busstopRepository.save(busStop);
                }).orElseThrow() ;
    }

    @DeleteMapping("/busstop/{busStopId}")
    public void deleteBusStop(@PathVariable int busStopId) {
        // Check if there are any schedules referencing this bus stop
        List<Schedule> schedules = scheduleRepository.findScheduleByBusStop_StopID(busStopId);

        // Delete schedules first
        for (Schedule schedule : schedules) {
            scheduleRepository.delete(schedule);
        }

        // Now delete the bus stop
        busstopRepository.deleteById(busStopId);
    }

    @GetMapping("/route/{route_no}/stops")
    public Iterable<BusStop>  findBusStopByRouteNo(@PathVariable int route_no){
        return busStopService. findBusStopByBusRouteNo(route_no);
    }


}
