package com.SCM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SCM.model.Country;

@Repository
public interface CountryRepo extends JpaRepository<Country, Integer> {

}
