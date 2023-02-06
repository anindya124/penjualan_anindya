package com.zubet.penjualan_anindya

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.zubet.penjualan_anindya.room.db_penjualan
import com.zubet.penjualan_anindya.room.tb_barang
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {
    val db by lazy { db_penjualan(this) }
    private var tbbarbar : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        simpandata()
        setupView()
        tbbarbar= intent.getIntExtra("intent_nis",tbbarbar)
        Toast.makeText(this,tbbarbar.toString(), Toast.LENGTH_SHORT).show()
    }
    fun setupView(){
        val intenType = intent.getIntExtra("intent_type",0)
        when(intenType){
            Constant.TYPE_CREATE ->{
                Btnupdate.visibility=View.GONE

            }
            Constant.TYPE_READ ->{
                Btnsave.visibility= View.GONE
                Btnupdate.visibility= View.GONE
                tampilkanbarang()
            }
            Constant.TYPE_UPDATE ->{
                Btnsave.visibility= View.GONE
                tampilkanbarang()

            }
        }
    }
    fun simpandata(){
        Btnsave.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.BarangDao().addBarang(
                    tb_barang(ETid.text.toString().toInt(),ETnama.text.toString(),ETharga.text.toString().toInt(),ETstok.text.toString().toInt())
                )
                finish()
            }
        }
        Btnupdate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.BarangDao().updateBarang(
                    tb_barang(tbbarbar,ETnama.text.toString(),ETharga.text.toString().toInt(),ETstok.text.toString().toInt())
                )
                finish()
            }
        }
    }
    fun tampilkanbarang(){
        tbbarbar = intent.getIntExtra("intent_nis",0)
        CoroutineScope(Dispatchers.IO).launch {
            val gudang  =db.BarangDao().tampilid(tbbarbar).get(0)
            //ETid.setText(siswa.id_brg)
            ETnama.setText(gudang.nm_brg)
            ETharga.setText(gudang.hrg_brg)
            ETstok.setText(gudang.stok)
        }
    }
}