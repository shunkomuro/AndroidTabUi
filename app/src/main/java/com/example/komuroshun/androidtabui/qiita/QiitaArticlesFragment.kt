package com.example.komuroshun.androidtabui.qiita

import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.komuroshun.androidtabui.BaseFragment
import com.example.komuroshun.androidtabui.R
import kotlinx.android.synthetic.main.fragment_qita_article_list.*

/**
 * Qiita の記事一覧
 */
class QiitaArticlesFragment : BaseFragment() {
    // TODO 上から下に画面を引っ張って画面を更新できるようにする
    // TODO 20 項目分スクロールで追加読み込みする
    // TODO 画面読み込み時くるくるだす

    lateinit var mQiitaArticlesViewModel: QiitaArticlesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_qita_article_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mQiitaArticlesViewModel = QiitaArticlesViewModel.create(activity!!, this)
        mQiitaArticlesViewModel.getQitaArticleListAdapter()
    }

    // TODO もっといいやり方がないか
    fun setAdapter(qiitaArticlesAdapter: QiitaArticlesAdapter) {
        recyclerView.setLayoutManager(LinearLayoutManager(activity))
        recyclerView.adapter = qiitaArticlesAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
    }

    fun createFragmentOnCurrentPage(uri: Uri) {
        mListener!!.onFragmentInteraction(uri)
    }

    companion object {

        fun newInstance(): QiitaArticlesFragment {
            return QiitaArticlesFragment()
        }
    }
}
