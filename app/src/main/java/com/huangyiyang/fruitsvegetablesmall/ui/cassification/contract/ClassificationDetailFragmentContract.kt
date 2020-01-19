package com.huangyiyang.fruitsvegetablesmall.ui.cassification.contract

import com.huangyiyang.fruitsvegetablesmall.bean.RecommendGoodsBean
import com.huangyiyang.fruitsvegetablesmall.mvp.load.LoadListPresenter
import com.huangyiyang.fruitsvegetablesmall.mvp.model.BaseModelInterface
import com.huangyiyang.fruitsvegetablesmall.mvp.view.BaseViewInterface

interface ClassificationDetailFragmentContract {
    interface ClassificationDetailFragmentModel : BaseModelInterface{

    }

    interface ClassificationDetailFragmentView : BaseViewInterface{

    }

    abstract class MainClassificationFragmentPresenter : LoadListPresenter<RecommendGoodsBean, ClassificationDetailFragmentModel, ClassificationDetailFragmentView>() {

    }

}