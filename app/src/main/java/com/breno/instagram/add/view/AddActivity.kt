package com.breno.instagram.add.view

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.breno.instagram.R
import com.breno.instagram.add.Add
import com.breno.instagram.add.presentation.AddPresenter
import com.breno.instagram.common.DependencyInjector
import com.breno.instagram.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity(), Add.View {

    private lateinit var binding: ActivityAddBinding
    private val uri by lazy {
        intent?.getParcelableExtra<Uri>("photoUri") ?: throw RuntimeException("photo not found")
    }

    override lateinit var presenter: Add.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.aAddIvCaption.setImageURI(uri)

        presenter = AddPresenter(this, DependencyInjector.addRepository())

        setSupportActionBar(binding.aAddTToolbar)

        val drawable = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)
        supportActionBar?.setHomeAsUpIndicator(drawable)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_share, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }

            R.id.action_share -> {
                presenter.createPost(uri, binding.aAddEtCaption.text.toString())
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun showProgress(enabled: Boolean) {
        binding.aAddPbProgress.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    override fun displayRequestSuccess() {
        setResult(RESULT_OK)
        finish()
    }

    override fun displayRequestFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
