package com.mindiii.lasross.firebase_notification.model

import java.io.Serializable


data class NotificationModel(var reference_id: String? = "",
                             var click_action: String? = "",
                             var message: String? = "",
                             var orderId: String? = "",
                             var tittle: String? = "") : Serializable