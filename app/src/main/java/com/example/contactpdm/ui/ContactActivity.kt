package com.example.contactpdm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.contactpdm.R
import com.example.contactpdm.databinding.ActivityContactBinding

class ContactActivity : AppCompatActivity() {

    private val act: ActivityContactBinding by lazy {
        ActivityContactBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(act.root)
    }
}