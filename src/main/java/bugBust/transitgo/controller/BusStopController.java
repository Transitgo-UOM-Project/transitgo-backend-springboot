//BusStopController.java
package bugBust.transitgo.controller;


import bugBust.transitgo.model.BusStop;
import bugBust.transitgo.repository.BusStopRepository;
import bugBust.transitgo.services.BusStopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@CrossOrigin("http://localhost:3000")
public class BusStopController {
    @Autowired
    private BusStopRepository busstopRepository;

    @Autowired
    private BusStopService busStopService;


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

                    return busstopRepository.save(busStop);
                }).orElseThrow() ;
    }

    @DeleteMapping("/busstop/{id}")
    String deleteBusStop(@PathVariable int id){
//        if(!busstopRepository.existsById(id)){
//            throw new UserNotFoundException(id);
//        }
        busstopRepository.deleteById(id);
        return  "Bus stop with id "+id+" has been deleted success.";
    }




}
