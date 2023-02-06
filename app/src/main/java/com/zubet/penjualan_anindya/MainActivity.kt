package com.zubet.penjualan_anindya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.zubet.penjualan_anindya.room.db_penjualan
import com.zubet.penjualan_anindya.room.tb_barang
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    val db by lazy { db_penjualan(this) }
    lateinit var BarangAdapter: barangAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerview()
        createbutton()
    }

    private fun createbutton() {
        Btninput.setOnClickListener {
            intentEdit(0,Constant.TYPE_CREATE)
        }
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }
    fun loadData(){
        CoroutineScope(Dispatchers.IO).launch {
            val brg = db.BarangDao().tampilBarang()
            Log.d("MainActivity","dbresponse:$brg")
            withContext(Dispatchers.Main){
            BarangAdapter.setData(brg)
        }
    }
    }
    /*fun createbutton(view:View){
        val ganti = Intent(this,EditActivity::class.java)
        startActivity(ganti)

        Btninput.setOnClickListener{
            intentEdit(0,Constant.TYPE_CREATE)
        }
    }*/
    fun intentEdit(tbsisnis:Int,intentType:Int){
        startActivity(Intent(applicationContext,EditActivity::class.java)
            .putExtra("intent_nis",tbsisnis)
            .putExtra("intent_type",intentType)
        )
    }
    fun setupRecyclerview(){
        BarangAdapter = barangAdapter(arrayListOf(),object : barangAdapter.onAdapterListener{
            override fun onClick(tbbar: tb_barang) {
                intentEdit(tbbar.id_brg,Constant.TYPE_READ)
            }

            override fun onUpdate(tbbar: tb_barang) {
                intentEdit(tbbar.id_brg,Constant.TYPE_UPDATE)
             }

            override fun onDelete(tbbar: tb_barang) {
                deleteAlert(tbbar)
            }

        })
        Listbarang.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter =BarangAdapter
        }
    }
    private fun deleteAlert(tbBarang: tb_barang){
        val dialog = AlertDialog.Builder(this)
        dialog.apply {
            setTitle("Konfirmasi Hapus")
            setMessage("Yakin Hapus ${tbBarang.nm_brg}?")
            setNegativeButton("Batal"){dialogInterface,i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus"){dialogInterface, i ->
                CoroutineScope(Dispatchers.IO).launch {
                    db.BarangDao().deleteBarang(tbBarang)
                    dialogInterface.dismiss()
                    loadData()
                }
            }
        }
        dialog.show()
    }
}