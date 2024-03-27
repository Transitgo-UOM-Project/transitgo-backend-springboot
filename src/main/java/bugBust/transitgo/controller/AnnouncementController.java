package bugBust.transitgo.controller;

import java.util.logging.Logger;

import bugBust.transitgo.exception.AnnouncementNotFoundException;
import bugBust.transitgo.model.Announcement;
import bugBust.transitgo.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class AnnouncementController {
    @Autowired
    private AnnouncementRepository announcementRepository;

    @PostMapping("/announcement")
    Announcement newAnnouncement(@RequestBody Announcement newAnnouncement){
        return announcementRepository.save(newAnnouncement);
    }

    @GetMapping("/announcements")
    List<Announcement> getAllAnnouncements(){
        return announcementRepository.findAllByOrderByCreatedAtDesc();
    }

    @GetMapping("/announcement/{id}")
    Announcement getAnnouncementById(@PathVariable Long id){
        return announcementRepository.findById(id)
                .orElseThrow(()->new AnnouncementNotFoundException(id));
    }

    @DeleteMapping("announcement/{id}")
    String deleteAnnouncement(@PathVariable Long id){
        if(!announcementRepository.existsById(id)){
            throw  new AnnouncementNotFoundException(id);
        }
        announcementRepository.deleteById(id);
        return "announcement deleted successfully.";
    }

    @PutMapping("/announcement/{id}")
    Announcement updateAnnouncement(@RequestBody Announcement newAnnouncement, @PathVariable Long id){
        return  announcementRepository.findById(id)
                .map(announcement -> {
                    announcement.setDetails(newAnnouncement.getDetails());
                    return announcementRepository.save(announcement);
                }).orElseThrow(()-> new AnnouncementNotFoundException(id));
    }
}
