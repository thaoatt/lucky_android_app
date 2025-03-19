package com.example.luckyandroidapp

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.android.billingclient.api.Purchase
import com.example.luckyandroidapp.utils.BillingController
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initBilling()
    }

    companion object {
        lateinit var preference: SharedPreferences
            private set
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun initBilling() {
        BillingController.onItemPurchased = { purchase, _ ->
            GlobalScope.launch {
                runCatching {

                }.onSuccess {
//                    purchase.purchaseState == Purchase.PurchaseState.PURCHASED
                    BillingController.consume(purchase)
                }.onFailure {
                    withContext(Dispatchers.Main) {
                        // Handle error
                        }
                }
            }
        }
        BillingController.onItemConsumed = { productDetails ->
            productDetails.oneTimePurchaseOfferDetails?.let { offerDetails ->
                Log.e("BillingDetail", "onItemConsumed: - ${productDetails.productId} - ${offerDetails.priceCurrencyCode}", )
            }
        }
        BillingController.init(this)
    }
}