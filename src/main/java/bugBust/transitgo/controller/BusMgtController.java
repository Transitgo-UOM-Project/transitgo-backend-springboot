package bugBust.transitgo.controller;

import bugBust.transitgo.model.BusMgt;
import bugBust.transitgo.model.BusRoute;
import bugBust.transitgo.model.BusStop;
import bugBust.transitgo.repository.BusMgtRepository;
import bugBust.transitgo.repository.BusRouteRepository;
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


    @PostMapping("/bus")
    BusMgt newBus(@RequestBody BusMgt newBus){
        return busmgtRepository.save(newBus);
    }
    @GetMapping("/buses")
    List<BusMgt> getAllBuses(){
        return busmgtRepository.findAll();
    }

    @GetMapping("/bus/{id}")
    BusMgt getBusById(@PathVariable int id) {
        return busmgtRepository.findById(id).orElseThrow();
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
//        if(!busmgtRepository.existsById(id)){
//            throw new UserNotFoundException(id);
//        }
        busmgtRepository.deleteById(id);
        return  "User with id "+id+" has been deleted success.";
    }



}
