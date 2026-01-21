package com.SCM.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SCM.dto.AssetReqFormDto;
import com.SCM.service.AssetReqFormService;

@RestController
@RequestMapping("/api/assetreqform/")
@CrossOrigin(origins = "*")
public class AssetReqFormController {
	
	
	@Autowired
	private AssetReqFormService formService;
	
	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody AssetReqFormDto formDto){
		AssetReqFormDto save = formService.save(formDto);
		return new ResponseEntity<>(save,HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<AssetReqFormDto>> getAll(){
		List<AssetReqFormDto> all = formService.all();
		return new ResponseEntity<List<AssetReqFormDto>>(all,HttpStatus.OK);
	}
	
	@GetMapping("/byid/{id}")
	public ResponseEntity<?> getById(@PathVariable long id){
		Optional<AssetReqFormDto> byId = formService.getById(id);
		return new ResponseEntity<>(byId,HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateAssetreqForm(@PathVariable long id,@RequestBody AssetReqFormDto dto){
		String updateAsset = formService.updateAsset(dto, id);
		return new ResponseEntity<>(updateAsset,HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteAssetReq(@PathVariable long id){
		String deleteAsset = formService.deleteAsset(id);
		return new ResponseEntity<>(deleteAsset,HttpStatus.OK);
	}
	
	
	@GetMapping("page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> IndexAssetReqForm(@PathVariable("pageno") int pageno,
			                                  @PathVariable("pagesize") int pagesize,
			                                  @PathVariable("sortby") String sortby,
			                                  @PathVariable("field") String field,
			                                  @PathVariable("search") String search) {
		if (!search.equals(" ")) {
			
			return new ResponseEntity<>(formService.SearchAssetReqForm(pageno, pagesize, search), HttpStatus.OK);
			
		} else if ("asc".equals(sortby)) {
			
			return new ResponseEntity<>(formService.IndexAssetReqFormAsc(pageno, pagesize, field), HttpStatus.OK);
			
		} else if ("desc".equals(sortby)) {
			
			return new ResponseEntity<>(formService.IndexAssetReqFormDesc(pageno, pagesize, field),HttpStatus.OK);
			
		} else {
			
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}

	
}
