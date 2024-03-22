package bugBust.transitgo.controller;



import bugBust.transitgo.model.BusRoute;
import bugBust.transitgo.repository.BusRouteRepository;
import bugBust.transitgo.services.BusRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class BusRouteController {
    @Autowired
    private BusRouteRepository busrouteRepository;

    @Autowired
    private BusRouteService busRouteService;

    @PostMapping("/busroute")
    public ResponseEntity<BusRoute> addARoute(@RequestBody BusRoute busroute) {
        return new ResponseEntity<BusRoute>(busRouteService.saveOrUpdateABusRoute(busroute), HttpStatus.CREATED);
    }
    @GetMapping("/busroutes")
    public Iterable<BusRoute> getAllRoutes(){
        return busRouteService.findAll();
    }

    @GetMapping("/busroute/{BusRouteNo}")
    public ResponseEntity<BusRoute> getRouteByRouteNo(@PathVariable int BusRouteNo) {
        return new ResponseEntity<BusRoute>(busRouteService.findBusRouteByNo(BusRouteNo), HttpStatus.OK);
    }
    @PutMapping("/busroute/{id}")
    BusRoute updateBusRoute(@RequestBody BusRoute newBusRoute, @PathVariable Long id) {
        return busrouteRepository.findById(id)
                .map(busRoute -> {
                    busRoute.setRouteno(newBusRoute.getRouteno());
                    busRoute.setRoutename(newBusRoute.getRoutename());

                    return busrouteRepository.save(busRoute);
                }).orElseThrow() ;
    }

    @DeleteMapping("/busroute/{id}")
    String deleteBusRoute(@PathVariable Long id){
//        if(!busrouteRepository.existsById(id)){
//            throw new UserNotFoundException(id);
//        }
        busrouteRepository.deleteById(id);
        return  "User with id "+id+" has been deleted success.";
    }




}
