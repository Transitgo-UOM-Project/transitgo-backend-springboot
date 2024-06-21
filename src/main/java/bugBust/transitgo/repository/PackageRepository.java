package bugBust.transitgo.repository;

import bugBust.transitgo.model.Package;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PackageRepository  extends JpaRepository<Package,Long> {
  //Optional<Package> findByPackageID(Long PackageID);


}
