package com.mindiii.lasross.module.cart.presenter

import android.content.Context
import com.mindiii.lasross.Lasross
import com.mindiii.lasross.R
import com.mindiii.lasross.app.session.Session
import com.mindiii.lasross.base.ApiCallback
import com.mindiii.lasross.base.errorResponse.ErrorUtils
import com.mindiii.lasross.module.cart.model.CartClearAllResponse
import com.mindiii.lasross.module.cart.model.CartItemIncreaseResponse
import com.mindiii.lasross.module.cart.model.CartListResponse
import com.mindiii.lasross.module.cart.model.DeleteCartItemResponse
import com.mindiii.lasross.network.API
import com.mindiii.lasross.network.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class GetCartPresenter(var mContext: Context, var getCartCallback: ApiCallback.GetCartCallback) {

    /*Get all cart Item*/
    fun callCartListApi(offset: String) {
        val session = Session(mContext)
        getCartCallback.onShowBaseLoader()
        val api = ServiceGenerator.createService(API::class.java)
        val cartListApi = api.callCartList(Lasross.appLanguage,session.authToken, offset, "18")
        cartListApi.enqueue(object : Callback<CartListResponse> {
            override fun onResponse(call: Call<CartListResponse>, response: Response<CartListResponse>) {
                getCartCallback.onHideBaseLoader()
                if (response.isSuccessful) {
                    getCartCallback.onSuccessGetCart(response.body())
                } else {
                    val apiErrors = ErrorUtils.parseError(response)
                    if (apiErrors.message == "Invalid token") {
                        getCartCallback.onTokenChangeError(apiErrors.message)
                    } else {
                        getCartCallback.onError(apiErrors.message)
                    }
                }
            }

            override fun onFailure(call: Call<CartListResponse>, t: Throwable) {
                getCartCallback.onHideBaseLoader()
                if (t is IOException) {
                 } else {
                    getCartCallback.onError(mContext.getString(R.string.oops_wrong))
                }
            }
        })
    }

    /*Increase Item quantity*/
    fun callUpdateCartListApi(cartItemId: String, productId: String, productQuantity: String) {
        val session = Session(mContext)
        getCartCallback.onShowBaseLoader()
        val api = ServiceGenerator.createService(API::class.java)
        val cartListApi = api.callUpdateCart(Lasross.appLanguage,session.authToken, cartItemId, productId, productQuantity)
        cartListApi.enqueue(object : Callback<CartItemIncreaseResponse> {
            override fun onResponse(call: Call<CartItemIncreaseResponse>, response: Response<CartItemIncreaseResponse>) {
                getCartCallback.onHideBaseLoader()
                if (response.isSuccessful) {
                    getCartCallback.onSuccessUpdateCartItem(response.body())
                } else {
                    val apiErrors = ErrorUtils.parseError(response)
                    if (apiErrors.message == "Invalid token") {
                        getCartCallback.onTokenChangeError(apiErrors.message)
                    } else {
                        getCartCallback.onError(apiErrors.message)
                    }
                }
            }

            override fun onFailure(call: Call<CartItemIncreaseResponse>, t: Throwable) {
                getCartCallback.onHideBaseLoader()
                if (t is IOException) {
                    //getCartCallback.onError(mContext.getString(R.string.internet_connection))
                } else {
                    getCartCallback.onError(mContext.getString(R.string.oops_wrong))
                }
            }
        })
    }


    /*Decrease Item Quantity*/
    fun callUpdateDecreaseApi(cartItemId: String, productId: String, productQuantity: String) {
        val session = Session(mContext)
        getCartCallback.onShowBaseLoader()
        val api = ServiceGenerator.createService(API::class.java)
        val cartListApi = api.callUpdateDecrementCart(Lasross.appLanguage,session.authToken, cartItemId, productId, productQuantity)
        cartListApi.enqueue(object : Callback<CartItemIncreaseResponse> {
            override fun onResponse(call: Call<CartItemIncreaseResponse>, response: Response<CartItemIncreaseResponse>) {
                getCartCallback.onHideBaseLoader()
                if (response.isSuccessful) {
                    getCartCallback.onSuccessUpdateCartDecreaseItem(response.body())
                } else {
                    val apiErrors = ErrorUtils.parseError(response)
                    if (apiErrors.message == "Invalid token") {
                        getCartCallback.onTokenChangeError(apiErrors.message)
                    } else {
                        getCartCallback.onError(apiErrors.message)
                    }
                }
            }

            override fun onFailure(call: Call<CartItemIncreaseResponse>, t: Throwable) {
                getCartCallback.onHideBaseLoader()
                if (t is IOException) {
                    //getCartCallback.onError(mContext.getString(R.string.internet_connection))
                } else {
                    getCartCallback.onError(mContext.getString(R.string.oops_wrong))
                }
            }
        })
    }

    /*Delete cart Item */
    fun callDeleteCartItemApi(cartItemId: String) {
        val session = Session(mContext)
        getCartCallback.onShowBaseLoader()
        val api = ServiceGenerator.createService(API::class.java)
        val delteCartItemApi = api.callDeleteCartItem(Lasross.appLanguage,session.authToken, cartItemId)
        delteCartItemApi.enqueue(object : Callback<DeleteCartItemResponse> {
            override fun onResponse(call: Call<DeleteCartItemResponse>, response: Response<DeleteCartItemResponse>) {
                getCartCallback.onHideBaseLoader()
                if (response.isSuccessful) {
                    getCartCallback.onDeleteCartItem(response.body())
                } else {
                    val apiErrors = ErrorUtils.parseError(response)
                    if (apiErrors.message == "Invalid token") {
                        getCartCallback.onTokenChangeError(apiErrors.message)
                    } else {
                        getCartCallback.onError(apiErrors.message)
                    }
                }
            }

            override fun onFailure(call: Call<DeleteCartItemResponse>, t: Throwable) {
                getCartCallback.onHideBaseLoader()
                if (t is IOException) {
                    //getCartCallback.onError(mContext.getString(R.string.internet_connection))
                } else {
                    getCartCallback.onError(mContext.getString(R.string.oops_wrong))
                }
            }
        })
    }


    /*Clear Cart List Api*/
    fun callClearAllItemApi() {
        val session = Session(mContext)
        getCartCallback.onShowBaseLoader()
        val api = ServiceGenerator.createService(API::class.java)
        val cartClearAllApi = api.callAllClearCartItem(Lasross.appLanguage,session.authToken)
        cartClearAllApi.enqueue(object : Callback<CartClearAllResponse> {
            override fun onResponse(call: Call<CartClearAllResponse>, response: Response<CartClearAllResponse>) {
                getCartCallback.onHideBaseLoader()
                if (response.isSuccessful) {
                    getCartCallback.onSuccessClearAllItem(response.body())
                } else {
                    val apiErrors = ErrorUtils.parseError(response)
                    if (apiErrors.message == "Invalid token") {
                        getCartCallback.onTokenChangeError(apiErrors.message)
                    } else {
                        getCartCallback.onError(apiErrors.message)
                    }
                }
            }

            override fun onFailure(call: Call<CartClearAllResponse>, t: Throwable) {
                getCartCallback.onHideBaseLoader()
                if (t is IOException) {
                    //getCartCallback.onError(mContext.getString(R.string.internet_connection))
                } else {
                    getCartCallback.onError(mContext.getString(R.string.oops_wrong))
                }
            }
        })
    }
}