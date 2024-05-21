package com.arif.gatchafood

import com.google.gson.annotations.SerializedName

data class RestaurantResponseItem(

	@field:SerializedName("range_harga")
	val rangeHarga: String? = null,

	@field:SerializedName("jumlah_ulasan")
	val jumlahUlasan: Int? = null,

	@field:SerializedName("rating")
	val rating: Double? = null,

	@field:SerializedName("judul")
	val judul: String? = null,

	@field:SerializedName("ringkasan")
	val ringkasan: List<String?>? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null
)
