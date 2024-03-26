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
}