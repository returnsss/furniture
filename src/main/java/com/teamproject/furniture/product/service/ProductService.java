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


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductService(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Autowired
    public ProductService(ProductRepository productRepository, JPAQueryFactory queryFactory) {
        this.productRepository = productRepository;
        this.queryFactory = queryFactory;
    }


    public Long addProduct(AddProductDto addProductDto) { // 제품 등록
        Product product = new Product(addProductDto);
        Product save = productRepository.save(product);
        return save.getProductId();
    }

    public Product getProduct(Long productId) { // 제품 상세정보 조회
        return productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("해당 제품을 찾을 수 없습니다."));
    }

    public List<Product> getProducts() { // 모든 제품 조회
        return productRepository.findAll();
    }

    public void updateProduct(UpdateProductDto updateProductDto) { // 제품 수정
        Product existingProduct = productRepository.findById(updateProductDto.getProductId())
                .orElseThrow(() -> new NoSuchElementException("해당 제품을 찾을 수 없습니다."));
        existingProduct.updateProduct(updateProductDto);
    }

    public void deleteProduct(Long productId) { // 제품 삭제
        productRepository.deleteById(productId);
    }




    public List<Product> searchProducts(String column, String keyword) {

        BooleanExpression searchCondition;

        if (column.equalsIgnoreCase("productName")) {
            searchCondition = product.productName.containsIgnoreCase(keyword);
        } else if (column.equalsIgnoreCase("category")) {
            searchCondition = product.category.containsIgnoreCase(keyword);
        } else {
            searchCondition = product.isNotNull();
        }

        return queryFactory
                .selectFrom(product)
                .where(searchCondition)
                .fetch();
    }



}
