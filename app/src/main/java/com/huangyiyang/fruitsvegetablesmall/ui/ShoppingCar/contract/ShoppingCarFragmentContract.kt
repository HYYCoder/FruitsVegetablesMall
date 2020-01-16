package com.huangyiyang.fruitsvegetablesmall.ui.ShoppingCar.contract

import com.huangyiyang.fruitsvegetablesmall.mvp.model.BaseModelInterface
import com.huangyiyang.fruitsvegetablesmall.mvp.presenter.BasePresenter
import com.huangyiyang.fruitsvegetablesmall.mvp.view.BaseViewInterface

interface ShoppingCarFragmentContract {
    interface ShoppingCarFragmentModel : BaseModelInterface{

    }

    interface ShoppingCarFragmentView : BaseViewInterface{

    }

    abstract class ShoppingCarFragmentPresenter : BasePresenter<ShoppingCarFragmentModel,ShoppingCarFragmentView>(){

    }
}