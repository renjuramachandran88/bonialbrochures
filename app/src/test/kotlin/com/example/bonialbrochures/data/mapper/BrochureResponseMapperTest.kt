package com.example.bonialbrochures.data.mapper

import com.example.bonialbrochures.data.entity.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BrochureResponseMapperTest {
    @InjectMocks
    private lateinit var subject: BrochureResponseMapper

    @Test
    fun mapNetworkResponseToLocalEntity_givenBrochureResponseFromNetwork_returnsBrochureEntityList() {
        val actual = subject.mapNetworkResponseToLocalEntity(buildResponse())

        assertThat(actual.size).isEqualTo(3)

        assertThat(actual[0])
            .extracting(
                "id",
                "contentType",
                "brochureImage",
                "retailerName",
                "distance"
            ).containsExactly(
                1,
                "brochure",
                "brochureImage1",
                "name1",
                1.0f
            )

        assertThat(actual[1])
            .extracting(
                "id",
                "contentType",
                "brochureImage",
                "retailerName",
                "distance"
            ).containsExactly(
                3,
                "brochurePremium",
                "brochureImage3",
                "name3",
                3.0f
            )
        assertThat(actual[2])
            .extracting(
                "id",
                "contentType",
                "brochureImage",
                "retailerName",
                "distance"
            ).containsExactly(
                4,
                "brochure",
                "brochureImage4",
                "name4",
                4.0f
            )
    }

    private fun buildResponse(): List<Contents> {
        return listOf(
            Contents(
                "placement1",
                "ad1",
                "content1",
                "brochure",
                buildExternalTracking(1),
                buildBrochureContentList(1),
            ),
            Contents(
                "placement2",
                "ad2",
                "content2",
                "other",
                buildExternalTracking(2),
                buildBrochureContentList(2),
            ),
            Contents(
                "placement3",
                "ad3",
                "content3",
                "brochurePremium",
                buildExternalTracking(3),
                buildBrochureContentList(3),
            ),
            Contents(
                "placement4",
                "ad4",
                "content4",
                "brochure",
                buildExternalTracking(4),
                buildBrochureContentList(4),
            ),
            Contents(
                "placement5",
                "ad5",
                "content5",
                "storyCarousel",
                buildExternalTracking(5),
                buildBrochureContentList(5),
            )
        )
    }

    private fun buildExternalTracking(number: Int): ExternalTracking {
        return ExternalTracking(emptyList(), emptyList())
    }

    private fun buildBrochureContentList(number: Int): ContentList {
        return ContentList(
            listOf(
                BrochureContent(
                    number,
                    "title$number",
                    "link$number",
                    buildExternalTracking(number),
                    buildContent(number),
                    "campaignItemId$number",
                    "url$number",
                    "url$number",
                    "teaser$number",
                    "validFrom$number",
                    "validUntil",
                    retailer = buildRetailer(number),
                    brochureImage = "brochureImage$number",
                    distance = number.toFloat()
                )
            )
        )
    }

    private fun buildContent(number: Int): Content {
        return Content(
            "id$number",
            number.toLong(),
            "publishedFrom$number",
            "publishedUntil$number",
            "url$number",
            "url$number",
            "type$number",
        )
    }

    private fun buildRetailer(number: Int): Retailer {
        return Retailer(
            number, "name$number"
        )

    }

}