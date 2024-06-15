//BusMgtService.java
package bugBust.transitgo.services;

import bugBust.transitgo.model.BusMgt;
import bugBust.transitgo.repository.BusMgtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class BusMgtService {
    @Autowired
    private BusMgtRepository busmgtRepository;

    private static final Logger logger = LoggerFactory.getLogger(BusMgtService.class);

    public BusMgt saveOrUpdateABus(BusMgt bus) {
        return busmgtRepository.save(bus);
    }

    public Iterable<BusMgt> findAll() {
        return busmgtRepository.findAll();
    }

    public BusMgt findBusById(int busid) {
        return busmgtRepository.findById(busid).orElse(null);
    }

    public List<BusMgt> searchBusSchedules(String from, String to, String direction) {
        logger.info("Searching bus schedules from {} to {} with direction {}", from, to, direction);
        try {

            List<BusMgt> buses = busmgtRepository.findAll().stream()
                    .filter(bus -> {
                        if (bus.getStatus().equals("off")) {
                            return false;
                        }
                        boolean matchesDirection = bus.getStatus().equals(direction);
                        logger.info("whether direction {}", matchesDirection);
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




}

