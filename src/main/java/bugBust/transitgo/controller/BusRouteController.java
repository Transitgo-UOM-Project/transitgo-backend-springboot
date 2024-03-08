package bugBust.transitgo.controller;



import bugBust.transitgo.model.BusRoute;
import bugBust.transitgo.repository.BusRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class BusRouteController {
    @Autowired
    private BusRouteRepository busrouteRepository;

    @PostMapping("/busroute")
    BusRoute newBusRoute(@RequestBody BusRoute newBusRoute){
        return busrouteRepository.save(newBusRoute);
    }
    @GetMapping("/busroutes")
    List<BusRoute> getAllBusRoutes(){
        return busrouteRepository.findAll();
    }

    @GetMapping("/busroute/{id}")
   BusRoute getBusRouteById(@PathVariable Long id) {
        return busrouteRepository.findById(id).orElseThrow();
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
