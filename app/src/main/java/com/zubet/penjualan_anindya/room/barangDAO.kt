package com.zubet.penjualan_anindya.room

import androidx.room.*

@Dao
interface barangDAO {
    @Insert
    fun addBarang (barang:tb_barang)
    @Update
    fun updateBarang (barang: tb_barang)
    @Delete
    fun deleteBarang (barang: tb_barang)
    @Query ("SELECT * FROM tb_barang")
    fun tampilBarang():List<tb_barang>
    @Query("SELECT *FROM tb_barang WHERE id_brg=:tbsiswa_nis")
    fun tampilid(tbsiswa_nis: Int): List<tb_barang>
}