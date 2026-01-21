package com.SCM.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.SCM.ExportDto.ExportProduct;
import com.SCM.dto.ProductDto;
import com.SCM.model.OpeningStock;
import com.SCM.model.Product;
import com.SCM.projection.ProductProjection;
import com.SCM.projection.ProductTypeByProduct;
import com.google.api.gax.paging.Page;

public interface ProductService {

	public Product saveProduct1(ProductDto productDto, MultipartFile[] files) throws IllegalStateException, IOException;

	public List<OpeningStock> getAllProductandOpeningStock();

	public List<ProductDto> getAllProductandOpeningStockandImages();

	public List<Product> getAllProduct();

	List<Product> findByBrand(int id);

	public List<Product> fetchAll(int page);

	public List<ProductDto> getAllProductWithImages();

	public ProductDto getProductbyIdWithImages(int id);

	public Product productById(int id);

	public Product getProductById(int id);

	public String deleteProduct(int id);

	public String deleteOpeningStockandProduct(int id);

	public String deleteProductImage(int id);

	public Product updateProduct(ProductDto productDto, MultipartFile[] files, int id)
			throws IllegalStateException, IOException;

	public Product updateProduct1(ProductDto productDto, MultipartFile[] files, int id)
			throws IllegalStateException, IOException, JSONException;

	public byte[] downloadImages(String location) throws IOException;

	public Resource getImagePath() throws MalformedURLException;

// -------------- Index Fetch

	public Map<String, Object> IndexProductAsc(int pageno, int pagesize, String field);

	public Map<String, Object> IndexProductDSC(int pageno, int pagesize, String field);

	public Map<String, Object> SearchProduct(int pageno, int pagesize, String search);

//    -------------- Index Finished Product

	public Map<String, Object> IndexFinishedProductAsc(int pageno, int pagesize, String field);

	public Map<String, Object> IndexFinishedProductDSC(int pageno, int pagesize, String field);

	public Map<String, Object> SearchFinishedProduct(int pageno, int pagesize, String search);

	// ------------- export product

	public List<ExportProduct> exportProduct();

	// -------------

//    public List<Product> productwithwarehouseid(int woid);

	public List<Product> productwithwarehouseid(int woid);

	public List<ProductTypeByProduct> productByProductType(String product_type);

	public List<Product> AllProduct();

	public Map<String, Object> productByProductCategory(int pagno, int pagesize, String category);

	Page<List<Product>> findWithPagination(String category, int offset, int pagesize);

//  product pagination by category

	Map<String, Object> IndexProductProjectionForCategory(String category, int pageno, int pagesize, String field);

	Map<String, Object> searchProductByCategoryVise(String search1, String search2, int pageno, int pagesize);

	Map<String, Object> IndexProductProjectionForCategoryAndProductTypeAsc(String productType, String category,int pageno, int pagesize, String field);

	Map<String, Object> IndexProductProjectionForCategoryAndProductTypeDesc(String productType, String category,int pageno, int pagesize, String field);

	Map<String, Object> searchProductByProductTypeVise(String productType, String category, String search1, int pageno,int pagesize);

	Map<String, Object> searchProductByProductNameVise(String search, int pageno, int pagesize);

	
// ------------------ Index product with single image

	public Map<String, Object> IndexProductWithSingleImageAsc(int pageno, int pagesize, String field);

	public Map<String, Object> IndexProductWithSingleImageDSC(int pageno, int pagesize, String field);

	public Map<String, Object> SearchProductWithSingleImage(int pageno, int pagesize, String search);



}
