package com.huangyiyang.fruitsvegetablesmall.ui.cassification.contract

import com.huangyiyang.fruitsvegetablesmall.mvp.model.BaseModelInterface
import com.huangyiyang.fruitsvegetablesmall.mvp.presenter.BasePresenter
import com.huangyiyang.fruitsvegetablesmall.mvp.view.BaseViewInterface

interface ClassificationFragmentContract {
    interface ClassificationFragmentModel : BaseModelInterface {

    }

    interface ClassificationFragmentView : BaseViewInterface {

    }

    abstract class ClassificationFragmentPresenter :
        BasePresenter<ClassificationFragmentModel, ClassificationFragmentView>() {

    }

}