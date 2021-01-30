package com.mindiii.lasross.module.settings.model

data class TermsPolicyResponse(
        val content: Content,
        val content_url: ContentUrl,
        val message: String,
        val status: String
)

data class Content(
        val policy: String,
        val termAndCondition: String
)

data class ContentUrl(
        val policy: String,
        val term_and_condition: String
)