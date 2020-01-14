package com.huangyiyang.fruitsvegetablesmall.ui.main.contract

import com.huangyiyang.fruitsvegetablesmall.mvp.model.BaseModelInterface
import com.huangyiyang.fruitsvegetablesmall.mvp.presenter.BasePresenter
import com.huangyiyang.fruitsvegetablesmall.mvp.view.BaseViewInterface
import rx.Observable

interface MainFragmentContract {
    interface MainFragmentModel : BaseModelInterface {

    }

    interface MainFragmentView : BaseViewInterface {

    }

    abstract class MainFragmentPresenter : BasePresenter<MainFragmentModel, MainFragmentView>() {
    }

}