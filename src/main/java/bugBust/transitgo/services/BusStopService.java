//BusStopService.java
package bugBust.transitgo.services;
import bugBust.transitgo.model.BusStop;
import bugBust.transitgo.repository.BusStopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusStopService {
    @Autowired
    private BusStopRepository busstopRepository;

    public BusStop saveOrUpdateABusStop(BusStop busstop) {
        return busstopRepository.save(busstop);
    }

    public BusStop findBusStopById(int Id) {
        return busstopRepository.getBystopID(Id);
    }

    public Iterable<BusStop> findAll() {
        return busstopRepository.findAll();
    }
}
