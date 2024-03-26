package bugBust.transitgo.controller;




import bugBust.transitgo.model.BusStop;
import bugBust.transitgo.repository.BusStopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class BusStopController {
    @Autowired
    private BusStopRepository busstopRepository;



    @PostMapping("/busstop")
    BusStop newBusStop(@RequestBody BusStop newBusStop){
        return busstopRepository.save(newBusStop);
    }
    @GetMapping("/busstops")
    List<BusStop> getAllBusStops(){
        return busstopRepository.findAll();
    }

    @GetMapping("/busstop/{id}")
    BusStop getBusStopById(@PathVariable Long id) {
        return busstopRepository.findById(id).orElseThrow();
    }
    @PutMapping("/busstop/{id}")
    BusStop updateBusStop(@RequestBody BusStop newBusStop, @PathVariable Long id) {
        return busstopRepository.findById(id)
                .map(busStop -> {
                    busStop.setStopID(newBusStop.getStopID());
                    busStop.setName(newBusStop.getName());

                    return busstopRepository.save(busStop);
                }).orElseThrow() ;
    }

    @DeleteMapping("/busstop/{id}")
    String deleteBusStop(@PathVariable Long id){
//        if(!busstopRepository.existsById(id)){
//            throw new UserNotFoundException(id);
//        }
        busstopRepository.deleteById(id);
        return  "Bus stop with id "+id+" has been deleted success.";
    }




}
