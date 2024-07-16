//BusStatusUpdateService.java
package bugBust.transitgo.services;

import bugBust.transitgo.model.BusMgt;
import bugBust.transitgo.model.BusTimeTable;
import bugBust.transitgo.repository.BusMgtRepository;
import bugBust.transitgo.repository.BusTimeTableRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class BusStatusUpdateService {

    private final BusMgtRepository busmgtRepository;
    private final ScheduleService scheduleService;

    private final BusTimeTableRepository busTimeTableRepository;
    private final BusMgtService busMgtService;
    private static final Logger logger = LoggerFactory.getLogger(BusStatusUpdateService.class);

    public BusStatusUpdateService(BusMgtRepository busmgtRepository, ScheduleService scheduleService, BusTimeTableRepository busTimeTableRepository, BusMgtService busMgtService) {
        this.busmgtRepository = busmgtRepository;
        this.scheduleService = scheduleService;
        this.busTimeTableRepository = busTimeTableRepository;
        this.busMgtService = busMgtService;
    }

    @Scheduled(fixedRate = 15000)  // 15 seconds
    @Transactional
    public void updateBusStatusFromTimeTable() {
        logger.info("Executing scheduled task to update bus statuses.");
        Iterable<BusMgt> allBuses = busmgtRepository.findAll();
        for (BusMgt bus : allBuses) {
            if (bus.getNoOfJourneysPerDay() == 1)  {
                updateSingleJourneyBusStatus(bus);
            }
            else{
            updateBusStatus(bus, "up");
            updateBusStatus(bus, "down");
            busmgtRepository.save(bus);
        }}
    }
    private void updateSingleJourneyBusStatus(BusMgt bus) {
        LocalDate today = LocalDate.now();
        String statusUp = busMgtService.getStatusFromBusTimeTable(bus.getId(), today, "up");
        String statusDown = busMgtService.getStatusFromBusTimeTable(bus.getId(), today, "down");

        if (statusUp != null && statusDown != null) {
            // Assuming you want to prioritize one over the other, or combine them in some way
            bus.setStatus("off");
        } else if (statusUp != null) {
            bus.setStatus(statusUp);
        } else if (statusDown != null) {
            bus.setStatus(statusDown);
        } else {
            bus.setStatus("off");
        }
    }
    private void updateBusStatus(BusMgt bus, String direction) {
        LocalTime now = LocalTime.now();
        LocalTime[] times = scheduleService.getJourneyStartAndEndTime(bus.getId(), direction);

        if (times == null) {
          //logger.info("No schedules found for bus {} in direction {}", bus.getId(), direction);
            return;
        }

        LocalTime startTime = times[0];
        LocalTime endTime = times[1];

        if (isWithinTimeRange(now, startTime, endTime)) {
            logger.info("Setting status for bus {} to {}", bus.getId(), direction);
            bus.setStatus(direction);
        } else if (bus.getStatus().equals(direction)) {
            if (isOutsideTimeRangeByMoreThan30Minutes(now, startTime, endTime)) {
               logger.info("Bus {} is no longer within time range of 30 minutes from start and end time, setting status to 'off'", bus.getId());
                bus.setStatus("off");
            }
            else {
                logger.info("Bus {} is within time range of 30 minutes from start and end time , setting status to {}", bus.getId(),direction);
                bus.setStatus(direction);
            }
        }
    }

    private boolean isWithinTimeRange(LocalTime now, LocalTime startTime, LocalTime endTime) {
        return (now.isAfter(startTime) || now.equals(startTime)) && (now.isBefore(endTime) || now.equals(endTime));
    }

    private boolean isOutsideTimeRangeByMoreThan30Minutes(LocalTime now, LocalTime startTime, LocalTime endTime) {
        return now.isAfter(endTime.plusMinutes(30)) || now.isBefore(startTime.minusMinutes(30));
    }

}
