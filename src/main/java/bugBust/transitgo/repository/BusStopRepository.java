//BusStopRepository.java
package bugBust.transitgo.repository;

import bugBust.transitgo.model.BusStop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusStopRepository extends JpaRepository<BusStop,Integer> {
    BusStop getBystopID(int Id);

    List<BusStop> findBusStopByBusroute_Routeno(int route_no);
}
