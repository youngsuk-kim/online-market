package me.bread.product.infrastructure.jpa.repository

import me.bread.product.domain.entity.Product
import me.bread.product.domain.repository.ProductRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class ProductRepositoryAdapter(
    private val productJpaRepository: ProductJpaRepository,
) : ProductRepository {
    override fun findById(id: Long): Product? {
        return productJpaRepository.findByIdOrNull(id)
            .let {
                Product(
                    id = it!!.id,
                    name = it.name,
                    price = it.price,
                )
            }
    }
}
