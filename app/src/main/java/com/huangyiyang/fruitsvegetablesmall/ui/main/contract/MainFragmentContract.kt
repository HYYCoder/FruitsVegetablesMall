package com.huangyiyang.fruitsvegetablesmall.ui.main.contract

import com.huangyiyang.fruitsvegetablesmall.bean.RecommendGoodsBean
import com.huangyiyang.fruitsvegetablesmall.mvp.load.LoadListPresenter
import com.huangyiyang.fruitsvegetablesmall.mvp.model.BaseModelInterface
import com.huangyiyang.fruitsvegetablesmall.mvp.view.BaseViewInterface

interface MainFragmentContract {
    interface MainFragmentModel : BaseModelInterface {

    }

    interface MainFragmentView : BaseViewInterface {

    }

    abstract class MainFragmentPresenter :
        LoadListPresenter<RecommendGoodsBean, MainFragmentModel, MainFragmentView>() {
    }

}