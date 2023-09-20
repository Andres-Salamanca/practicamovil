package com.example.practicamovil

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.practicamovil.databinding.ActivityContactosBinding
import com.example.practicamovil.databinding.ActivityMainBinding

class Contactos : AppCompatActivity() {
    private lateinit var binding: ActivityContactosBinding
    private val CONTACTS_PERMISSION = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contactos)

        binding = ActivityContactosBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "El permiso de contactos no fue concedido", Toast.LENGTH_LONG).show()


        } else {
            retrieveContacts()

        }
    }
    private fun retrieveContacts() {
        val contactsList = mutableListOf<String>()
        val projection = arrayOf(ContactsContract.Contacts.DISPLAY_NAME)

        val cursor: Cursor? = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            projection,
            null,
            null,
            null
        )

        cursor?.use {
            while (it.moveToNext()) {
                val contactName =
                    it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                Log.i("contacto",contactName)
                contactsList.add(contactName)
            }
        }

        val adapter = ContactsAdapter(this, contactsList)
        val listView = findViewById<ListView>(R.id.listview)
        listView.adapter = adapter
    }
}