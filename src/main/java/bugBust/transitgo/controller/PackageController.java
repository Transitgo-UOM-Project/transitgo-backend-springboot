package bugBust.transitgo.controller;

import bugBust.transitgo.exception.PackageNotFoundException;
import bugBust.transitgo.exception.UnauthorizedException;
import bugBust.transitgo.model.*;
import bugBust.transitgo.model.Package;
import bugBust.transitgo.repository.ActivityLogRepository;
import bugBust.transitgo.repository.PackageRepository;
import bugBust.transitgo.services.ActivityLogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000" , "http://localhost:8081"})
public class PackageController {
    //Push data to database

    @Autowired
    private PackageRepository packageRepository;
    @Autowired
    private ActivityLogRepository activityLogRepository;
    @Autowired
    private ActivityLogService activityLogService;

    @PostMapping("/package")
    Package newPackage(@Valid @RequestBody Package newPackage, BindingResult result, Principal principal){

        if(result.hasErrors()){
            throw new IllegalArgumentException("Invalid Input");
        }
        newPackage.setCreatedBy(principal.getName());
        Package savedPackage = packageRepository.save(newPackage);

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = ((User) userDetails).getId();
        ActivityLog activityLog = new ActivityLog();
        activityLog.setUserId(userId);
        activityLog.setActivityType("Package");
        activityLog.setDescription(savedPackage.getStart() + " - " + savedPackage.getDestination());
        activityLog.setInfo(savedPackage.getEmployeeName() + " - " + savedPackage.getEmployeePhone());
        activityLog.setActivityId(savedPackage.getPackageID());
        activityLog.setPacStatus(savedPackage.getStatus());
        activityLog.setDateTime(LocalDateTime.now());
        activityLogRepository.save(activityLog);

        return savedPackage;
    }
    @GetMapping("/packages")
    List<Package> getAllPackages(){
        return packageRepository.findAll();
    }

    @GetMapping("/package/{PackageID}")
    Package getPackageById(@PathVariable Long PackageID){
        return packageRepository.findById(PackageID)
                .orElseThrow(()->new PackageNotFoundException(PackageID));
    }

    //Edite Status
    @PutMapping("/package/{PackageID}")
    ResponseEntity<Package> updatePackage(@RequestBody Package newPackage, @PathVariable Long PackageID){
//        if(result.hasErrors()){
//            throw new IllegalArgumentException("Invalid Input");
//        }
        Package updatedPackage =  packageRepository.findById(PackageID)
                .map(aPackage ->{
                    aPackage.setStatus(newPackage.getStatus());
                    return packageRepository.save(aPackage);
                }).orElseThrow(()->new PackageNotFoundException(PackageID));
        activityLogRepository.findByActivityId(PackageID).ifPresent(activityLog -> {
            activityLog.setPacStatus(newPackage.getStatus());
            activityLogRepository.save(activityLog);
        });

       return ResponseEntity.ok(updatedPackage);
    }

    //Delete data
    @DeleteMapping("/package/{PackageID}")
    String deletePackage(@PathVariable Long PackageID, Principal principal){
//        if(!packageRepository.existsById(PackageID)){
//            throw new PackageNotFoundException(PackageID);
//        }

        Package pac = packageRepository.findById(PackageID)
                .orElseThrow(() -> new PackageNotFoundException(PackageID));
//        if (!isAuthorizedToModify(principal, pac)){
//            throw new UnauthorizedException("You are not authorized to update this review");
//        }
        packageRepository.deleteById(PackageID);
        return "Package with id "+PackageID+" has been deleted successfully.";
    }

    private boolean isAuthorizedToModify(Principal principal, Package pac){
        String username = principal.getName();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return pac.getCreatedBy().equals(username) || userDetails.getAuthorities().contains(new SimpleGrantedAuthority("Roleadmin"));
    }



}
