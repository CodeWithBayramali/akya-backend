package com.tekerasoft.arzuamber.repository;

import com.tekerasoft.arzuamber.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    Page<Product> findAllByIsActiveTrueOrderByCreatedAtDesc(Pageable pageable);

    Page<Product> findByIsActiveTrue(Pageable pageable);

    Optional<Product> findBySlugIgnoreCase(String slug);

    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.isActive = :isActive WHERE p.id = :productId")
    void updateIsActive(@Param("productId") UUID productId, @Param("isActive") boolean isActive);

    @Query("SELECT t FROM Product t WHERE t.newSeason = true AND t.isActive = true")
    Page<Product> findByNewSeasonTrueAndIsActiveTrue(Pageable pageable);

    @Query("SELECT t FROM Product t WHERE t.populate = true AND t.isActive = true")
    Page<Product> findByPopulateTrueAndIsActiveTrue(Pageable pageable);

    @Query("SELECT t FROM Product t WHERE t.price IS NOT NULL")
    List<Product> findAllProductPrices();

    @Modifying
    @Query("UPDATE Product t SET t.price = :price WHERE t.id = :id")
    void updateProductPriceById(@Param("price") BigDecimal price, @Param("id") UUID id);

    @Modifying
    @Query("UPDATE Product t SET t.price = t.price + :amount")
    void increaseAllProductsPrice(@Param("amount") BigDecimal amount);

    @Modifying
    @Query("UPDATE Product t SET t.price = t.price - :amount")
    int decreaseAllProductsPrice(@Param("amount") BigDecimal amount);

    @Modifying
    @Transactional
    @Query("UPDATE StockSize ss SET ss.stock = ss.stock - :quantity WHERE ss.id = :stockSizeId AND ss.stock >= :quantity")
    void reduceStock(@Param("stockSizeId") UUID stockSizeId, @Param("quantity") int quantity);

    @Query("""
    SELECT DISTINCT p FROM Product p
    WHERE
        (:subCategory IS NULL OR p.subCategory = :subCategory)
        AND (p.isActive = true)
""")
    Page<Product> findProductsByFilters(
            @Param("subCategory") String subCategory,
            Pageable pageable
    );
}
