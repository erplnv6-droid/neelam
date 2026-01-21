package com.SCM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SCM.model.NewProductLaunching;

@Repository
public interface NewProductLaunchingRepo extends JpaRepository<NewProductLaunching, Integer> {

}
