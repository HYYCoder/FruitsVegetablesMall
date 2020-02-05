package com.huangyiyang.fruitsvegetablesmall.ui.goods.contract

import com.huangyiyang.fruitsvegetablesmall.mvp.model.BaseModelInterface
import com.huangyiyang.fruitsvegetablesmall.mvp.presenter.BasePresenter
import com.huangyiyang.fruitsvegetablesmall.mvp.view.BaseViewInterface

interface GoodsDetailActivityContract {
    interface GoodsDetailActivityModel : BaseModelInterface{

    }

    interface GoodsDetailActivityView : BaseViewInterface{

    }

    abstract class GoodsDetailActivityPresenter : BasePresenter<GoodsDetailActivityModel, GoodsDetailActivityView>(){

    }
}