package com.zubet.penjualan_anindya.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
 class tb_barang (
    @PrimaryKey (autoGenerate = true)
    val id_brg : Int,
    val nm_brg : String,
    val hrg_brg : Int,
    val stok : Int
        )