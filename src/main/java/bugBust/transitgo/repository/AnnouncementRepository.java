package bugBust.transitgo.repository;

import bugBust.transitgo.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository <Announcement,Long>{

    List<Announcement> findAllByOrderByCreatedAtDesc();
}
