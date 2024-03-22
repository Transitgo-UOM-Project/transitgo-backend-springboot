package bugBust.transitgo.services;

import bugBust.transitgo.model.BusMgt;
import bugBust.transitgo.repository.BusMgtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusMgtService {
    @Autowired
    private BusMgtRepository busmgtRepository;

    public BusMgt saveOrUpdateABus(BusMgt busmgt) {
        return busmgtRepository.save(busmgt);
    }

    public BusMgt findBusById(int Id) {
        return busmgtRepository.getById(Id);
    }

    public Iterable<BusMgt> findAll() {
        return busmgtRepository.findAll();
    }
}