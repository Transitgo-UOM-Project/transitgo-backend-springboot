package bugBust.transitgo.repository;
import bugBust.transitgo.model.BusRoute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRouteRepository extends JpaRepository<BusRoute,Integer> {
BusRoute getByrouteno(int routeno);
}
