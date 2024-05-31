package netwok

import kotlinx.serialization.Serializable

@Serializable
data class OrderResponse(
    val totalPrice: Int,
    val coupon: String?,
    val discount: Int,
    val requestId: String
)
@Serializable
data class OrderedItem(
    val productId: String,
    val quantity: Int
)
@Serializable
data class OrderRequest(
    val userId: String,
    val coupon: String?,
    val items: List<OrderedItem>,
)
@Serializable
data class PurchasedResponse(
    var purchaseId: String? = null,
    var discountId: String? = null,
    var returnExpireDate: String? = null
)
@Serializable
data class ProductReturnRequestResponse(
    val message:String
)
@Serializable
data class ProductReturnRequestEntity(
    val purchaseId: String,
    val returnQuantity: String
)
