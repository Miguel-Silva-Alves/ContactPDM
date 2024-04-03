package com.example.contactpdm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.contactpdm.databinding.ActivityMainBinding
import com.example.contactpdm.model.Contact

class MainActivity : AppCompatActivity() {

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

        fillContacts()

        amb.contactlist.adapter = contactAdapter
    }

    private fun fillContacts() {
        for (i in 1..50) {
            contactList.add(
                Contact(i, "Nome $i", "Address $i", "Phone $i", "Email $i")
            )
        }
    }
}