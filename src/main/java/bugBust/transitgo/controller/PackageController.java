package bugBust.transitgo.controller;

import bugBust.transitgo.exception.PackageNotFoundException;
import bugBust.transitgo.model.Package;
import bugBust.transitgo.repository.PackageRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class PackageController {
    //Push data to database

    @Autowired
    private PackageRepository packageRepository;

    @PostMapping("/package")
    Package newPackage(@Valid @RequestBody Package newPackage, BindingResult result){
        if(result.hasErrors()){
            throw new IllegalArgumentException("Invalid Input");
        }
        return packageRepository.save(newPackage);
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
    String deletePackage(@PathVariable Long PackageID){
        if(!packageRepository.existsById(PackageID)){
            throw new PackageNotFoundException(PackageID);
        }
        packageRepository.deleteById(PackageID);
        return "Package with id "+PackageID+" has been deleted successfully.";
    }



}
