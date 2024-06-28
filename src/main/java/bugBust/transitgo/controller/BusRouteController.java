//BusRouteController.java

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
    @PutMapping("/busroute/{busRouteNo}")
    public ResponseEntity<BusRoute> updateBusRoute(@PathVariable("busRouteNo") int busRouteNo, @RequestBody BusRoute updatedBusRoute) {
        BusRoute updatedRoute = busRouteService.updateBusRoute(busRouteNo, updatedBusRoute);
        if (updatedRoute != null) {
            return new ResponseEntity<>(updatedRoute, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/busroute/{id}")
    String deleteBusRoute(@PathVariable int id){
//        if(!busrouteRepository.existsById(id)){
//            throw new UserNotFoundException(id);
//        }
        busrouteRepository.deleteById(id);
        return  "User with id "+id+" has been deleted success.";
    }

//    @PostMapping("route/addStopBetween")
//    public void addStopBetween(@RequestParam String routeName, @RequestParam String newStopName, @RequestParam int afterStopOrderIndex) {
//        busRouteService.addBusStopBetween(routeName, newStopName, afterStopOrderIndex);
//    }




}
