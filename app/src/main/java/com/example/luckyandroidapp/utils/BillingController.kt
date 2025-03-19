package com.example.luckyandroidapp.utils

import android.app.Activity
import android.app.Application
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import com.android.billingclient.api.queryProductDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import com.android.billingclient.api.ConsumeParams
import com.android.billingclient.api.BillingFlowParams

object BillingController : BillingClientStateListener {
    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private lateinit var billingClient: BillingClient
    val billingResponseCode = MutableSharedFlow<Int>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    private var inAppProductIdList: List<String> = listOf()
    private var productIDs = listOf<String>()
    var productDetails: List<ProductDetails>? = null
    var onItemPurchased: (Purchase, String) -> Unit = { _, _ -> }
    var onItemConsumed: (ProductDetails) -> Unit = {}

    private val purchasesUpdatedListener = PurchasesUpdatedListener { billingResult, purchases ->
        billingResponseCode.tryEmit(billingResult.responseCode)
        when (billingResult.responseCode) {
            BillingClient.BillingResponseCode.OK -> {
                coroutineScope.launch {
                    if (inAppProductIdList.isEmpty()) {
                        val inAppList = productIDs.map {
                            QueryProductDetailsParams.Product.newBuilder()
                                .setProductId(it)
                                .setProductType(BillingClient.ProductType.INAPP)
                                .build()
                        }
                        inAppProductIdList = billingClient
                            .queryProductDetails(
                                QueryProductDetailsParams.newBuilder()
                                    .setProductList(inAppList)
                                    .build()
                            )
                            .productDetailsList?.map { it.productId } ?: listOf()
                    }

                    purchases
                        ?.filterNot { it.isAcknowledged }
                        ?.forEach { purchase ->
                            onItemPurchased(purchase, BillingClient.ProductType.INAPP)
                        }
                }
            }

            else -> {}
        }
    }

    fun init(application: Application) {
        billingClient = BillingClient.newBuilder(application)
            .enablePendingPurchases()
            .setListener(purchasesUpdatedListener)
            .build()
        billingClient.startConnection(this)
    }

    override fun onBillingServiceDisconnected() {
        coroutineScope.launch {
            delay(66_000)
            billingClient.startConnection(this@BillingController)
        }
    }

    override fun onBillingSetupFinished(p0: BillingResult) {
        restorePurchases()
    }

    fun restorePurchases() {
        if (billingClient.isReady) {
            billingClient.queryPurchasesAsync(
                QueryPurchasesParams.newBuilder()
                    .setProductType(BillingClient.ProductType.INAPP)
                    .build()
            ) { result, purchases ->
                if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                    purchases.forEach { onItemPurchased(it, BillingClient.ProductType.INAPP) }
                }
            }
        }
    }

    fun consume(purchase: Purchase) {
        billingClient.consumeAsync(
            ConsumeParams.newBuilder()
                .setPurchaseToken(purchase.purchaseToken)
                .build()
        ) { billingResult, _ ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                logPurchase(purchase)
            }
        }
    }

    suspend fun queryProductDetails(productIdList: List<String>): List<ProductDetails>? {
        if (productIdList.isEmpty()) return null

        val productDetailsParams = QueryProductDetailsParams.newBuilder()
            .setProductList(
                productIdList.map {
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(it)
                        .setProductType(BillingClient.ProductType.INAPP)
                        .build()
                }
            )
            .build()
        val productDetailsResult = billingClient.queryProductDetails(productDetailsParams)
        val code = productDetailsResult.billingResult.responseCode
        billingResponseCode.tryEmit(code)
        return if (code == BillingClient.BillingResponseCode.OK) {
            productDetailsResult.productDetailsList
        } else {
            null
        }
    }

    fun purchase(activity: Activity, productDetails: ProductDetails) {
        val productDetailsParamsBuilder = BillingFlowParams.ProductDetailsParams.newBuilder()
            .setProductDetails(productDetails)
        val params = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(listOf(productDetailsParamsBuilder.build()))
            .build()
        val billingResult = billingClient.launchBillingFlow(activity, params)
        billingResponseCode.tryEmit(billingResult.responseCode)
    }

    private fun logPurchase(purchase: Purchase) {
        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(
                purchase.products.map {
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(it)
                        .setProductType(BillingClient.ProductType.INAPP)
                        .build()
                }
            )
            .build()
        billingClient.queryProductDetailsAsync(params) { billingResult, productDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                productDetailsList.forEach { onItemConsumed(it) }
            }
        }
    }

    fun close() {
        billingClient.endConnection()
    }
}
