package com.example.glampingofart

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class DesktopActivity : AppCompatActivity() {

    private lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_desktop)

        drawer = findViewById(R.id.menu_desplegable)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val navView: NavigationView = findViewById(R.id.nav_view)

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}