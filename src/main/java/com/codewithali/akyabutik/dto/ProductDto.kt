package com.codewithali.akyabutik.dto
import com.codewithali.akyabutik.model.ColorSize
import com.codewithali.akyabutik.model.Product
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class ProductDto @JvmOverloads constructor (

    @JsonProperty("name")
    val name: String,
    @JsonProperty("price")
    val price: Double,
    @JsonProperty("stock")
    val stock: Int,
    @JsonProperty("sex")
    val sex: String,
    @JsonProperty("description")
    val description: String,
    @JsonProperty("colorSize")
    val colorSize: List<ColorSize>,
    @JsonProperty("mainCategory")
    val mainCategory: String,
    @JsonProperty("subCategory")
    val subCategory: String,

    val images: List<String>? = null,
    val createdAt: LocalDateTime? = null,

    val id: String? = null,
) {
    companion object {
        @JvmStatic
        fun convertToDto(from: Product): ProductDto {
            return ProductDto(
                from.name,
                from.price,
                from.stock,
                from.sex,
                from.description,
                from.colorSize,
                from.mainCategory,
                from.subCategory,
                from.images,
                null,
                from.id,
            )
        }

        @JvmStatic
        fun convertToEntity(from: ProductDto): Product {
            return Product(
                from.name,
                from.price,
                from.stock,
                from.sex,
                from.description,
                from.colorSize,
                from.mainCategory,
                from.subCategory,
                from.images,
                null,
                from.id
            )
        }
    }
}
