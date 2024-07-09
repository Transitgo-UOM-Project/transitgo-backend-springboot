//BusStatusUpdateService.java
package bugBust.transitgo.services;

import bugBust.transitgo.model.BusMgt;
import bugBust.transitgo.repository.BusMgtRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@Service
public class BusStatusUpdateService {

    private final BusMgtRepository busmgtRepository;
    private final ScheduleService scheduleService;

    private static final Logger logger = LoggerFactory.getLogger(BusStatusUpdateService.class);

    public BusStatusUpdateService(BusMgtRepository busmgtRepository, ScheduleService scheduleService) {
        this.busmgtRepository = busmgtRepository;
        this.scheduleService = scheduleService;
    }

    @Scheduled(fixedRate = 15000) // Example rate, update as needed
    @Transactional
    public void updateBusStatusFromTimeTable() {
        logger.info("Executing scheduled task to update bus statuses.");
        Iterable<BusMgt> allBuses = busmgtRepository.findAll();
        for (BusMgt bus : allBuses) {
            updateBusStatus(bus, "up");
            updateBusStatus(bus, "down");
            busmgtRepository.save(bus);
        }
    }

    private void updateBusStatus(BusMgt bus, String direction) {
        LocalTime now = LocalTime.now();
        LocalTime[] times = scheduleService.getJourneyStartAndEndTime(bus.getId(), direction);

        if (times == null) {
            logger.info("No schedules found for bus {} in direction {}", bus.getId(), direction);
            return;
        }

        LocalTime startTime = times[0];
        LocalTime endTime = times[1];

        if (isWithinTimeRange(now, startTime, endTime)) {
            logger.info("Setting status for bus {} to {}", bus.getId(), direction);
            bus.setStatus(direction);
        } else if (bus.getStatus().equals(direction)) {
            logger.info("Bus {} is no longer within time range, setting status to 'off'", bus.getId());
            bus.setStatus("off");
        }
    }

    private boolean isWithinTimeRange(LocalTime now, LocalTime startTime, LocalTime endTime) {
        return (now.isAfter(startTime) || now.equals(startTime)) && (now.isBefore(endTime) || now.equals(endTime));
    }
}
