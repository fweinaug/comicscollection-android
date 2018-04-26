package de.florianweinaug.comicscollection.ui.issue

import android.app.AlertDialog
import android.app.Dialog
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import de.florianweinaug.comicscollection.R
import kotlinx.android.synthetic.main.dialog_issue_edit.view.*

class EditIssueDialog : DialogFragment() {
    private lateinit var mViewModel: IssuesViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = View.inflate(context, R.layout.dialog_issue_edit, null)

        val editTitle = view.edit_title
        val editSummary = view.edit_summary

        mViewModel = IssuesViewModel.create(activity as IssueDetailActivity)

        mViewModel.getIssue().observe(this, Observer {
            editTitle.setText(it?.title)
            editSummary.setText(it?.summary)
        })

        val builder = AlertDialog.Builder(activity)
        builder.setView(view)
                .setTitle(R.string.issue_edit_header)
                .setPositiveButton(R.string.issue_edit_update, { _, _ -> editIssue(editTitle, editSummary) })
                .setNegativeButton(R.string.dialog_cancel, null)

        val dialog = builder.create()
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

        return dialog
    }

    private fun editIssue(editTitle: EditText, editSummary: EditText) {
        mViewModel.edit(editTitle.text.toString(), editSummary.text.toString())
    }
}