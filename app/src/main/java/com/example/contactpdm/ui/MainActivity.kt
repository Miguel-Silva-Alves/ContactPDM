package com.example.contactpdm.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.contactpdm.R
import com.example.contactpdm.databinding.ActivityMainBinding
import com.example.contactpdm.model.Contact

class MainActivity : AppCompatActivity() {

    /* Tarefa
        Criar tela ContactActivity
        nameEt
        addressEt,
        phoneEt
        emailEt
        saveBtn
    * */

    private lateinit var parl: ActivityResultLauncher<Intent> // parametro activity result launcher (parl)
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val contactList: MutableList<Contact> = mutableListOf()

    // Adapter
    private val contactAdapter: ArrayAdapter<String> by lazy {
        ArrayAdapter(this, android.R.layout.simple_list_item_1, contactList.map { it.name })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        amb.toolbarIn.apply {
            setSupportActionBar(this.toolbar)
        }

        fillContacts()

        amb.contactlist.adapter = contactAdapter

        // Parl configuration
        parl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            object: ActivityResultCallback<ActivityResult> {
                override fun onActivityResult(result: ActivityResult) {

                }
            })
    }

    private fun fillContacts() {
        for (i in 1..50) {
            contactList.add(
                Contact(i, "Nome $i", "Address $i", "Phone $i", "Email $i")
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean{
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        return when(item.itemId){
            R.id.viewMi -> {
//                val url: Uri = Uri.parse(amb.parametroTv.text.toString())
//                val navegadorIntent: Intent = Intent(ACTION_VIEW, url)
//                startActivity(navegadorIntent)
                Intent(this, ContactActivity::class.java).also{
//                    it.putExtra(PARAMETRO_EXTRA, amb.parametroTv.text.toString()) // chave e valor -> envia o valor do text view pro campo de input na outra tela
                    parl.launch(it)
                }
                println("VIEWMI")
                true }

            else -> {false}
        }
    }

}