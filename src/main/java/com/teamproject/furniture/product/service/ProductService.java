package com.teamproject.furniture.product.service;

import com.teamproject.furniture.product.dtos.AddProductDto;
import com.teamproject.furniture.product.dtos.GetProductDto;
import com.teamproject.furniture.product.model.Product;
import com.teamproject.furniture.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public Long addProduct(AddProductDto addProductDto) { // 제품 등록
        Product product = new Product(addProductDto);
        Product save = productRepository.save(product);
        return save.getProductId();
    }

    public Product getProduct(GetProductDto getProductDto) { // 제품 상세정보 조회
        return productRepository.findById(getProductDto.getProductId())
                .orElseThrow(() -> new NoSuchElementException("해당 제품을 찾을 수 없습니다."));
    }

    public List<Product> getProducts() { // 모든 제품 조회
        return productRepository.findAll();
    }




}
