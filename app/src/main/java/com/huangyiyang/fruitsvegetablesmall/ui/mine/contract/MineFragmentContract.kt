package com.huangyiyang.fruitsvegetablesmall.ui.mine.contract

import com.huangyiyang.fruitsvegetablesmall.mvp.model.BaseModelInterface
import com.huangyiyang.fruitsvegetablesmall.mvp.presenter.BasePresenter
import com.huangyiyang.fruitsvegetablesmall.mvp.view.BaseViewInterface

interface MineFragmentContract {

    interface MineFragmentModel : BaseModelInterface{

    }

    interface MineFragmentView : BaseViewInterface{

    }

    abstract class MineFragmentPresenter : BasePresenter<MineFragmentModel,MineFragmentView>(){

    }
}