package com.SCM.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SCM.ExportDto.ExportProduct;
import com.SCM.IndexDto.IndexProduct;
import com.SCM.IndexDto.IndexProductCategory;
import com.SCM.IndexDto.ProductWithSigleImage;
import com.SCM.model.OpeningStock;
import com.SCM.model.Product;
import com.SCM.projection.ProductProjection;
import com.SCM.projection.ProductProjectionByCategory;
import com.SCM.projection.ProductProjectionByProductType;
import com.SCM.projection.ProductTypeByProduct;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {


	@Query(value = "SELECT * " +
            "FROM product p left join brand b on p.brand=b.brand_name\r\n" +
       "WHERE p.product_kind = 'finished' and b.id=:id", 
    nativeQuery = true)
List<Product> findByBrand(@Param("id") int id);
	
//	@Query(value = "SELECT p.id, p.product_name, p.costprice, p.uom_secondary, p.uom_primary, p.product_type, p.standard_qty_per_box, p.product_group, p.mrp, p.ean_code, p.dlp, p.capacity, p.diameter, p.short_name, DATE_FORMAT(p.createddate,'%d-%m-%Y') AS createddate, p.createdtime, p.product_kind " +
//            "FROM product p " +
//            "LEFT JOIN brand b ON p.brand = b.brand_name " +
//            "WHERE p.product_kind = 'finished' AND p.brand = ?", 
//    nativeQuery = true)
//List<ProductProjection> findByBrand(String brand);


	
	@Query(value = "SELECT * FROM product p JOIN opening_stock ON p.id = opening_stock.pid;", nativeQuery = true)
	List<OpeningStock> getProductandOpeningStock();

	
	@Query(value = "select * from product ORDER BY  id LIMIT 10 OFFSET ?1", nativeQuery = true)
	List<Product> fetchProduct(int page);
	
	// --------- index product

	@Query(value = "SELECT p.id,p.product_name,p.uom_secondary,p.costprice,p.uom_primary,p.product_type,p.standard_qty_per_box,p.product_group,p.mrp,p.ean_code,p.dlp,p.capacity,p.diameter,p.short_name,DATE_FORMAT(p.createddate,'%d-%m-%Y') as createddate,p.createdtime,p.product_kind"
			+ " from product p", nativeQuery = true)
	List<IndexProduct> indexProduct(Pageable p);

	
	@Query(value = "SELECT p.id,p.product_name,p.costprice,p.uom_secondary,p.uom_primary,p.product_type,p.standard_qty_per_box,p.product_group,p.mrp,p.ean_code,p.dlp,p.capacity,p.diameter,p.short_name,DATE_FORMAT(p.createddate,'%d-%m-%Y') as createddate,p.createdtime,p.product_kind "
			+ "FROM product p" 
			+ " WHERE p.product_kind = 'finished'", nativeQuery = true)
	List<IndexProduct> indexProductForFinishedProduct(Pageable p);
	
	
//	@Query(value = "SELECT p.id,p.product_name,p.costprice,p.uom_secondary,p.uom_primary,p.product_type,p.standard_qty_per_box,p.product_group,p.mrp,p.ean_code,p.dlp,p.capacity,p.diameter,p.short_name,DATE_FORMAT(p.createddate,'%d-%m-%Y') as createddate,p.createdtime,p.product_kind "
//			+ "FROM product p left join brand" 
//			+ " WHERE p.product_kind = 'finished'", nativeQuery = true)
//	List<IndexProduct> indexProductForFinishedProductBrand(Pageable p);

	
//	@Query(value = "select p.id,p.product_name,p.standard_qty_per_box,p.product_group,p.mrp,p.ean_code,p.dlp,p.capacity,p.diameter FROM product p where p.product_name Like CONCAT('%', :search, '%')"
//			+ "or p.standard_qty_per_box Like CONCAT('%', :search, '%')"
//			+ "or p.dlp Like CONCAT('%', :search, '%')"
//			+ "or p.product_group Like CONCAT('%', :search, '%')"
//			+ "or p.capacity Like CONCAT('%', :search, '%')"
//			+ "or p.diameter Like CONCAT('%', :search, '%')"
//			+ "or p.ean_code Like CONCAT('%', :search, '%')" + "or p.id Like CONCAT('%', :search, '%')"
//			+ "or p.mrp Like CONCAT('%', :search,'%')", nativeQuery = true)
//	List<IndexProduct> SearchByProduct(String search, Pageable p);

//	@Query(value = "select p.id,p.product_name,p.standard_qty_per_box,p.product_group,p.mrp,p.ean_code,p.dlp,p.capacity,p.diameter FROM product p where p.product_name regexp CONCAT('[', :search, ']')"
//			+ "or p.standard_qty_per_box regexp CONCAT('[', :search, ']')"
//			+ "or p.dlp regexp CONCAT('[', :search, ']')"
//			+ "or p.product_group regexp CONCAT('[', :search, ']')"
//			+ "or p.capacity regexp CONCAT('[', :search, ']')"
//			+ "or p.diameter regexp CONCAT('[', :search, ']')"
//			+ "or p.ean_code regexp CONCAT('[', :search, ']')" 
//			+ "or p.id regexp CONCAT('[', :search, ']')"
//			+ "or p.mrp regexp CONCAT('[', :search, ']')", nativeQuery = true)
//	List<IndexProduct> SearchByProduct(String search, Pageable p);

//	@Query(value = "select p.id,p.product_name,p.standard_qty_per_box,p.product_group,p.mrp,p.ean_code,p.dlp,p.capacity,p.diameter,p.short_name,DATE_FORMAT(p.createddate,'%d-%m-%Y')AS createddate,p.createdtime FROM product p "
//			+ " where  REPLACE(p.product_name,' ', '') Like CONCAT('%', :search, '%')"
//			+ "  or p.product_name  Like CONCAT('%', :search, '%') "
//
//			+ " or REPLACE(p.standard_qty_per_box,' ', '')  Like CONCAT('%', :search, '%')"
//			+ "  or p.standard_qty_per_box  Like CONCAT('%', :search, '%') "
//
//			+ " or REPLACE(p.dlp,' ', '')  Like CONCAT('%', :search, '%')"
//			+ " or p.dlp  Like CONCAT('%', :search, '%') "
//
//			+ "or REPLACE(p.product_group,' ', '')  Like CONCAT('%', :search, '%')"
//			+ "or p.product_group  Like CONCAT('%', :search, '%') "
//
//			+ "or REPLACE(p.capacity,' ', '')  Like CONCAT('%', :search, '%')"
//			+ "or p.capacity Like CONCAT('%', :search, '%') "
//
//			+ "or REPLACE(p.diameter,' ', '')  Like CONCAT('%', :search, '%')"
//			+ "or p.diameter Like CONCAT('%', :search, '%') "
//
//			+ "or REPLACE(p.ean_code,' ', '')  Like CONCAT('%', :search, '%')"
//			+ "or p.ean_code Like CONCAT('%', :search, '%') "
//
//			+ "or REPLACE(p.id,' ', '')  Like CONCAT('%', :search, '%')" + "or p.id Like CONCAT('%', :search, '%') "
//
//			+ "or REPLACE(p.mrp,' ', '')  Like CONCAT('%', :search,'%')" + " or p.mrp Like CONCAT('%', :search, '%') "
//
//			+ "or REPLACE(p.short_name,' ', '')  Like CONCAT('%', :search, '%')"
//			+ "or p.short_name  Like CONCAT('%', :search, '%') "
//
//			, nativeQuery = true)
//	List<IndexProduct> SearchByProduct(String search, Pageable p);
	
	
	
	@Query(value = "select p.id,p.product_name,p.standard_qty_per_box,p.product_group,p.mrp,p.ean_code,p.dlp,p.capacity,p.diameter,p.short_name,DATE_FORMAT(p.createddate,'%d-%m-%Y')AS createddate,p.createdtime FROM product p "
			+ " where REGEXP_REPLACE(LOWER(p.product_name), '[^a-z0-9]', '') LIKE CONCAT('%', :search, '%')\r\n"
			+ "OR LOWER(p.product_name) LIKE CONCAT('%', :search, '%') "

			+ " or REPLACE(p.standard_qty_per_box,' ', '')  Like CONCAT('%', :search, '%')"
			+ "  or p.standard_qty_per_box  Like CONCAT('%', :search, '%') "

			+ " or REPLACE(p.dlp,' ', '')  Like CONCAT('%', :search, '%')"
			+ " or p.dlp  Like CONCAT('%', :search, '%') "

			+ "or REPLACE(p.product_group,' ', '')  Like CONCAT('%', :search, '%')"
			+ "or p.product_group  Like CONCAT('%', :search, '%') "

			+ "or REPLACE(p.capacity,' ', '')  Like CONCAT('%', :search, '%')"
			+ "or p.capacity Like CONCAT('%', :search, '%') "

			+ "or REPLACE(p.diameter,' ', '')  Like CONCAT('%', :search, '%')"
			+ "or p.diameter Like CONCAT('%', :search, '%') "

			+ "or REPLACE(p.ean_code,' ', '')  Like CONCAT('%', :search, '%')"
			+ "or p.ean_code Like CONCAT('%', :search, '%') "

			+ "or REPLACE(p.id,' ', '')  Like CONCAT('%', :search, '%')" + "or p.id Like CONCAT('%', :search, '%') "

			+ "or REPLACE(p.mrp,' ', '')  Like CONCAT('%', :search,'%')" + " or p.mrp Like CONCAT('%', :search, '%') "

			+ "or REPLACE(p.short_name,' ', '')  Like CONCAT('%', :search, '%')"
			+ "or p.short_name  Like CONCAT('%', :search, '%') "

			, nativeQuery = true)
	List<IndexProduct> SearchByProduct(String search, Pageable p);

	@Query(value = "SELECT p.id,p.product_name,p.standard_qty_per_box,p.product_kind,p.product_group,p.mrp,p.ean_code,p.dlp,p.capacity,p.diameter,p.short_name,DATE_FORMAT(p.createddate,'%d-%m-%Y')AS createddate,p.createdtime "
			+ " FROM product p "
			+ "  WHERE (p.product_kind = 'finished') AND (REGEXP_LIKE(REGEXP_REPLACE(p.product_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
			+ "   OR REGEXP_LIKE(REGEXP_REPLACE(p.standard_qty_per_box,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
			+ "   OR REGEXP_LIKE(REGEXP_REPLACE(p.product_group,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
			+ "   OR REGEXP_LIKE(REGEXP_REPLACE(p.mrp,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
			+ "   OR REGEXP_LIKE(REGEXP_REPLACE(p.dlp,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
			+ "   OR REGEXP_LIKE(REGEXP_REPLACE(p.capacity,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
			+ "   OR REGEXP_LIKE(REGEXP_REPLACE(p.diameter,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
			+ "   OR REGEXP_LIKE(REGEXP_REPLACE(p.product_kind,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
			+ "   OR REGEXP_LIKE(REGEXP_REPLACE(p.short_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', '')))", nativeQuery = true)
	List<IndexProduct> SearchByProductForFinishedProduct(String search, Pageable p);

	@Query(value = "SELECT p.*,b.brand_name FROM product p"
			+ " LEFT join brand b on p.brand = b.id", nativeQuery = true)
	List<ExportProduct> ExportProduct();

	@Query(value = "SELECT p.* FROM product p JOIN opening_stock os ON p.id = os.product_id JOIN warehouse w ON os.wid = w.id WHERE w.id = ?1", nativeQuery = true)
	public List<Product> fetchproductbywarehouseid(int woid);

	@Query(value = "select p.category from product p group by p.category", nativeQuery = true)
	public List<IndexProductCategory> category();

	@Query(value = "select * from product where category = :category", nativeQuery = true)
	public List<Product> productbycategory(String category, PageRequest p);

	@Query(value = "select * from product where category = :category", nativeQuery = true)
	public List<Product> productbycategoryPagination(String category, Pageable p);

	@Query(value = "select category from product where product_type=? group by category", nativeQuery = true)
	public List<ProductTypeByProduct> productByProductType(String product_type);

//	@Query(value="SELECT p.product_name, p.short_name, p.ean_code, p.category FROM product p WHERE REPLACE(p.category, ' ', '') LIKE CONCAT('%', :search, '%') OR p.category LIKE CONCAT('%', :search, '%')", nativeQuery = true)
	@Query(value = "SELECT p.id,p.product_name,p.mrp,p.short_name, p.ean_code, p.category,pi.location"
			+ "  FROM product p" + "  left join product_image pi on p.id = pi.product_id"
			+ "  WHERE REPLACE(p.category, ' ', '') LIKE CONCAT('%', :search, '%') "
			+ " OR p.category LIKE CONCAT('%', :search, '%')", nativeQuery = true)
	public List<ProductProjectionByCategory> getProductByCategoryVise(String search, Pageable p);

	
	@Query(value = "SELECT p.product_name,p.short_name,p.ean_code,p.category,p.id,p.mrp,pi.location FROM product p\r\n"
			+ " left join product_image pi on p.id = pi.product_id"
			+ " WHERE (REPLACE(p.category, ' ', '') LIKE CONCAT('%', :search1,'%') or p.category LIKE CONCAT('%', :search1,'%') )\r\n"
			+ " and ((replace(p.product_name,' ','') LIKE CONCAT('%', :search2,'%') or p.product_name LIKE CONCAT('%', :search2,'%')\r\n"
			+ " or (replace(p.ean_code,' ','') LIKE CONCAT('%', :search2,'%') or p.ean_code LIKE CONCAT('%', :search2,'%')\r\n"
			+ " or (replace(p.mrp,' ','') LIKE CONCAT('%', :search2,'%') or p.mrp LIKE CONCAT('%', :search2,'%')"
			+ " or (replace(pi.location,' ','') LIKE CONCAT('%', :search2,'%') or pi.location LIKE CONCAT('%', :search2,'%')"
			+ " or (replace(p.short_name,' ','') LIKE CONCAT('%', :search2,'%') or p.short_name LIKE CONCAT('%', :search2,'%')\r\n))))))", nativeQuery = true)
	public List<ProductProjectionByCategory> searchProductByCategoryVise(String search1, String search2, Pageable p);

	
	@Query(value = "SELECT p.id,p.product_name,p.mrp,p.short_name,p.hsn_code,p.ean_code, p.category,pi.location,p.product_type,p.standard_qty_per_box,p.uom_primary,p.uom_secondary,p.dlp,p.capacity,p.diameter,p.brand,p.igst,p.cgst,p.sgst\r\n"
			+ "			FROM product p\r\n" 
			+ "			left join product_image pi on p.id = pi.product_id\r\n"
			+ "			WHERE p.category=? and p.product_type=? ", nativeQuery = true)
	public List<ProductProjectionByProductType> getProductByProductTypeVise(@Param("category") String category,
			@Param("product_type") String product_type, Pageable p);

	@Query(value = "SELECT p.id,p.product_name,p.mrp,p.short_name,p.hsn_code, p.ean_code, p.category as category,pi.location,p.product_type,"
			+ " p.standard_qty_per_box,p.uom_primary,p.uom_secondary,p.dlp,p.capacity,p.diameter,p.brand,p.igst,p.cgst,p.sgst\r\n"
			+ "	FROM product p\r\n" + "	left join product_image pi on p.id = pi.product_id\r\n"
			+ "	WHERE ((p.category=:category) and (p.product_type=:product_type))\r\n"
			+ " and ((replace(p.product_name,' ','') LIKE CONCAT('%', :search1,'%') or p.product_name LIKE CONCAT('%', :search1,'%'))\r\n"
			+ "or ((replace(p.mrp,' ','') LIKE CONCAT('%', :search1,'%') or p.mrp LIKE CONCAT('%', :search1,'%'))\r\n"
			+ "or ((replace(p.short_name,' ','') LIKE CONCAT('%', :search1,'%') or p.short_name LIKE CONCAT('%', :search1,'%'))\r\n"
			+ "or ((replace(p.ean_code,' ','') LIKE CONCAT('%', :search1,'%') or p.ean_code LIKE CONCAT('%', :search1,'%'))\r\n"
			+ "or ((replace(p.hsn_code,' ','') LIKE CONCAT('%', :search1,'%') or p.hsn_code LIKE CONCAT('%', :search1,'%'))\r\n"
			+ "or ((replace(p.category,' ','') LIKE CONCAT('%', :search1,'%') or p.category LIKE CONCAT('%', :search1,'%'))\r\n"
			+ "or ((replace(p.standard_qty_per_box,' ','') LIKE CONCAT('%', :search1,'%') or p.standard_qty_per_box LIKE CONCAT('%', :search1,'%'))\r\n"
			+ "or ((replace(p.uom_primary,' ','') LIKE CONCAT('%', :search1,'%') or p.uom_primary LIKE CONCAT('%', :search1,'%'))\r\n"
			+ "or ((replace(p.uom_secondary,' ','') LIKE CONCAT('%', :search1,'%') or p.uom_secondary LIKE CONCAT('%', :search1,'%'))\r\n"
			+ "or ((replace(p.dlp,' ','') LIKE CONCAT('%', :search1,'%') or p.dlp LIKE CONCAT('%', :search1,'%'))\r\n"
			+ "or ((replace(p.capacity,' ','') LIKE CONCAT('%', :search1,'%') or p.capacity LIKE CONCAT('%', :search1,'%'))\r\n"
			+ "or ((replace(p.diameter,' ','') LIKE CONCAT('%', :search1,'%') or p.diameter LIKE CONCAT('%', :search1,'%'))\r\n"
			+ "or ((replace(p.brand,' ','') LIKE CONCAT('%', :search1,'%') or p.brand LIKE CONCAT('%', :search1,'%'))\r\n"
			+ "or ((replace(p.igst,' ','') LIKE CONCAT('%', :search1,'%') or p.igst LIKE CONCAT('%', :search1,'%'))\r\n"
			+ "or ((replace(p.cgst,' ','') LIKE CONCAT('%', :search1,'%') or p.cgst LIKE CONCAT('%', :search1,'%'))\r\n"
			+ "or ((replace(p.sgst,' ','') LIKE CONCAT('%', :search1,'%') or p.sgst LIKE CONCAT('%', :search1,'%'))\r\n"
			+ "or ((replace(pi.name,' ','') LIKE CONCAT('%', :search1,'%') or pi.name LIKE CONCAT('%', :search1,'%'))\r\n"
			+ "or ((replace(p.product_type,' ','') LIKE CONCAT('%', :search1,'%') or p.product_type LIKE CONCAT('%', :search1,'%'))))))))))))))))))))", nativeQuery = true)
	public List<ProductProjectionByProductType> getProductByProductTypeViseSearch(@Param("category") String category,
			@Param("product_type") String product_type, Pageable p, String search1);

	@Query(value = "SELECT p.id,p.product_name,p.mrp,p.short_name,p.hsn_code, p.ean_code, p.category as category,pi.location,p.product_type,\r\n"
			+ "		    p.standard_qty_per_box,p.uom_primary,p.uom_secondary,p.dlp,p.capacity,p.diameter,p.brand,p.igst,p.cgst,p.sgst\r\n"
			+ "				FROM product p\r\n" + "				left join product_image pi on p.id = pi.product_id\r\n"
			+ "				WHERE (REPLACE(p.product_name,' ', '') LIKE CONCAT('%',:search,'%') or p.product_name LIKE CONCAT('%',:search,'%'))\r\n"
			+ "			 \r\n" + "		\r\n"
			+ "			or ((replace(p.short_name,' ','') LIKE CONCAT('%',:search,'%') or p.short_name LIKE CONCAT('%',:search,'%'))\r\n"
			+ "			or ((replace(p.ean_code,' ','') LIKE CONCAT('%', :search,'%') or p.ean_code LIKE CONCAT('%',:search,'%'))\r\n"
			+ "		))", nativeQuery = true)
	public List<ProductProjectionByProductType> getProductByProductNameViseSearch(String search, Pageable p);

	
	@Query(value = "SELECT p.id,p.product_name,p.mrp,p.short_name,p.hsn_code, p.ean_code, p.category as category,p.product_type,"
			+ "	 p.standard_qty_per_box,p.uom_primary,p.uom_secondary,p.dlp,p.capacity,p.diameter,p.brand,p.igst,p.cgst,p.sgst"
			+ "	 FROM product p where p.id = ?1", nativeQuery = true)
	ProductProjectionByProductType productgetById(int pid);

	
	@Query(value = "SELECT p.id, p.product_name,p.ean_code,p.hsn_code,p.product_type,p.short_name,p.product_kind,p.costprice,p.uom,p.dlp,p.mrp,pi.location,pi.name,pi.product_id\r\n"
			+ "FROM product p\r\n" 
			+ "LEFT JOIN product_image pi ON p.id = pi.product_id\r\n"
			+ "WHERE pi.product_id IS NULL OR pi.id IN \r\n"
			+ "(SELECT MIN(pi2.id) FROM product_image pi2 WHERE pi2.product_id = p.id)", nativeQuery = true)
	List<ProductWithSigleImage> productWithSigleImages(Pageable p);

	
	
//	@Query(value = "SELECT p.id,p.product_name,p.ean_code,p.hsn_code,p.product_type,p.short_name,p.product_kind,p.costprice,p.uom,p.dlp,p.mrp,pi.location,pi.name,pi.product_id\r\n"
//			+ " FROM product p\r\n"
//			+ " LEFT JOIN product_image pi ON p.id = pi.product_id\r\n"
//			+ " WHERE (pi.product_id IS NULL OR pi.id IN (SELECT MIN(pi2.id) FROM product_image pi2 WHERE pi2.product_id = p.id))\r\n"
//			+ " AND REGEXP_LIKE(REGEXP_REPLACE(p.product_name, '[^a-zA-Z0-9]', ''), :search)"
//			+ " OR REGEXP_LIKE(REGEXP_REPLACE(p.ean_code, '[^a-zA-Z0-9]', ''), :search)"
//			+ " OR REGEXP_LIKE(REGEXP_REPLACE(p.hsn_code, '[^a-zA-Z0-9]', ''), :search)"
//			+ " OR REGEXP_LIKE(REGEXP_REPLACE(p.product_type, '[^a-zA-Z0-9]', ''), :search)"
//			+ " OR REGEXP_LIKE(REGEXP_REPLACE(p.short_name, '[^a-zA-Z0-9]', ''), :search)"
//			+ " OR REGEXP_LIKE(REGEXP_REPLACE(p.product_kind, '[^a-zA-Z0-9]', ''), :search)", nativeQuery = true)
//	List<ProductWithSigleImage> SearchproductWithSigleImages(Pageable p, String search);
	
	@Query(value = "SELECT p.id,p.product_name,p.ean_code,p.hsn_code,p.product_type,p.short_name,p.product_kind,p.costprice,p.uom,p.dlp,p.mrp,pi.location,pi.name,pi.product_id\r\n"
			+ "FROM product p\r\n"
			+ "LEFT JOIN product_image pi ON p.id = pi.product_id\r\n"
			+ "WHERE (pi.product_id IS NULL OR pi.id IN (SELECT MIN(pi2.id) FROM product_image pi2 WHERE pi2.product_id = p.id))\r\n"
			+ "AND (REGEXP_LIKE(REGEXP_REPLACE(p.product_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))\r\n"
			+ "OR  REGEXP_LIKE(REGEXP_REPLACE(p.product_kind,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))\r\n"
			+ "OR  REGEXP_LIKE(REGEXP_REPLACE(p.product_type,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))\r\n"
			+ "OR  p.id Like CONCAT('%',:search, '%')\r\n"
			+ "OR p.ean_code LIKE CONCAT('%',:search, '%'))", nativeQuery = true)
    List<ProductWithSigleImage> SearchproductWithSigleImages(Pageable p, String search);
	
	


}
