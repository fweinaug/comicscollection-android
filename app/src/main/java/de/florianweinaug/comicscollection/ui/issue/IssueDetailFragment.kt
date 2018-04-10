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
        val item = arguments?.getSerializable(ARG_ISSUE) as Issue
        val binding = FragmentIssueDetailBinding.inflate(inflater, container, false)

        binding.item = item

        return binding.root
    }

    companion object {
        private const val ARG_ISSUE = "issue"

        fun newInstance(issue: Issue): IssueDetailFragment {
            val fragment = IssueDetailFragment()
            val args = Bundle()
            args.putSerializable(ARG_ISSUE, issue)
            fragment.arguments = args
            return fragment
        }
    }
}