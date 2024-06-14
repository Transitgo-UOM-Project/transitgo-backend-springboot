package bugBust.transitgo.scheduledTask;

import bugBust.transitgo.model.BusMgt;
import bugBust.transitgo.repository.BusMgtRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Component
public class BusStatusUpdateTask {

    private  BusMgtRepository busmgtRepository;

    @Autowired
    public BusStatusUpdateTask(BusMgtRepository busMgtRepository) {
        this.busmgtRepository = busMgtRepository;
    }

    @Scheduled(fixedRate = 30000) // Run every 30 seconds
    public void updateBusStatuses() {
        List<BusMgt> buses = busmgtRepository.findAll();
        for (BusMgt bus : buses) {
            // Example logic: Toggle between "up" and "down" statuses every 30 seconds
            if ("up".equals(bus.getStatus())) {
                bus.setStatus("down");
            } else if ("down".equals(bus.getStatus())) {
                bus.setStatus("up");
            }
            busmgtRepository.save(bus);
        }
    }
}