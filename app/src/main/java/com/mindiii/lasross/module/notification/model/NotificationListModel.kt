package com.mindiii.lasross.module.notification.model

data class NotificationListModel(
        val `data`: List<Data>,
        val status: String,
        val count: String
)

data class Data(
        val created_on: String,
        val current_time: String,
        val is_read: String,
        val notificationId: String,
        val notification_by: String,
        val notification_for: String,
        val notification_message: String,
        val notification_payload: String,
        val notification_title: String,
        val notification_type: String,
        val reference_id: String,
        val updated_on: String,
        val web_push: String,
        val product_image: String
)

/*
"notificationId": "45",
"notification_by": "1",
"notification_for": "275",
"reference_id": "151",
"notification_type": "Order Status",
"notification_title": "Order Delivered",
"notification_message": "Your order with reference number FBAN191445082410 has been Delivered",
"notification_payload": "{\"type\":\"Order Status\",\"body\":\"Your order with reference number FBAN191445082410 has been Delivered\",\"title\":\"Order Delivered\",\"sound\":\"default\",\"order_id\":\"151\"}",
"is_read": "0",
"web_push": "0",
"updated_on": "2019-10-24 08:55:20",
"created_on": "2019-10-24 08:55:20",
"current_time": "2019-11-01 13:39:16",
"product_image": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/lObT1GCRZQBt3nku.jpg"*/
