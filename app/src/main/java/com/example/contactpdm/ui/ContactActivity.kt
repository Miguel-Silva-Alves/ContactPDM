package com.example.contactpdm.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.contactpdm.R
import com.example.contactpdm.databinding.ActivityContactBinding
import com.example.contactpdm.model.Contact

class ContactActivity : AppCompatActivity() {

    private val act: ActivityContactBinding by lazy {
        ActivityContactBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(act.root)

        act.toolbarIn.toolbar.apply {
            subtitle = this@ContactActivity.javaClass.simpleName
            setSupportActionBar(this)
        }
        act.saveBt.setOnClickListener{
            Contact(
                id = hashCode(),
                name = act.nameEt.text.toString(),
                address = act.addressEt.text.toString(),
                phone = act.phoneEt.text.toString(),
                email = act.emailEt.text.toString()
            ).let { contact ->
                Intent().apply {
                    putExtra("EXTRA_CONTACT", contact)
                    setResult(RESULT_OK, this)
                    finish()
                }
            }
        }
    }
}