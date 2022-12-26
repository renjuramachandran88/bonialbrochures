package com.example.bonialbrochures.data.entity

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import javax.inject.Inject

class BrochureContentJsonAdapter @Inject constructor(private val gson: Gson) : TypeAdapter<ContentList>() {
    override fun write(out: JsonWriter?, value: ContentList?) {}

    override fun read(jsonReader: JsonReader?): ContentList {
        val contentList = mutableListOf<BrochureContent>()

        while (jsonReader?.hasNext() == true) {
            if (jsonReader.peek() == JsonToken.BEGIN_ARRAY || jsonReader.peek() == JsonToken.BEGIN_OBJECT) {
                val nextObject = jsonReader.peek()?.name


                if (nextObject == JsonToken.BEGIN_ARRAY.toString()) {
                    jsonReader.beginArray()

                    while (jsonReader.hasNext()) {
                        val contentStuff: BrochureContent =
                            gson.fromJson(jsonReader, Content::class.java)
                        contentList.add(contentStuff)
                    }
                    jsonReader.endArray()
                    continue
                } else {
                    jsonReader.beginObject()

                    val content = BrochureContent()

                    while (jsonReader.hasNext()) {
                        when (jsonReader.nextName()) {
                            "id" -> content.id = jsonReader.nextInt()
                            "title" -> content.title = jsonReader.nextString()
                            "brochureImage" -> content.brochureImage = jsonReader.nextString()
                            "externalTracking" -> content.externalTracking =
                                gson.fromJson(jsonReader, ExternalTracking::class.java)
                            "distance" -> content.distance = jsonReader.nextDouble().toFloat()
                            "retailer" -> content.retailer =
                                gson.fromJson(jsonReader, Retailer::class.java)


                            "validFrom" -> content.validFrom = jsonReader.nextString()
                            "validUntil" -> content.validUntil = jsonReader.nextString()
                            "publishedFrom" -> content.publishedFrom = jsonReader.nextString()
                            "publishedUntil" -> content.publishedUntil = jsonReader.nextString()
                            "type" -> content.type = jsonReader.nextString()

                            "pageCount" -> content.pageCount = jsonReader.nextInt()
                            "publisher" -> content.publisher =
                                gson.fromJson(jsonReader, Publisher::class.java)

                            "isEcommerce" -> content.isEcommerce = jsonReader.nextBoolean()

                            "hideValidityDate" -> content.hideValidityDate =
                                jsonReader.nextBoolean()
                            "content" -> content.content =
                                gson.fromJson(jsonReader, Content::class.java)

                            "campaign_item_id" -> content.campaignItemId = jsonReader.nextString()
                            "link" -> content.link = jsonReader.nextString()

                            "imgURL" -> content.imageURL = jsonReader.nextString()
                            "imageUrl" -> content.imageUrl2 = jsonReader.nextString()

                            "teaserText" -> content.teaserText = jsonReader.nextString()
                            "publisherId" -> content.publisherId = jsonReader.nextString()
                            "publisherImage" -> content.publisherImage = jsonReader.nextString()

                            /**
                             * The arrays inside the Content object aren't useful to the app
                             * so we just "skip over them" by moving through the json stream
                             */
                            "badges" -> {
                                if (jsonReader.peek() == JsonToken.BEGIN_ARRAY) {
                                    jsonReader.beginArray()
                                    jsonReader.endArray()
                                }
                            }
                            "items" -> {
                                if (jsonReader.peek() == JsonToken.BEGIN_ARRAY) {
                                    jsonReader.beginArray()
                                    jsonReader.endArray()
                                }
                            }
                        }
                    }
                    /**
                     * Add the created Content object from the just deserialized json object to the overall list of Contents
                     * Then move on to the next value in the json stream
                     */
                    contentList.add(content)

                    jsonReader.endObject()
                    continue
                }

            } else {
                /**
                 * Content found is null, so skip to the next value
                 */
                jsonReader.skipValue()
                continue
            }

        }
        /**
         * When there is no other value in the stream,
         * Return the list of the deserialized Content objects
         */

        return ContentList(contentList)
    }
}