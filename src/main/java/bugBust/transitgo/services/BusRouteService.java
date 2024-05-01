package bugBust.transitgo.services;

import bugBust.transitgo.model.BusRoute;
import bugBust.transitgo.model.BusStop;
import bugBust.transitgo.repository.BusRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusRouteService {

    @Autowired
    private BusRouteRepository busRouteRepository;

    public BusRoute saveOrUpdateABusRoute(BusRoute busRoute) {
        return busRouteRepository.save(busRoute);
    }

    public BusRoute findBusRouteByNo(int BusRouteNo) {
        return busRouteRepository.getByrouteno(BusRouteNo);
    }

    public Iterable<BusRoute> findAll() {
        return busRouteRepository.findAll();
    }

    public BusRoute updateBusRoute(int busRouteNo, BusRoute updatedBusRoute) {
        BusRoute existingBusRoute = findBusRouteByNo(busRouteNo);
        if (existingBusRoute != null) {
            // Update the existing bus route properties
            existingBusRoute.setRoutename(updatedBusRoute.getRoutename());
            // Update other properties as needed

            // Update or modify bus stops
            List<BusStop> existingBusStops = existingBusRoute.getBusStops();
            List<BusStop> updatedBusStops = updatedBusRoute.getBusStops();

            // Update existing bus stops or add new ones
            for (BusStop updatedBusStop : updatedBusStops) {
                boolean found = false;
                for (BusStop existingBusStop : existingBusStops) {
                    if (existingBusStop.getStopID() == updatedBusStop.getStopID()) {
                        // Update existing bus stop with data from updated bus stop
                        existingBusStop.setName(updatedBusStop.getName());
                        // Update other properties as needed
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    // Add new bus stop to the list
                    existingBusStops.add(updatedBusStop);
                }
            }

            // Remove bus stops that are not in the updated list
            existingBusStops.removeIf(existingBusStop -> !updatedBusStops.contains(existingBusStop));

            // Save the updated bus route
            return busRouteRepository.save(existingBusRoute);
        } else {
            // Handle the case where the bus route with the given number doesn't exist
            return null;
        }
    }

}