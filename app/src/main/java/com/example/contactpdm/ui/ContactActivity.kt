package com.example.contactpdm.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.INVISIBLE
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

        val receivedContact = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            intent.getParcelableExtra("EXTRA_CONTACT", Contact::class.java)
        }else{
            intent.getParcelableExtra("EXTRA_CONTACT")
        }

        receivedContact?.let{
            act.nameEt.setText(receivedContact.name)
            act.addressEt.setText(receivedContact.address)
            act.phoneEt.setText(receivedContact.phone)
            act.emailEt.setText(receivedContact.email)
            if(intent.getBooleanExtra("EXTRA_VIEW_CONTACT", false)){
                act.nameEt.isEnabled = false
                act.addressEt.isEnabled = false
                act.phoneEt.isEnabled = false
                act.emailEt.isEnabled = false
                act.saveBt.visibility = INVISIBLE
            }
        }
        act.saveBt.setOnClickListener{
            Contact(
                id = receivedContact?.id?:hashCode(),
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