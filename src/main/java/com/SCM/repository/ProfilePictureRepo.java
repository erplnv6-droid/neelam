package com.SCM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SCM.model.ProfilePicture;

@Repository
public interface ProfilePictureRepo extends JpaRepository<ProfilePicture, Integer> {

	List<ProfilePicture> findBystaffId(int staffid);

	List<ProfilePicture> findByretailerId(int retailerid);

	List<ProfilePicture> findBydistributorId(int distributorid);
	
	List<ProfilePicture> findBysupplierId(int supplierid);

	
	
	boolean existsBystaffId(int staffid);

	
	
	ProfilePicture findByStaffId(int staffid);

	ProfilePicture findByRetailerId(int retailerid);

	ProfilePicture findByDistributorId(int distributorid);

	ProfilePicture findByStaffIdAndRetailerIdAndDistributorIdAndSupplierId(int staffid, int retailerid, int distributorid,int supplierid);

}
