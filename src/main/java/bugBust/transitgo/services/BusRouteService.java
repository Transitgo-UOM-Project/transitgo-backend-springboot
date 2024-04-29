package bugBust.transitgo.services;

import bugBust.transitgo.model.BusRoute;
import bugBust.transitgo.repository.BusRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            // Update the existing BusRoute with the data from updatedBusRoute
            existingBusRoute.setBusStops(updatedBusRoute.getBusStops());
            // Update other properties as needed

            // Save the updated BusRoute
            return busRouteRepository.save(existingBusRoute);
        } else {
            // Handle the case where the BusRoute with the given number doesn't exist
            return null;
        }
    }
}