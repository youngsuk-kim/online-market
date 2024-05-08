package me.bread.order.infrastructure.external

import me.bread.order.application.annotation.Local
import me.bread.order.application.annotation.LocalDev
import me.bread.order.application.external.ProductApi
import org.springframework.stereotype.Component

@Local
@LocalDev
@Component
class ProductFakeApi : ProductApi {
    override fun isProductQuantityEnough(itemId: Long) = true
}
