//BusMgtRepository.java
package bugBust.transitgo.repository;

import bugBust.transitgo.model.BusMgt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusMgtRepository extends JpaRepository<BusMgt,Integer> {
  BusMgt getByid(int Id);
   Optional<BusMgt> findById(int id);
}
