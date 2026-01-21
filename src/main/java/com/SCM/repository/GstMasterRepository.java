package com.SCM.repository;


import com.SCM.model.GstMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GstMasterRepository extends JpaRepository<GstMaster, Integer> {

    List<GstMaster> findByType(int type);

    Optional<GstMaster> findByPerAndType(float per, int type);

}
