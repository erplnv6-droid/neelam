package com.SCM.controller;

import com.SCM.dto.OpeningStockResponse;
import com.SCM.model.OpeningStock;
import com.SCM.model.Warehouse;
import com.SCM.repository.OpeningStockRepository;
import com.SCM.repository.WarehouseRepository;
import com.SCM.service.OpeningStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/openingstock")
public class OpeningStockController {


    @Autowired
    private OpeningStockService openingStockService;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private OpeningStockRepository openingStockRepository;



    @PostMapping("/add")
    public OpeningStock add(@RequestBody OpeningStock openingStock) {

        System.out.println(openingStock);
       return openingStockService.saveOpeningStock(openingStock);

    }
   
    @GetMapping("/getAll")
       public List<OpeningStock> getAllOpeningStockwithimg(){

           return openingStockRepository.getAllOsImg();

       }

    @GetMapping("/getById/{id}")
    public OpeningStock findOpeningStockById(@PathVariable int id){

        return openingStockService.getOpeningStockById(id);
    }

    @PutMapping("/update/{id}")
    public OpeningStock updateOpeningStock(@PathVariable int id, @RequestBody OpeningStock openingStock) {

        return openingStockService.updateOpeningStock(openingStock, id);

    }

    @DeleteMapping("/delete/{id}")
    public String deleteOpeningStock(@PathVariable int id){

        return openingStockService.deleteOpeningStock(id);

    }




    @GetMapping("/getAllOpening")
    public List<OpeningStockResponse> getAllOpeningStockWithProdWare(){

        return openingStockRepository.findAllOpeningStockWithProdWar();

    }



    @GetMapping("/getByCheckId/{id}/{ids}")
//    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Optional<Warehouse> findOpeningStockWareById(@PathVariable("id") int id, @PathVariable("ids") int ids){

        if(openingStockRepository.findAllByWidAndPid(id,ids).isPresent()){
        	System.out.println("opening stock is null");
            return Optional.empty();
        }else {
            return warehouseRepository.findById(id);
        }
    }
    
    
    
    @GetMapping("/page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
  	public ResponseEntity<?> IndexDistributor(@PathVariable("pageno") int pageno,
  			                                  @PathVariable("pagesize") int pagesize,
  			                                  @PathVariable("sortby") String sortby,
  			                                  @PathVariable("field") String field,
  			                                  @PathVariable("search")String search) 
  	{
      	
      	if(!search.equals(" "))
  		{
  			return new ResponseEntity<>(openingStockService.SearchOpeningStock(pageno, pagesize,search),HttpStatus.OK) ; 
  		}
      	else if("asc".equals(sortby))
  		{ 
  			return new ResponseEntity<>(openingStockService.IndexOpeningStockAsc(pageno,pagesize,field), HttpStatus.OK);
  		}
  		else if("desc".equals(sortby))
  		{
  			return new ResponseEntity<>(openingStockService.IndexOpeningStockDesc(pageno,pagesize,field), HttpStatus.OK);
  		}
  		else {			
  			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
  		}
  	}
    
}
