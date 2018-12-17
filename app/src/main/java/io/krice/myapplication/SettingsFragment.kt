package io.krice.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.support.v7.preference.PreferenceFragmentCompat

class SettingsFragment: PreferenceFragmentCompat() {

    override fun onCreatePreferences(p0: Bundle?, p1: String?) {
        addPreferencesFromResource(R.xml.settings)

        findPreference("settings_legal").setOnPreferenceClickListener {
            val url = "https://www.google.fr"

            val builder = CustomTabsIntent.Builder()
            context?.let {
                builder.setToolbarColor(ContextCompat.getColor(it, R.color.colorPrimary))
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(it, Uri.parse(url))

            }
            true

        }

        findPreference("snettings_contact").setOnPreferenceClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.type = "plain/text"
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, arrayOf("redaction@mgig.fr"))
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Thanks you")
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Your app is so awesome")
            context?.startActivity(Intent.createChooser(emailIntent, "Choose mailer app"))
            true
        }

        findPreference("settings_website").setOnPreferenceClickListener {
            startActivity(Intent(context, WebsiteActivity::class.java))
            true
        }
    }

    companion object {
        fun newInstance() : SettingsFragment {
            val settingsIntance = SettingsFragment()
            return settingsIntance
        }
    }
}