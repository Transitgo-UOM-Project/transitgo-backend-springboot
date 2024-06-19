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
    String findStatusByBusIdAndDate(int busId, LocalDate date);
    List<BusTimeTable> findByBusId(int busId);

    @Query(value = "WITH RECURSIVE DateSeries AS ( " +
            "    SELECT :date - INTERVAL '7' DAY AS date " +
            "    UNION ALL " +
            "    SELECT date - INTERVAL '7' DAY " +
            "    FROM DateSeries " +
            "    WHERE date - INTERVAL '7' DAY >= (SELECT MIN(b.date) FROM bus_time_table b WHERE b.bus_id = :busId) " +
            ") " +
            "SELECT b.* " +
            "FROM bus_time_table b " +
            "JOIN DateSeries d " +
            "ON b.date = d.date " +
            "WHERE b.bus_id = :busId " +
            "ORDER BY b.date DESC",
            nativeQuery = true)
    List<BusTimeTable> findLatestStatusBeforeDate(@Param("busId") int busId, @Param("date") LocalDate date);

//    @Transactional
//    @Modifying
//    @Query("DELETE FROM BusTimeTable b WHERE b.date < :today")
//    void deletePastSchedules(LocalDate today);
}
