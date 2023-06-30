package com.teamproject.furniture.product.service;

import com.teamproject.furniture.order.domain.OrderData;
import com.teamproject.furniture.order.dtos.OrderDataDto;
import com.teamproject.furniture.product.dtos.AddProductDto;
import com.teamproject.furniture.product.dtos.ProductDto;
import com.teamproject.furniture.product.dtos.ProductPageDto;
import com.teamproject.furniture.product.dtos.UpdateProductDto;
import com.teamproject.furniture.product.model.Product;
import com.teamproject.furniture.product.repository.ProductRepository;
import com.teamproject.furniture.product.repository.ProductRepositoryCustomPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ProductService {

    private ProductRepository productRepository;
    private ProductRepositoryCustomPage productRepositoryCustomPage;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductRepositoryCustomPage productRepositoryCustomPage) {
        this.productRepository = productRepository;
        this.productRepositoryCustomPage = productRepositoryCustomPage;
    }

    @Value("${image.path}")
    private String uploadDir;


    /**
     * 제품 등록
     * @param addProductDto
     * @return
     */
    public Long addProduct(AddProductDto addProductDto) throws IOException {
        Product product = new Product(addProductDto);

        String fileName = getFileName(addProductDto.getProductImage());

        addProductDto.getProductImage().transferTo(new File(uploadDir + fileName));
        product.setFileName(fileName);
        product.setImgPath("/PjImg/" + fileName);

        Product save = productRepository.save(product);
        return save.getProductId();
    }

    /**
     * 제품 상세정보 조회
     * @param productId
     * @return
     */
    public Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("해당 제품을 찾을 수 없습니다."));
    }

    public ProductDto getProductDto(Long productId){
        Product product = productRepository.findById(productId).orElseThrow();
        return ProductDto.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .description(product.getDescription())
                .category(product.getCategory())
                .productsInStock(product.getProductsInStock())
                .fileName(product.getFileName())
                .imgPath(product.getImgPath())
                .registDay(product.getRegistDay())
                .build();
    }

    /**
     * 모든 제품 조회
     * @return
     */
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    /**
     * 제품 수정
     * @param updateProductDto
     */
    public void updateProduct(UpdateProductDto updateProductDto) throws IOException {
        Product existingProduct = productRepository.findById(updateProductDto.getProductId())
                .orElseThrow(() -> new NoSuchElementException("해당 제품을 찾을 수 없습니다."));

        if("".equals(updateProductDto.getProductImage().getOriginalFilename())){
            String fileName = updateProductDto.getFileName();

            existingProduct.updateProduct(updateProductDto);

            existingProduct.setFileName(fileName);
            existingProduct.setImgPath("/PjImg/" + fileName);
        }else {
            String fileName = getFileName(updateProductDto.getProductImage());

            updateProductDto.getProductImage().transferTo(new File(uploadDir + fileName));

            existingProduct.updateProduct(updateProductDto);

            existingProduct.setFileName(fileName);
            existingProduct.setImgPath("/PjImg/" + fileName);
        }


    }

    private String getFileName(MultipartFile productImage){
        LocalDateTime now = LocalDateTime.now();
        String fileName = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "-" + productImage.getOriginalFilename();
        return fileName;
    }

    /**
     * 제품 삭제
     * @param productId
     */
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }


    /**
     * 제품 검색
     * @param column
     * @param keyword
     * @return
     */
    public List<Product> searchProducts(String column, String keyword) {

        return productRepository.searchProducts(column, keyword);

    }

    public void productLockCnt(List<OrderDataDto> orderDataDtoList) throws Exception {
        List<Product> productList = new ArrayList<>();

        for (OrderDataDto orderDataDto : orderDataDtoList){
            OrderData orderData = new OrderData(orderDataDto);

            Product product = productRepository.findById(Long.valueOf(orderData.getProductId())).orElseThrow();

            int productsInStock = product.getProductsInStock();
            int lockCnt = product.getLockCnt();

            if (productsInStock < orderData.getCnt()){
                throw new Exception("상품의 남은 수량보다 주문하려는 상품의 개수가 더 많음");
            }

            product.setProductsInStock(productsInStock - orderData.getCnt());
            product.setLockCnt(lockCnt + orderData.getCnt());

            productList.add(product);
        }
        productRepository.saveAll(productList);
    }

    public void rollBackProductLockCnt(List<OrderDataDto> orderDataDtoList){
        List<Product> productList = new ArrayList<>();

        for (OrderDataDto orderDataDto : orderDataDtoList){
            OrderData orderData = new OrderData(orderDataDto);

            Product product = productRepository.findById(Long.valueOf(orderData.getProductId())).orElseThrow();

            int productsInStock = product.getProductsInStock();
            int lockCnt = product.getLockCnt();
            product.setProductsInStock(productsInStock + orderData.getCnt());
            product.setLockCnt(lockCnt - orderData.getCnt());

            productList.add(product);
        }
        productRepository.saveAll(productList);
    }


    public Page<ProductPageDto> selectProductList(String searchVal, Pageable pageable) {
        return productRepositoryCustomPage.selectProductList(searchVal, pageable);
    }
}
