package com.huangyiyang.fruitsvegetablesmall.ui.order.contract

import com.huangyiyang.fruitsvegetablesmall.mvp.model.BaseModelInterface
import com.huangyiyang.fruitsvegetablesmall.mvp.presenter.BasePresenter
import com.huangyiyang.fruitsvegetablesmall.mvp.view.BaseViewInterface

interface SuccessActivityContract {

    interface SuccessActivityModel : BaseModelInterface{

    }

    interface SuccessActivityView : BaseViewInterface{

    }

    abstract class SuccessActivityPresenter : BasePresenter<SuccessActivityModel, SuccessActivityView>(){

    }
}