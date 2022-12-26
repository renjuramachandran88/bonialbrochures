package com.example.bonialbrochures.data.entity

import com.google.gson.annotations.SerializedName

data class BrochureContent(
    var id: Int? = null,
    var title: String? = null,
    var link: String? = null,
    var externalTracking: ExternalTracking? = null,
    @SerializedName("content")
    var content: Content? = null,
    @SerializedName("campaign_item_id")
    var campaignItemId: String? = null,
    @SerializedName("imgURL")
    var imageURL: String? = null,
    @SerializedName("imageUrl")
    var imageUrl2: String? = null,
    var teaserText: String? = null,
    var validFrom: String? = null,
    var validUntil: String? = null,
    var publishedFrom: String? = null,
    var publishedUntil: String? = null,
    var type: String? = null,
    var pageCount: Int? = null,
    var publisher: Publisher? = null,
    var retailer: Retailer? = null,
    var brochureImage: String? = null,
    val badges: List<String>? = null,
    var isEcommerce: Boolean? = null,
    var distance: Float? = null,
    var hideValidityDate: Boolean? = null,
    var publisherId: String? = null,
    var publisherImage: String? = null,
    val items: List<Item>? = null


)
