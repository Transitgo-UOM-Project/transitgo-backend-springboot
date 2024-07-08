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

    @Autowired
    private BusTimeTableRepository busTimeTableRepository;

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
                        logger.info("For Bus {}", bus.getRegNo());
                        // Check if status from BusTimeTable on specific date matches direction
                        String statusOnDate = getStatusFromBusTimeTable(bus.getId(), date, direction);
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

    private String getStatusFromBusTimeTable(int busid, LocalDate date, String direction) {
        List<String> statuses = busTimeTableRepository.findStatusByBusIdAndDate(busid, date);

        if (statuses.isEmpty()) {
            logger.info("No status found for bus {} on date {}", busid, date);
            LocalDate previousDate = date.minusWeeks(1);
            return getStatusFromPreviousWeek(busid, previousDate, direction);
        } else if (statuses.size() > 1) {
            logger.warn("Multiple statuses found for bus {} on date {}", busid, date);
            // Filter statuses by direction
            Optional<String> matchingStatus = statuses.stream()
                    .filter(status -> status.equals(direction))
                    .findFirst();

            return matchingStatus.orElse(null); // Return matching status or null if not found
        } else {
            // Only one status found, return it if it matches the direction
            return statuses.get(0).equals(direction) ? statuses.get(0) : null;
        }
    }

    private String getStatusFromPreviousWeek(int busid, LocalDate date, String direction) {
        logger.info("Fetching status from previous week for bus {} on date {}", busid, date);
        List<String> statuses = busTimeTableRepository.findStatusByBusIdAndDate(busid, date);

        if (statuses.isEmpty()) {
            logger.info("No status found for bus {} on previous week date {}", busid, date);
            // Recursively check previous weeks until a status is found or limit is reached
            if (!date.isBefore(LocalDate.now().minusWeeks(4))) { // Limit to 4 weeks in the past
                return getStatusFromPreviousWeek(busid, date.minusWeeks(1), direction);
            } else {
                logger.warn("Reached limit of 4 weeks in the past for bus {} on date {}", busid, date);
                return null; // Or handle accordingly based on your application's logic
            }
        } else {
            // Filter statuses by direction
            Optional<String> matchingStatus = statuses.stream()
                    .filter(status -> status.equals(direction))
                    .findFirst();

            return matchingStatus.orElse(null); // Return matching status or null if not found
        }
    }

}










