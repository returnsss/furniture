package com.teamproject.furniture.product.service;

import com.teamproject.furniture.product.dtos.AddProductDto;
import com.teamproject.furniture.product.dtos.ProductPageDto;
import com.teamproject.furniture.product.dtos.UpdateProductDto;
import com.teamproject.furniture.product.model.Product;
import com.teamproject.furniture.product.repository.ProductRepository;
import com.teamproject.furniture.product.repository.ProductRepositoryCustomPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 제품 등록
     * @param addProductDto
     * @return
     */
    public Long addProduct(AddProductDto addProductDto) {
        Product product = new Product(addProductDto);
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
    public void updateProduct(UpdateProductDto updateProductDto) {
        Product existingProduct = productRepository.findById(updateProductDto.getProductId())
                .orElseThrow(() -> new NoSuchElementException("해당 제품을 찾을 수 없습니다."));
        existingProduct.updateProduct(updateProductDto);
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


    public Page<ProductPageDto> selectProductList(String searchVal, Pageable pageable) {
        return productRepositoryCustomPage.selectProductList(searchVal, pageable);
    }
}
