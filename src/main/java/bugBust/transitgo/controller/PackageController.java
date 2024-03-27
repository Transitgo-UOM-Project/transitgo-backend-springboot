package bugBust.transitgo.controller;


import bugBust.transitgo.model.Package;
import bugBust.transitgo.repository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class PackageController {
    //Push data to database

    @Autowired
    private PackageRepository packageRepository;

    @PostMapping("/package")
    Package newPackage(@RequestBody Package newPackage){
        return packageRepository.save(newPackage);
    }
    @GetMapping("/packages")
    List<Package> getAllPackages(){
        return packageRepository.findAll();
    }
}
