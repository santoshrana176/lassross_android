package com.mindiii.lasross.module.address.model

data class GetAddressResponse(
        val `data`: Data,
        val message: String,
        val status: String
)

data class Data(
        val total_records: String,
        val user_addresslist: List<UserAddresslist>
)

data class UserAddresslist(
        val bill_address_city: String,
        val bill_address_company_name: String,
        val bill_address_country: String,
        val bill_address_email: String,
        val bill_address_first_name: String,
        val bill_address_house_number: String,
        val bill_address_last_name: String,
        val bill_address_latitude: String,
        val bill_address_locality: String,
        val bill_address_location: String,
        val bill_address_longitude: String,
        val bill_address_phone: String,
        val bill_address_zip_code: String,
        val created_on: String,
        val ship_address_city: String,
        val ship_address_company_name: String,
        val ship_address_country: String,
        val ship_address_first_name: String,
        val ship_address_house_number: String,
        val ship_address_last_name: String,
        val ship_address_latitude: String,
        val ship_address_locality: String,
        val ship_address_location: String,
        val ship_address_longitude: String,
        val ship_address_zip_code: String,
        val updated_on: String,
        val userAddressId: String,
        val user_id: String
)