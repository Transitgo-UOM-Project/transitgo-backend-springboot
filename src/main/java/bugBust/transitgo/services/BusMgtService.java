//BusMgtService.java
package bugBust.transitgo.services;

import bugBust.transitgo.model.BusMgt;
import bugBust.transitgo.model.BusTimeTable;
import bugBust.transitgo.repository.BusMgtRepository;
import bugBust.transitgo.repository.BusTimeTableRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class BusMgtService {
    @Autowired
    private BusMgtRepository busmgtRepository;

    private static final Logger logger = LoggerFactory.getLogger(BusMgtService.class);

    public BusMgt saveOrUpdateABus(BusMgt bus) {
        Optional<BusMgt> existingBus = busmgtRepository.findById(bus.getId());
        if (existingBus.isPresent()) {
            BusMgt busToUpdate = existingBus.get();
            busToUpdate.setDelay(bus.getDelay());
            busToUpdate.setLastLeftStop(bus.getLastLeftStop());
            busToUpdate.setNextLocation(bus.getNextLocation());

            return busmgtRepository.save(busToUpdate);
        } else {
            return busmgtRepository.save(bus);
        }
    }

    public Iterable<BusMgt> findAll() {
        return busmgtRepository.findAll();
    }

    public BusMgt findBusById(int busid) {
        return busmgtRepository.findById(busid).orElse(null);
    }

    public List<BusMgt> searchBusSchedules(String from, String to, String direction, LocalDate date) {
        logger.info("Searching bus schedules from {} to {} with direction {} on {}", from, to, direction, date);
        try {
            List<BusMgt> buses = busmgtRepository.findAll().stream()
                    .filter(bus -> {
                        if (bus.getStatus().equals("off")) {
                            return false;
                        }
                        // Check if status from BusTimeTable on specific date matches direction
                        String statusOnDate = getStatusFromBusTimeTable(bus.getId(), date);
                        logger.info("Status on Date is {}", statusOnDate);
                        boolean matchesDirection = statusOnDate != null && statusOnDate.equals(direction);
                        logger.info("Whether direction {} on date {} is {}", direction, date, matchesDirection);
                        boolean matchesRoute = bus.getSchedules().stream()
                                .anyMatch(schedule -> schedule.getBusStop().getName().equals(from)
                                        || schedule.getBusStop().getName().equals(to));
                        return matchesDirection && matchesRoute;
                    })
                    .collect(Collectors.toList());
            logger.info("Found {} bus schedules", buses.size());
            return buses;
        } catch (Exception e) {
            logger.error("Error occurred while searching bus schedules", e);
            throw e;
        }
    }

    private String findPreviousWeekStatus(int busid, LocalDate date) {
        LocalDate previousDate = date;
        logger.info("previousDate is {} for bus id {}" , previousDate,busid);
        while (true) {
            List<BusTimeTable> previousStatuses = busTimeTableRepository.findLatestStatusBeforeDate(busid, previousDate);
            logger.info("pre xnxxxuuuxxxx  get  0 {} ",previousStatuses.get(0).getStatus());


            if (!previousStatuses.isEmpty()) {
                return previousStatuses.get(0).getStatus();
            }
            previousDate = previousDate.minusWeeks(1);
        }
    }

//    private String getStatusFromBusTimeTable(int busId, LocalDate date) {
//        // Implement logic to fetch status from BusTimeTable for given busId and date
//        return busTimeTableRepository.findStatusByBusIdAndDate(busId, date);
//    }

    private String getStatusFromBusTimeTable(
            int busid,
           LocalDate date) {

        String status = busTimeTableRepository.findStatusByBusIdAndDate(busid, date);
logger.info("status  is {}" , status);
        if (status == null) {
            logger.info("status  is null so coming in" );
            status = findPreviousWeekStatus(busid, date);
        }

        return status;
    }


    // Inside BusMgtService

    @Autowired
    private BusTimeTableRepository busTimeTableRepository;

    @Transactional // Ensure the transactional context to avoid detached entity issues
    public void updateBusStatusFromTimeTable() {
        Iterable<BusMgt> allBuses = busmgtRepository.findAll();
        for (BusMgt bus : allBuses) {
            List<BusTimeTable> timeTables = busTimeTableRepository.findByBusId(bus.getId());
            //logger.info("Time table for a bus - {} is {}", bus ,timeTables);
            bus.updateStatusFromTimeTable(timeTables);
           // logger.info("Found {} bus changed to {}", bus ,bus.getStatus());
            busmgtRepository.save(bus);
        }
    }





}

