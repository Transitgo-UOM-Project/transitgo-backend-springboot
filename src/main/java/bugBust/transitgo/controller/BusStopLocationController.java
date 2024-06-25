package bugBust.transitgo.controller;

import bugBust.transitgo.model.BusStopLocation;
import bugBust.transitgo.repository.BusStopLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000")
public class BusStopLocationController {

    @Autowired
    private BusStopLocationRepository busStopLocationRepository;

    @PostMapping("/busstoplocation")
    public BusStopLocation newBusStopLocation(@RequestBody BusStopLocation newBusStopLocation) {
        return busStopLocationRepository.save(newBusStopLocation);
    }

    @GetMapping("/busstoplocations")
    public List<BusStopLocation> getAllBusStopLocations() {
        return busStopLocationRepository.findAll();
    }

    @PutMapping("/busstoplocation/{id}")
    public BusStopLocation updateBusStopLocation(@PathVariable int id, @RequestBody BusStopLocation updatedBusStopLocation) {
        Optional<BusStopLocation> optionalBusStopLocation = busStopLocationRepository.findById(id);
        if (optionalBusStopLocation.isPresent()) {
            BusStopLocation existingBusStopLocation = optionalBusStopLocation.get();
            existingBusStopLocation.setLocation(updatedBusStopLocation.getLocation());
            existingBusStopLocation.setLatitude(updatedBusStopLocation.getLatitude());
            existingBusStopLocation.setLongitude(updatedBusStopLocation.getLongitude());
            return busStopLocationRepository.save(existingBusStopLocation);
        } else {
            throw new RuntimeException("Bus stop location not found with id " + id);
        }
    }

    @DeleteMapping("/busstoplocation/{id}")
    public void deleteBusStopLocation(@PathVariable int id) {
        busStopLocationRepository.deleteById(id);
    }
}

