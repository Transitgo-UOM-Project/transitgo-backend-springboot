package bugBust.transitgo.repository;
import bugBust.transitgo.model.BusStop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusStopRepository extends JpaRepository<BusStop,Integer> {
    BusStop getBystopID(int Id);
}
