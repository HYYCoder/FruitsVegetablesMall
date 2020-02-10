package com.huangyiyang.fruitsvegetablesmall.ui.order.contract

import com.huangyiyang.fruitsvegetablesmall.mvp.model.BaseModelInterface
import com.huangyiyang.fruitsvegetablesmall.mvp.presenter.BasePresenter
import com.huangyiyang.fruitsvegetablesmall.mvp.view.BaseViewInterface

interface ConfirmOrderActivityContract {

    interface ConfirmOrderActivityModel : BaseModelInterface{

    }

    interface ConfirmOrderActivityView : BaseViewInterface{

    }

    abstract class ConfirmOrderActivityPresenter : BasePresenter<ConfirmOrderActivityModel, ConfirmOrderActivityView>(){

    }
}