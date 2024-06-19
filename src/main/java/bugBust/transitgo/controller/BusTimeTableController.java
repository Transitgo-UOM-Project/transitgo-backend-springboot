package bugBust.transitgo.controller;

import bugBust.transitgo.model.BusTimeTable;
import bugBust.transitgo.repository.BusTimeTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class BusTimeTableController {
    @Autowired
    private BusTimeTableRepository bustimetableRepository;

    @PostMapping("/bus/{busid}/bustimetable")
    public ResponseEntity<List<BusTimeTable>> saveBusTimeTable(
            @PathVariable int busid,
            @RequestBody List<BusTimeTable> busTimeTables) {



        busTimeTables.forEach(status -> status.setBusId(busid));
        bustimetableRepository.saveAll(busTimeTables);
        return ResponseEntity.ok(busTimeTables);
    }

    @GetMapping("/bus/{busid}/bustimetable")
    public ResponseEntity<List<BusTimeTable>> getBusTimeTable(
            @PathVariable int busid,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {

        List<BusTimeTable> statuses = bustimetableRepository.findByBusIdAndDateBetween(busid, startDate, endDate);
        return ResponseEntity.ok(statuses);
    }


}
