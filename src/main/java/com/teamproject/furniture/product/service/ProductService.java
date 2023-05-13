package com.teamproject.furniture.product.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.teamproject.furniture.product.dtos.AddProductDto;
import com.teamproject.furniture.product.dtos.UpdateProductDto;
import com.teamproject.furniture.product.model.Product;
import com.teamproject.furniture.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.NoSuchElementException;

import static com.teamproject.furniture.product.model.QProduct.product;

@Service
@Transactional
public class ProductService {

    private ProductRepository productRepository;

    private JPAQueryFactory queryFactory;


    @Autowired
    public ProductService(ProductRepository productRepository, EntityManager entityManager) {
        this.productRepository = productRepository;
        this.queryFactory = new JPAQueryFactory(entityManager);
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

        return productRepository.searchProducts(column,keyword,queryFactory);

    }



}
