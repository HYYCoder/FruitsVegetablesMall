package com.huangyiyang.fruitsvegetablesmall.ui.main.contract

import com.huangyiyang.fruitsvegetablesmall.bean.ProductDetailListBean
import com.huangyiyang.fruitsvegetablesmall.mvp.load.LoadListPresenter
import com.huangyiyang.fruitsvegetablesmall.mvp.model.BaseModelInterface
import com.huangyiyang.fruitsvegetablesmall.mvp.view.BaseViewInterface

interface MainActivityContract {
    interface MainActivityModel : BaseModelInterface{

    }

    interface MainActivityView : BaseViewInterface{

    }

    abstract class MainActivityPresenter : LoadListPresenter<ProductDetailListBean, MainActivityModel, MainActivityView>(){

    }
}