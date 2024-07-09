package bugBust.transitgo.scheduledTask;

import bugBust.transitgo.services.BusMgtService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BusStatusUpdateTask {

    private final BusMgtService busMgtService;

    public BusStatusUpdateTask(BusMgtService busMgtService) {
        this.busMgtService = busMgtService;
    }

//    @Scheduled(fixedRate = 15000) // Runs every minute, adjust as needed
//    public void updateBusStatus() {
//        busMgtService.updateBusStatusFromTimeTable();
//    }
}
