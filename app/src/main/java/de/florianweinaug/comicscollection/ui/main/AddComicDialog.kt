package de.florianweinaug.comicscollection.ui.main

import android.app.AlertDialog
import android.app.Dialog
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.widget.*
import de.florianweinaug.comicscollection.R
import de.florianweinaug.comicscollection.model.Publisher
import kotlinx.android.synthetic.main.dialog_comic_add.view.*

class AddComicDialog : DialogFragment() {

    private lateinit var mViewModel: MainViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = View.inflate(context, R.layout.dialog_comic_add, null)

        val editName = view.edit_name
        val spinnerPublisher = view.spinner_publisher
        val checkConcluded = view.check_concluded

        mViewModel = MainViewModel.create(activity as MainActivity)

        mViewModel.getPublishers().observe(this, Observer {
            val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, it)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spinnerPublisher.adapter = adapter
        })

        val builder = AlertDialog.Builder(activity)
        builder.setView(view)
                .setTitle(R.string.comic_add_header)
                .setPositiveButton(R.string.comic_add_create, { _, _ -> addComic(editName, spinnerPublisher, checkConcluded) })
                .setNegativeButton(R.string.dialog_cancel, null)

        val dialog = builder.create()
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

        dialog.setOnShowListener { _ ->
            val button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            button.isEnabled = false
        }

        editName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                val enabled = s.isNotEmpty()
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = enabled
            }
        })

        editName.requestFocus()

        return dialog
    }

    private fun addComic(editName: EditText, spinnerPublisher: Spinner, checkConcluded: CheckBox) {
        mViewModel.addComic(editName.text.toString(), spinnerPublisher.selectedItem as Publisher, checkConcluded.isChecked)
    }
}