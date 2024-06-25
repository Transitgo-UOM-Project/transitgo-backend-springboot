package bugBust.transitgo.repository;

import bugBust.transitgo.model.Package;
import bugBust.transitgo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PackageRepository  extends JpaRepository<Package,Long> {
  //Optional<Package> findByPackageID(Long PackageID);
  @Query( "SELECT u FROM Package u")
  List<Package> findAll();

}
