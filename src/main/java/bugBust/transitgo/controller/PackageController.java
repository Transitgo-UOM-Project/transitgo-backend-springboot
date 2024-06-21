package bugBust.transitgo.controller;

import bugBust.transitgo.exception.ItemNotFoundException;
import bugBust.transitgo.exception.PackageNotFoundException;
import bugBust.transitgo.exception.UnauthorizedException;
import bugBust.transitgo.model.LostItems;
import bugBust.transitgo.model.Package;
import bugBust.transitgo.model.Role;
import bugBust.transitgo.model.User;
import bugBust.transitgo.repository.ActivityLogRepository;
import bugBust.transitgo.repository.PackageRepository;
import bugBust.transitgo.services.ActivityLogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
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
        String desc = savedPackage.getStart() + " - " + savedPackage.getDestination();
        activityLogService.logActivity(userId, "Package", desc , savedPackage.getPackageID());

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
    Package updatePackage(@Valid @RequestBody Package newPackage, BindingResult result, @PathVariable Long PackageID){
        if(result.hasErrors()){
            throw new IllegalArgumentException("Invalid Input");
        }
        return packageRepository.findById(PackageID)
                .map(aPackage ->{
                    aPackage.setReceiverName(newPackage.getReceiverName());
                    aPackage.setPayment(newPackage.getPayment());
                    aPackage.setStatus(newPackage.getStatus());
                    return packageRepository.save(aPackage);
                }).orElseThrow(()->new PackageNotFoundException(PackageID));

    }

    //Delete data
    @DeleteMapping("/package/{PackageID}")
    String deletePackage(@PathVariable Long PackageID, Principal principal){
//        if(!packageRepository.existsById(PackageID)){
//            throw new PackageNotFoundException(PackageID);
//        }

        Package pac = packageRepository.findById(PackageID)
                .orElseThrow(() -> new PackageNotFoundException(PackageID));
        if (!isAuthorizedToModify(principal, pac)){
            throw new UnauthorizedException();
        }


        activityLogService.deleteActivityByActivityId(PackageID);
        packageRepository.deleteById(PackageID);
        return "Package with id "+PackageID+" has been deleted successfully.";
    }

    private boolean isAuthorizedToModify(Principal principal, Package pac){
        String username = principal.getName();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return pac.getCreatedBy().equals(username) || userDetails.getAuthorities().contains(new SimpleGrantedAuthority(Role.admin.name()));
    }

}
