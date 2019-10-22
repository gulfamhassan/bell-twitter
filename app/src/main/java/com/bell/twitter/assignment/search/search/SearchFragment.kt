package com.bell.twitter.assignment.search.search

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bell.twitter.assignment.R
import com.bell.twitter.assignment.search.search.adapter.SearchTweetListAdapter
import com.twitter.sdk.android.core.models.Tweet
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment(), SearchContract.View {

    private lateinit var interactor: SearchContract.Interactor
    private lateinit var router: SearchContract.Router
    private lateinit var presenter: SearchContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewManager = LinearLayoutManager(activity)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            recyclerView.setHasFixedSize(true)
        }

        interactor = SearchInteractor()
        router = SearchRouter(context)
        presenter = SearchPresenter(context, this, interactor, router)
        presenter.onInitializeRequested()

        searchButton.setOnClickListener {
            presenter.performSearchWithQuery(searchField.text.toString())
            searchField.setText("")
        }
    }

    override fun setTweetsList(tweetsList: MutableList<Tweet>?) {
        activity?.let {
            tweetsList?.let { tweetList ->
                recyclerView.adapter =
                    SearchTweetListAdapter(it, tweetList, presenter as OnTweetListItemClickListener)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onCleanUpRequested()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu_options, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_maps -> presenter.openMapsPage()
        }
        return false
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
            .apply {
                arguments = Bundle().apply {
                }
            }
    }


}