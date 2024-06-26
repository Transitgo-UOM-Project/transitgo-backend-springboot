package bugBust.transitgo.repository;

import bugBust.transitgo.model.BusStopLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusStopLocationRepository extends JpaRepository<BusStopLocation, Integer> {
    // Custom query methods (if any) can be added here
}
