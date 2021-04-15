package com.shy.study.repository.entity
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "banner"
)
data class Banner(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @SerializedName("data")
    var banners: List<Data>? = listOf(),
    @Ignore
    @SerializedName("errorCode")
    var errorCode: Int? = 0,
    @Ignore
    @SerializedName("errorMsg")
    var errorMsg: String? = ""
) {
    data class Data(
        @SerializedName("desc")
        var desc: String? = "",
        @SerializedName("id")
        var id: Int? = 0,
        @SerializedName("imagePath")
        var imagePath: String? = "",
        @SerializedName("isVisible")
        var isVisible: Int? = 0,
        @SerializedName("order")
        var order: Int? = 0,
        @SerializedName("title")
        var title: String? = "",
        @SerializedName("type")
        var type: Int? = 0,
        @SerializedName("url")
        var url: String? = ""
    )
}