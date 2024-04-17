package bugBust.transitgo.controller;

import bugBust.transitgo.model.BusMgt;
import bugBust.transitgo.repository.BusMgtRepository;
import bugBust.transitgo.repository.BusRouteRepository;
import bugBust.transitgo.services.BusMgtService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:3000")
public class BusMgtController {
    @Autowired
    private BusMgtRepository busmgtRepository;
    @Autowired
    private BusRouteRepository busRouteRepository;

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

    @GetMapping("bus/{busId}")
    public ResponseEntity<BusMgt> getBookingById(@PathVariable int busId) {
        return new ResponseEntity<BusMgt>(busMgtService.findBusById(busId), HttpStatus.OK);
    }
    @PutMapping("/bus/{id}")
    BusMgt updateBus(@RequestBody BusMgt newBus, @PathVariable int id) {
        return busmgtRepository.findById(id)
                .map(bus -> {
                    bus.setId(newBus.getId());
                    bus.setRegNo(newBus.getRegNo());

                    return busmgtRepository.save(bus);
                }).orElseThrow() ;
    }

    @DeleteMapping("/bus/{id}")
    String deleteBus(@PathVariable int id){

        busmgtRepository.deleteById(id);
        return  "User with id "+id+" has been deleted success.";
    }



}
