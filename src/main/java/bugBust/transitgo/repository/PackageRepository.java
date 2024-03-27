package bugBust.transitgo.repository;

import bugBust.transitgo.model.Package;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRepository  extends JpaRepository<Package,Long> {

}
