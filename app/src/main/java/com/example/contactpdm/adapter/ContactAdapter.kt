package com.example.contactpdm.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.getSystemService
import com.example.contactpdm.R
import com.example.contactpdm.databinding.TilleContactBinding
import com.example.contactpdm.model.Contact

class ContactAdapter(context: Context, private var contactList: MutableList<Contact>):
    ArrayAdapter<Contact>(context, R.layout.tille_contact, contactList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        // Pegar o contato no data source
        val contact = contactList[position]

        // Inflo uma nova célula se for necessário
        var contactTilleView = convertView
        if(contactTilleView == null){

            // Criando a célula
            contactTilleView = LayoutInflater.from(context).inflate(
                R.layout.tille_contact,
                parent,
                false
            )

            // Criando o holder
            val tilleContactHolder = TilleContactHolder(
                contactTilleView.findViewById(R.id.nameTv),
                contactTilleView.findViewById(R.id.emailTv)
            )

            // Salvando na tag (bolso)
            contactTilleView.tag = tilleContactHolder

        }

        // Inserir os valores na célula
        (contactTilleView?.tag as TilleContactHolder).apply {
            this.nameTv.text = contact.name
            this.emailTv.text = contact.email
        }

        // Retorna a view preechida
        return contactTilleView
    }

    private data class TilleContactHolder(val nameTv: TextView, val emailTv: TextView)

//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//
//        var tcb: TilleContactBinding? = null
//
//        // Pegar o contato no data source
//        val contact = contactList[position]
//
//        // Inflo uma nova célula se for necessário
//        var contactTilleView = convertView
//        if(contactTilleView == null){
//
//            // Contexto
////            contactTilleView = (context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.tille_contact, parent, false)
//
//            tcb = TilleContactBinding.inflate(context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater,
//                parent, false)
//            // ViewBinding
//            contactTilleView = tcb.root
//        }
//
//        // Inserir os valores na célula

//        tcb?.emailTv?.text = contact.email
//        tcb?.nameTv?.text = contact.name
//
//        // Retorna a view preechida
//        return contactTilleView
//    }
}