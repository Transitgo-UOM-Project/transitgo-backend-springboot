//package bugBust.transitgo.services;
//
//import bugBust.transitgo.model.BusTimeTable;
//import bugBust.transitgo.repository.BusTimeTableRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Service
//public class BusTimeTableService {
//
//    @Autowired
//    private BusTimeTableRepository busTimeTableRepository;
//
//    public List<BusTimeTable> getWeeklySchedule(int busId, LocalDate startDate, LocalDate endDate) {
//        return busTimeTableRepository.findByBusIdAndDateBetween(busId, startDate, endDate);
//    }
//
//    public void saveWeeklySchedule(Long busId, List<BusTimeTable> schedules) {
//        LocalDate startDate = schedules.get(0).getDate();
//        LocalDate endDate = startDate.plusDays(6);
//
//        // Delete existing schedules for the week
//        busTimeTableRepository.deleteWeeklySchedules(busId, startDate, endDate);
//
//        // Save new schedules
//        busTimeTableRepository.saveAll(schedules);
//    }
//}
