package bugBust.transitgo.services;

import bugBust.transitgo.model.BusTimeTable;
import bugBust.transitgo.repository.BusTimeTableRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
public class BusTimeTableService {

    @Autowired
    private BusTimeTableRepository busTimeTableRepository;


    @Transactional
    public void updateJourney(int busId, int oldJourneys, int newJourneys) {
        if (oldJourneys == 1 && newJourneys == 2) {
            addJourneyTwoForCurrentWeek(busId);
        } else if (oldJourneys == 2 && newJourneys == 1) {
            busTimeTableRepository.deleteByBusIdAndJourneyNumber(busId, 2);
        }
    }

    private void addJourneyTwoForCurrentWeek(int busId) {
        LocalDate startOfWeek = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate endOfWeek = startOfWeek.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));

        List<BusTimeTable> newEntries = new ArrayList<>();
        for (LocalDate date = startOfWeek; !date.isAfter(endOfWeek); date = date.plusDays(1)) {
            BusTimeTable busTimeTable = new BusTimeTable();
            busTimeTable.setBusId(busId);
            busTimeTable.setDate(date);
            busTimeTable.setJourneyNumber(2);
            busTimeTable.setStatus("off");
            newEntries.add(busTimeTable);
        }

        busTimeTableRepository.saveAll(newEntries);
    }
}
