package com.mindiii.lasross.base;


import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by anil
 * Date: 23/05/19
 * Time: 03:07 PM
 */

public interface ClickListener {


    interface MenuChildClick {
        void onClick(String position, String catName);
    }

    interface HomeFooterClick {
        void onClick(int position);
    }

    interface SignatureSendListener {
        void onClick(Uri signatureUrl, Bitmap bitmap, String imgCheckVal);
    }

    interface WishListClickListener {
        void onWishClick(int position);

        void onProductClick(int position);
    }

    interface SimilarProductClickListener {
        void onProfileDetail(int position);

        void onClickToWish(int position);
    }

    interface WishListListener {
        void onDelete(int position);

        void onItemClick(int position);

        void onAddToCartClick(int position);
    }

    interface FilterClickListener {
        void onSizeClick(String size);

        void onColorClick(String color);

        void onSizeRemoveClick(String size);

        void onColorRemoveClick(String color);
    }

    interface CartItemClickListener {
        void onItenIncreaseClick(int position);

        void onItenDecreaseClick(int position);

        void onItenDeleteClick(int position);
    }

    interface MyAddressListener {
        void onItemDelete(int position);

        void onItemUpdate(int position);

        void onCheckBoxClick(int position);

    }

    interface CardClickListener {
        void deleteOnClick(int position);

        void setCardDataOnClick(int position, String cardId);
    }

    interface MyOrderListener {
        void onItemClick(int position);
    }

    interface OrderDetailsListner {
        void onItemClick(String productId, float rating, String description, int AdapterPosition, String item_id);
    }

    interface NotificationListener {
        void onItemClick(int position);
    }

}