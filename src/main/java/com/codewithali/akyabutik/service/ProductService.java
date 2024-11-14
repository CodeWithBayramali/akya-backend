package com.codewithali.akyabutik.service;

import com.codewithali.akyabutik.dto.ProductDto;
import com.codewithali.akyabutik.dto.response.ApiResponse;
import com.codewithali.akyabutik.exception.ProductNotFoundException;
import com.codewithali.akyabutik.model.ColorSize;
import com.codewithali.akyabutik.model.Product;
import com.codewithali.akyabutik.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;
    private final String UPLOAD_DIR = "uploads/";

    public ProductService(ProductRepository productRepository, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
    }

    public List<ProductDto> getAllProducts(int page, int size) {
        return productRepository.findAll(PageRequest.of(page,size))
                .map(ProductDto::convertToDto)
                .stream().toList();
    }

    public ProductDto getProduct(String id) {
        return productRepository.findById(id)
                .map(ProductDto::convertToDto)
                .orElseThrow();
    }

    public List<ProductDto> getProductsByCategory(String category) {
        return productRepository.getProductsBySubCategory(category)
                .stream().map(ProductDto::convertToDto)
                .toList();
    }

    public List<ProductDto> getAllNewProducts(int page, int size) {
        return productRepository.findAll(PageRequest.of(page,size, Sort.Direction.DESC, "createdAt"))
                .map(ProductDto::convertToDto)
                .stream().toList();
    }

    public ApiResponse<ProductDto> createProduct(String productDto, MultipartFile[] images) {
        try {
            ProductDto proDto = objectMapper.readValue(productDto, ProductDto.class);
            List<String> imageList = new ArrayList<>();

            for (MultipartFile image : images) {
                String originalFileName = image.getOriginalFilename();
                String newFileName = UUID.randomUUID() + originalFileName;

                Path filePath = Paths.get(UPLOAD_DIR + newFileName);
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, image.getBytes());
                String fileUrl = "/uploads/" + newFileName;
                imageList.add(fileUrl);
            }

            ProductDto mainProduct = new ProductDto(
                    proDto.getName(),
                    proDto.getPrice(),
                    proDto.getStock(),
                    proDto.getSex(),
                    proDto.getDescription(),
                    proDto.getColorSize(),
                    proDto.getMainCategory(),
                    proDto.getSubCategory(),
                    imageList
            );
            productRepository.save(ProductDto.convertToEntity(mainProduct));
            return new ApiResponse<>("Ürün başarıyla oluşturuldu",null,true);
        } catch ( Exception e ) {
            return new ApiResponse<>(e.getMessage(),null,false);
        }
    }

    public ApiResponse<ProductDto> updateProduct(ProductDto productDto) {
        try {
            productRepository.save(ProductDto.convertToEntity(productDto));
            return new ApiResponse<>("Ürün güncellendi",null,true);
        } catch (Exception e) {
            return new ApiResponse<>(e.getMessage(),null,false);
        }
    }

    public ApiResponse<ProductDto> deleteProduct(String productId) {
        try {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            List<String> imageUrls = product.getImages();

            for (String imageUrl : imageUrls) {
                try {
                    // Dosya yolunu tam olarak oluşturuyoruz
                    Path filePath = Paths.get(UPLOAD_DIR + imageUrl.replace("/uploads/", ""));
                    Files.deleteIfExists(filePath);
                } catch (IOException e) {
                    System.err.println("Görsel Silinemedi: " + imageUrl);
                    e.printStackTrace();
                }
            }

            // Ürünü veritabanından siliyoruz
            productRepository.deleteById(productId);
            return new ApiResponse<>("Ürün başarıyla silindi", null, true);

        } catch (Exception e) {
            return new ApiResponse<>(e.getMessage(), null, false);
        }
    }

    public ApiResponse<ProductDto> updateStock(ProductDto productDto) {
        try {
            productRepository.save(ProductDto.convertToEntity(productDto));
            return new ApiResponse<>("Stok güncellendi", null, true);
        }catch (Exception e) {
            return new ApiResponse<>(e.getMessage(),null,false);
        }
    }

    @Transactional
    public void decreaseProduct(String productId, String colorName, String weight, int quantity) {
        // Ürünü veri tabanından çekiyoruz
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        // Genel stok kontrolü
        if (product.getStock() < quantity) {
            throw new ProductNotFoundException("Insufficient stock for product: " + product.getName());
        }

        // İlgili renk ve bedene göre ColorSize öğesini buluyoruz
        Optional<ColorSize> colorSizeOpt = product.getColorSize().stream()
                .filter(cs -> cs.getColorTagName().equals(colorName) && cs.getWeight().equals(weight))
                .findFirst();

        if (!colorSizeOpt.isPresent()) {
            throw new ProductNotFoundException("İlgili Ürün Bulunamadı !");
        }

        ColorSize colorSize = colorSizeOpt.get();

        // Belirtilen beden ve renk için stok kontrolü
        if (colorSize.getCount() < quantity) {
            throw new ProductNotFoundException("Stok Adet Yetersiz !");
        }

        // Genel stok ve ilgili ColorSize stoklarını düşüyoruz
        product.setStock(product.getStock() - quantity);
        colorSize.setCount(colorSize.getCount() - quantity);

        // Güncellenmiş ürünü veri tabanında kaydediyoruz
        productRepository.save(product);
    }
}
