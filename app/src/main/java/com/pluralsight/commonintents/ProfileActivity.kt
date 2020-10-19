package com.pluralsight.commonintents

import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.ContactsContract
import android.provider.MediaStore
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.actions.ReserveIntents
import kotlinx.android.synthetic.main.activity_profile.*
import java.util.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

//        val buttonCalendar = findViewById<Button>(R.id.buttonCalendar)
        buttonCalendar.setOnClickListener {
            onClickCalendar()
        }

        val buttonMaps = findViewById<Button>(R.id.buttonMaps)
        buttonMaps.setOnClickListener {
            onClickMaps()
        }

        val buttonTaxi = findViewById<Button>(R.id.buttonTaxi)
        buttonTaxi.setOnClickListener {
            onClickTaxi()
        }

        val buttonSearch = findViewById<Button>(R.id.buttonSearch)
        buttonSearch.setOnClickListener {
            onClickSearch()
        }

        val buttonListen = findViewById<Button>(R.id.buttonListen)
        buttonListen.setOnClickListener {
            onClickListen()
        }

        val buttonWebsite = findViewById<Button>(R.id.buttonWebsite)
        buttonWebsite.setOnClickListener {
            onClickWeb()
        }

        val buttonCall = findViewById<Button>(R.id.buttonCall)
        buttonCall.setOnClickListener {
            onClickCall()
        }

        val buttonEmail = findViewById<Button>(R.id.buttonEmail)
        buttonEmail.setOnClickListener {
            onClickEmail()
        }

        val buttonContact = findViewById<Button>(R.id.buttonContact)
        buttonContact.setOnClickListener {
            onClickContact()
        }
    }

    private fun onClickContact() {
        val intent = Intent(Intent.ACTION_INSERT).apply {
            type = ContactsContract.Contacts.CONTENT_TYPE
            putExtra(ContactsContract.Intents.Insert.NAME, "Anne Droid")
            putExtra(ContactsContract.Intents.Insert.EMAIL, "anne@example.com")
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun onClickEmail() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("smsto:123456789")
            putExtra("sms_body", "Hello from Common Friends!")
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun onClickCall() {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:123456789")
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun onClickWeb() {
        val webpage: Uri = Uri.parse("http://example.com")
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun onClickListen() {
        val artist = "Beethoven"
        val song = "Moonlight Sonata"

        val intent = Intent(MediaStore.INTENT_ACTION_MEDIA_PLAY_FROM_SEARCH).apply {

            putExtra(MediaStore.EXTRA_MEDIA_FOCUS, "vnd.android.cursor.item/audio")
            putExtra(MediaStore.EXTRA_MEDIA_ARTIST, artist)
            putExtra(MediaStore.EXTRA_MEDIA_TITLE, song)
            putExtra(SearchManager.QUERY, artist + " " + song)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun onClickSearch() {
        val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
            putExtra(SearchManager.QUERY, "gardening")
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun onClickTaxi() {
        val intent = Intent(ReserveIntents.ACTION_RESERVE_TAXI_RESERVATION)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun onClickMaps() {
        val address = "182 N. Union Ave, Farmington, UT 84025"
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("geo:0,0?q=$address")
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun onClickCalendar() {

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, Calendar.MARCH)
        calendar.set(Calendar.DAY_OF_MONTH, 21)
        val dateLong = calendar.timeInMillis

        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, "Anne Droid Birthday")
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, dateLong)
            putExtra(CalendarContract.EXTRA_EVENT_END_TIME, dateLong)
            putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true)
            putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY")
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}
