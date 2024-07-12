package bugBust.transitgo.repository;

import bugBust.transitgo.model.BusTimeTable;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BusTimeTableRepository extends CrudRepository<BusTimeTable, Integer> {
    List<BusTimeTable> findByBusIdAndDateBetween(int busId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT b.status FROM BusTimeTable b WHERE b.busId = :busId AND b.date = :date")
    List <String> findStatusByBusIdAndDate(int busId, LocalDate date);

    void deleteByBusIdAndJourneyNumber(int busId, int journeyNumber);



}
