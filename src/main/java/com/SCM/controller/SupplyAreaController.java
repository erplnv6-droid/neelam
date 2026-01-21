package com.SCM.controller;

import com.SCM.model.SupplyArea;
import com.SCM.service.SupplyAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/supplyarea")
public class SupplyAreaController {

    @Autowired
    private SupplyAreaService supplyAreaService;

    @GetMapping("/getAll")
 ///   @PreAuthorize("hasRole('ADMIN')")
    public List<SupplyArea> getAllSupplyArea() {
        return supplyAreaService.getAllSupplyArea();
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SupplyArea> get(@PathVariable Integer id) {
        try {
            SupplyArea supplyArea = supplyAreaService.getSupplyAreaById(id);
            return new ResponseEntity<SupplyArea>(supplyArea, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<SupplyArea>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
 //   @PreAuthorize("hasRole('ADMIN')")
    public SupplyArea create(@RequestBody SupplyArea supplyArea) {
        System.out.println(supplyArea);
        return supplyAreaService.saveSupplyArea(supplyArea);
    }

    @DeleteMapping("/delete/{id}")
  //  @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable int id) {
        supplyAreaService.deleteSupplyArea(id);
        return new ResponseEntity<String>("Your Brand is SuccessFully Deleted!!", HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
 //   @PreAuthorize("hasRole('ADMIN')")
    public SupplyArea update(@PathVariable("id")int id, @RequestBody SupplyArea supplyArea){
        return supplyAreaService.updateSupplyArea(supplyArea,id);
    }

}
