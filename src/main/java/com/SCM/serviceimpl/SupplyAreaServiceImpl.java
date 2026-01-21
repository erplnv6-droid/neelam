package com.SCM.serviceimpl;

import com.SCM.model.SupplyArea;
import com.SCM.repository.SupplyAreaRepo;
import com.SCM.service.SupplyAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class SupplyAreaServiceImpl implements SupplyAreaService {
    @Autowired
    private SupplyAreaRepo supplyAreaRepo;

    @Override
    public SupplyArea saveSupplyArea(SupplyArea supplyArea) {
    	
        return supplyAreaRepo.save(supplyArea);
    }

    @Override
    public List<SupplyArea> getAllSupplyArea() {
        return supplyAreaRepo.findAll();
    }

    @Override
    public SupplyArea getSupplyAreaById(int id) {
        return supplyAreaRepo.findById(id).orElse(null);
    }

    @Override
    public String deleteSupplyArea(int id) {
        supplyAreaRepo.deleteById(id);

        return "supply Removed !!"+ id;
    }

    @Override
    public SupplyArea updateSupplyArea(SupplyArea supplyArea, int id) {
        SupplyArea existingSupplyArea = supplyAreaRepo.findById(id).orElse(null);
        existingSupplyArea.setAreaName(supplyArea.getAreaName());
        existingSupplyArea.setPinCode(supplyArea.getPinCode());
        existingSupplyArea.setActiveStatus(supplyArea.isActiveStatus());
        return supplyAreaRepo.save(existingSupplyArea);
    }
}
