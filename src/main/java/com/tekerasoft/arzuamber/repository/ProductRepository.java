package com.tekerasoft.arzuamber.repository;

import com.tekerasoft.arzuamber.model.Product;
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
    Page<Product> findByLangIgnoreCase(String lang, Pageable pageable);

    Optional<Product> findByLangIgnoreCaseAndSlugIgnoreCase(String lang, String slug);

    @Query("SELECT t FROM Product t WHERE t.newSeason = true AND t.lang = :lang")
    List<Product> findByNewSeasonAndLang(@Param("lang") String lang, Pageable pageable);

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


    @Query("""
    SELECT DISTINCT p FROM Product p
    JOIN p.colorSize cs
    JOIN cs.sizeStock ss
    WHERE
        (COALESCE(:color, cs.color) = cs.color)
        AND (COALESCE(:size, ss.size) = ss.size)
        AND (COALESCE(:category, p.category) = p.category)
        AND (COALESCE(:length, p.length) = p.length)
        AND (COALESCE(:lang, p.lang) = p.lang)
""")
    List<Product> findProductsByFilters(
            @Param("color") String color,
            @Param("size") String size,
            @Param("category") String category,
            @Param("length") String length,
            @Param("lang") String lang
    );
}
