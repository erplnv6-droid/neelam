package com.SCM.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.SCM.IndexDto.IndexProjectionEntry;
import com.SCM.model.ProjectionEntry;
import com.SCM.projectionDto.ProjectionReportDto;

public interface ProjectionEntryRepository extends JpaRepository<ProjectionEntry, Long>{


	@Query(value = "select pe.id ,pe.projection_date as pedate ,pei.productid as productid,p.product_type as producttype,p.uom_secondary as uomsecondary,p.ean_code as eancode,p.product_name as productname,pei.qty as pcsqty,pei.kgqty as kgqty from projectionentry pe \r\n"
			+ "left join projectionentryitems pei \r\n" + "on pe.id=pei.projectionentryitem \r\n"
			+ "left join product p on p.id=pei.productid\r\n"
			+ "where pe.projection_date between :fromdate AND :todate and pe.groupn1= :g1 ", nativeQuery = true)
	List<ProjectionReportDto> getAllNewProjectionEntryWithGroupn1(String g1, String fromdate, String todate);

	@Query(value = "select pe.id ,pe.projection_date as pedate ,pei.productid as productid,p.product_type as producttype,p.uom_secondary as uomsecondary,p.ean_code as eancode,p.product_name as productname,pei.qty as pcsqty,pei.kgqty as kgqty from projectionentry pe \r\n"
			+ "left join projectionentryitems pei \r\n" + "on pe.id=pei.projectionentryitem \r\n"
			+ "left join product p on p.id=pei.productid\r\n"
			+ "where pe.projection_date between :fromdate AND :todate and pe.groupn2= :g2 ", nativeQuery = true)
	List<ProjectionReportDto> getAllNewProjectionEntryWithGroupn2(String g2, String fromdate, String todate);

	@Query(value = "select pe.id ,pe.projection_date as pedate ,pei.productid as productid,p.product_type as producttype,p.uom_secondary as uomsecondary,p.ean_code as eancode,p.product_name as productname,pei.qty as pcsqty,pei.kgqty as kgqty from projectionentry pe \r\n"
			+ "left join projectionentryitems pei \r\n" + "on pe.id=pei.projectionentryitem \r\n"
			+ "left join product p on p.id=pei.productid\r\n"
			+ "where pe.projection_date between :fromdate AND :todate and pe.groupn3= :g3 ", nativeQuery = true)
	List<ProjectionReportDto> getAllNewProjectionEntryWithGroupn3(String g3, String fromdate, String todate);

	@Query(value = "select pe.id ,pe.projection_date as pedate ,pei.productid as productid,p.product_type as producttype,p.uom_secondary as uomsecondary,p.ean_code as eancode,p.product_name as productname,pei.qty as pcsqty,pei.kgqty as kgqty from projectionentry pe \r\n"
			+ "left join projectionentryitems pei \r\n" + "on pe.id=pei.projectionentryitem \r\n"
			+ "left join product p on p.id=pei.productid\r\n"
			+ "where pe.projection_date between :fromdate AND :todate and pe.groupn4= :g4 ", nativeQuery = true)
	List<ProjectionReportDto> getAllNewProjectionEntryWithGroupn4(String g4, String fromdate, String todate);

	@Query(value = "select pe.id ,pe.projection_date as pedate ,pei.productid as productid,p.ean_code as eancode,p.product_type as producttype,p.uom_secondary as uomsecondary,p.product_name as productname,pei.qty as pcsqty,pei.kgqty as kgqty from projectionentry pe \r\n"
			+ "left join projectionentryitems pei \r\n" + "on pe.id=pei.projectionentryitem \r\n"
			+ "left join product p on p.id=pei.productid\r\n"
			+ "where pe.projection_date between :fromdate AND :todate and pe.groupn5= :g5 ", nativeQuery = true)
	List<ProjectionReportDto> getAllNewProjectionEntryWithGroupn5(String g5, String fromdate, String todate);

//	=======================================================

	@Query(value = "select pe.id ,pe.projection_date as pedate, pei.productid as productid, p.product_name as productname,p.ean_code as eancode,p.product_type as producttype,p.uom_secondary as uomsecondary, pei.qty as pcsqty, pei.kgqty as kgqty "
			+ "from projectionentry pe " + "left join projectionentryitems pei on pe.id = pei.projectionentryitem "
			+ "left join product p on p.id = pei.productid " + "where pe.projection_date between :fromdate AND :todate "
			+ "and pe.groupn1 = :g1 and pe.groupn2 = :g2", nativeQuery = true)
	List<ProjectionReportDto> getAllNewProjectionEntryWithGroupn1And2(String g1, String g2, String fromdate,
			String todate);

	@Query(value = "select pe.id ,pe.projection_date as pedate, pei.productid as productid, p.product_name as productname,p.ean_code as eancode, p.product_type as producttype,p.uom_secondary as uomsecondary,pei.qty as pcsqty, pei.kgqty as kgqty "
			+ "from projectionentry pe " + "left join projectionentryitems pei on pe.id = pei.projectionentryitem "
			+ "left join product p on p.id = pei.productid " + "where pe.projection_date between :fromdate AND :todate "
			+ "and pe.groupn1 = :g1 and pe.groupn2 = :g2 and pe.groupn3 = :g3", nativeQuery = true)
	List<ProjectionReportDto> getAllNewProjectionEntryWithGroupn1AndGroupn2AndGroupn3(String g1, String g2, String g3,
			String fromdate, String todate);

	@Query(value = "select pe.id ,pe.projection_date as pedate, pei.productid as productid, p.product_name as productname,p.ean_code as eancode,p.product_type as producttype,p.uom_secondary as uomsecondary, pei.qty as pcsqty, pei.kgqty as kgqty "
			+ "from projectionentry pe " + "left join projectionentryitems pei on pe.id = pei.projectionentryitem "
			+ "left join product p on p.id = pei.productid " + "where pe.projection_date between :fromdate AND :todate "
			+ "and pe.groupn1 = :g1 and pe.groupn2 = :g2 and pe.groupn3 = :g3 and pe.groupn4 = :g4", nativeQuery = true)
	List<ProjectionReportDto> getAllNewProjectionEntryWithGroupn1AndGroupn2AndGroupn3AndGroupn4(String g1, String g2,
			String g3, String g4, String fromdate, String todate);

	@Query(value = "select pe.id ,pe.projection_date as pedate, pei.productid as productid, p.product_name as productname,p.ean_code as eancode,p.product_type as producttype,p.uom_secondary as uomsecondary, pei.qty as pcsqty, pei.kgqty as kgqty "
			+ "from projectionentry pe " + "left join projectionentryitems pei on pe.id = pei.projectionentryitem "
			+ "left join product p on p.id = pei.productid " + "where pe.projection_date between :fromdate AND :todate "
			+ "and pe.groupn1 = :g1 and pe.groupn2 = :g2 and pe.groupn3 = :g3 and pe.groupn4 = :g4 and pe.groupn5 = :g5", nativeQuery = true)
	List<ProjectionReportDto> getAllNewProjectionEntryWithGroupn1AndGroupn2AndGroupn3AndGroupn4AndGroupn5(String g1,
			String g2, String g3, String g4, String g5, String fromdate, String todate);

//	   ================  2
	
	// Method for g2, g3
	@Query(value = "select pe.id ,pe.projection_date as pedate, pei.productid as productid, p.product_name as productname,p.ean_code as eancode,p.product_type as producttype,p.uom_secondary as uomsecondary, pei.qty as pcsqty, pei.kgqty as kgqty "
			+ "from projectionentry pe " + "left join projectionentryitems pei on pe.id = pei.projectionentryitem "
			+ "left join product p on p.id = pei.productid " + "where pe.projection_date between :fromdate AND :todate "
			+ "and pe.groupn2 = :g2 and pe.groupn3 = :g3", nativeQuery = true)
	List<ProjectionReportDto> getAllNewProjectionEntryWithGroupn2AndGroupn3(String g2, String g3, String fromdate,
			String todate);

	// Method for g2, g3, g4
	@Query(value = "select pe.id ,pe.projection_date as pedate, pei.productid as productid, p.product_name as productname,p.ean_code as eancode,p.product_type as producttype,p.uom_secondary as uomsecondary, pei.qty as pcsqty, pei.kgqty as kgqty "
			+ "from projectionentry pe " + "left join projectionentryitems pei on pe.id = pei.projectionentryitem "
			+ "left join product p on p.id = pei.productid " + "where pe.projection_date between :fromdate AND :todate "
			+ "and pe.groupn2 = :g2 and pe.groupn3 = :g3 and pe.groupn4 = :g4", nativeQuery = true)
	List<ProjectionReportDto> getAllNewProjectionEntryWithGroupn2AndGroupn3AndGroupn4(String g2, String g3, String g4,
			String fromdate, String todate);

	// Method for g2, g3, g4, and g5
	@Query(value = "select pe.id ,pe.projection_date as pedate, pei.productid as productid, p.product_name as productname,p.ean_code as eancode, p.product_type as producttype,p.uom_secondary as uomsecondary,pei.qty as pcsqty, pei.kgqty as kgqty "
			+ "from projectionentry pe " + "left join projectionentryitems pei on pe.id = pei.projectionentryitem "
			+ "left join product p on p.id = pei.productid " + "where pe.projection_date between :fromdate AND :todate "
			+ "and pe.groupn2 = :g2 and pe.groupn3 = :g3 and pe.groupn4 = :g4 and pe.groupn5 = :g5", nativeQuery = true)
	List<ProjectionReportDto> getAllNewProjectionEntryWithGroupn2AndGroupn3AndGroupn4AndGroupn5(String g2, String g3,
			String g4, String g5, String fromdate, String todate);
// ==========================================
	
// Method for g3 and g4
	@Query(value = "select pe.id ,pe.projection_date as pedate, pei.productid as productid, p.product_name as productname,p.ean_code as eancode,p.product_type as producttype,p.uom_secondary as uomsecondary, pei.qty as pcsqty, pei.kgqty as kgqty "
			+ "from projectionentry pe " + "left join projectionentryitems pei on pe.id = pei.projectionentryitem "
			+ "left join product p on p.id = pei.productid " + "where pe.projection_date between :fromdate AND :todate "
			+ "and pe.groupn3 = :g3 and pe.groupn4 = :g4", nativeQuery = true)
	List<ProjectionReportDto> getAllNewProjectionEntryWithGroupn3AndGroupn4(String g3, String g4, String fromdate,
			String todate);

// Method for g3 and g4 and g5
	@Query(value = "select pe.id ,pe.projection_date as pedate, pei.productid as productid, p.product_name as productname,p.ean_code as eancode,p.product_type as producttype,p.uom_secondary as uomsecondary, pei.qty as pcsqty, pei.kgqty as kgqty "
			+ "from projectionentry pe " + "left join projectionentryitems pei on pe.id = pei.projectionentryitem "
			+ "left join product p on p.id = pei.productid " + "where pe.projection_date between :fromdate AND :todate "
			+ "and pe.groupn3 = :g3 and pe.groupn4 = :g4 and pe.groupn5 = :g5", nativeQuery = true)
	List<ProjectionReportDto> getAllNewProjectionEntryWithGroupn3AndGroupn4AndGroupn5(String g3, String g4, String g5,
			String fromdate, String todate);
//  ================================================
	
// Method for g4 and g5
	@Query(value = "select pe.id ,pe.projection_date as pedate, pei.productid as productid, p.product_name as productname,p.ean_code as eancode,p.product_type as producttype,p.uom_secondary as uomsecondary, pei.qty as pcsqty, pei.kgqty as kgqty "
			+ "from projectionentry pe " + "left join projectionentryitems pei on pe.id = pei.projectionentryitem "
			+ "left join product p on p.id = pei.productid " + "where pe.projection_date between :fromdate AND :todate "
			+ "and pe.groupn4 = :g4 and pe.groupn5 = :g5", nativeQuery = true)
	List<ProjectionReportDto> getAllNewProjectionEntryWithGroupn4AndGroupn5(String g4, String g5, String fromdate,
			String todate);

	@Query(value = "select p.id, p.groupn1,g1.title as title1,p.groupn2,g2.title as title2,p.groupn3,g3.title as title3,\r\n"
			+ " p.groupn4,g4.title as title4,p.groupn5 ,g5.title as title5,\r\n" + " p.projection_date as pdate,\r\n"
			+ " pr.product_name as productname ,pi.qty as qty\r\n" + " from projectionentry p \r\n"
			+ " left join projectionentryitems pi on p.id=pi.projectionentryitem\r\n"
			+ " left join product pr on pr.id=pi.productid \r\n" + " left join groupn1 g1 on g1.id=p.groupn1\r\n"
			+ " left join groupn2 g2 on g2.id=p.groupn2\r\n" + " left join groupn3 g3 on g3.id=p.groupn3\r\n"
			+ " left join groupn4 g4 on g4.id=p.groupn4\r\n"
			+ " left join groupn5 g5 on g5.id=p.groupn5", nativeQuery = true)
	Page<IndexProjectionEntry> getAllProjectionEntry(Pageable p);

	@Query(value = "select pe.id ,pe.projection_date as pedate,pei.qty,p.product_name as productname\r\n"
			+ "from projectionentry pe left join projectionentryitems pei \r\n" + "on pe.id=pei.projectionentryitem\r\n"
			+ "left join product p\r\n" + "on p.id=pei.productid\r\n"
			+ "where pe.groupn1 Like CONCAT('%', :search, '%')", nativeQuery = true)
	Page<IndexProjectionEntry> getAllProjectionEntryWithGroupn1(String search, Pageable p);


}
