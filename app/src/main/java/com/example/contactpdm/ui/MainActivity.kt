package com.example.contactpdm.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.contactpdm.R
import com.example.contactpdm.adapter.ContactAdapter
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
    private val contactAdapter: ContactAdapter by lazy {
        ContactAdapter(this, contactList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        amb.toolbarIn.apply {
            setSupportActionBar(this.toolbar)
        }

        fillContacts()

        amb.contactlist.adapter = contactAdapter
        registerForContextMenu(amb.contactlist)

        amb.contactlist.setOnItemClickListener{_, _, position, _ ->
            val contact = contactList[position]
            val viewContactIntent = Intent(this@MainActivity, ContactActivity::class.java)
            viewContactIntent.putExtra("EXTRA_CONTACT", contact)
            viewContactIntent.putExtra("EXTRA_VIEW_CONTACT", true)
            startActivity(viewContactIntent)
        }

        // Parl configuration
        parl = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val contact = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra("EXTRA_CONTACT", Contact::class.java)
                } else {
//                    TODO("VERSION.SDK_INT < TIRAMISU")
                    result.data?.getParcelableExtra("EXTRA_CONTACT")
                }
                contact?.let {newOrEditContact ->
                    val position = contactList.indexOfFirst { it.id == newOrEditContact.id }
                    if (position != -1){
                        // Contato já existe
                        contactList[position] = newOrEditContact
                    }else{
                        // Contato não existe e foi adicionado
                        contactList.add(newOrEditContact)
                    }

                    contactAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun fillContacts() {
        for (i in 1..10) {
            contactList.add(
                Contact(i, "Nome $i", "Address $i", "Phone $i", "Email $i")
            )
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterContextMenuInfo).position
        return when(item.itemId){
            R.id.removeContactMi -> {
                contactList.removeAt(position)
                contactAdapter.notifyDataSetChanged()
                Toast.makeText(this, "Contact removed", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.editContactMi -> {
                val contact = contactList[position]
                parl.launch(
                    Intent(this, ContactActivity::class.java).apply {
                        putExtra("EXTRA_CONTACT", contact)
                    }
                )
                true
            }

            else -> {false}
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