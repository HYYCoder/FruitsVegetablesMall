package com.huangyiyang.fruitsvegetablesmall.ui.login.contract

import com.huangyiyang.fruitsvegetablesmall.mvp.model.BaseModelInterface
import com.huangyiyang.fruitsvegetablesmall.mvp.presenter.BasePresenter
import com.huangyiyang.fruitsvegetablesmall.mvp.view.BaseViewInterface

interface LoginActivityContract {
    interface LoginActivityModel : BaseModelInterface{

    }

    interface LoginActivityView : BaseViewInterface{

    }

    abstract class LoginActivityPresenter : BasePresenter<LoginActivityModel, LoginActivityView>(){

    }

}