package de.florianweinaug.comicscollection.ui.issue

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.florianweinaug.comicscollection.databinding.FragmentIssueDetailBinding
import de.florianweinaug.comicscollection.model.Issue

class IssueDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val viewModel = IssueViewModel.create(this)

        val id = arguments?.getInt(ARG_ISSUE_ID)!!
        viewModel.setId(id)

        val binding = FragmentIssueDetailBinding.inflate(inflater, container, false)

        binding.item = viewModel
        binding.setLifecycleOwner(this)

        return binding.root
    }

    companion object {
        private const val ARG_ISSUE_ID = "issue_id"

        fun newInstance(issue: Issue): IssueDetailFragment {
            val fragment = IssueDetailFragment()
            val args = Bundle()
            args.putInt(ARG_ISSUE_ID, issue.id)
            fragment.arguments = args
            return fragment
        }
    }
}