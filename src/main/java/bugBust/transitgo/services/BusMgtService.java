//BusMgtService.java
package bugBust.transitgo.services;

import bugBust.transitgo.model.BusMgt;
import bugBust.transitgo.repository.BusMgtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusMgtService {
    @Autowired
    private BusMgtRepository busmgtRepository;

    public BusMgt saveOrUpdateABus(BusMgt bus) {
        return busmgtRepository.save(bus);
    }

    public Iterable<BusMgt> findAll() {
        return busmgtRepository.findAll();
    }

    public BusMgt findBusById(int busid) {
        return busmgtRepository.findById(busid).orElse(null);
    }

    public List<BusMgt> searchBusSchedules(String from, String to) {
        return busmgtRepository.findAll().stream()
                .filter(bus -> bus.getSchedules().stream()
                        .anyMatch(schedule -> schedule.getBusStop().getName().equals(from)
                                || schedule.getBusStop().getName().equals(to)))
                .collect(Collectors.toList());
    }



}

