package com.huangyiyang.fruitsvegetablesmall.mvp.util

import android.content.Context
import com.yanzhenjie.permission.Action
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Rationale

class PermissionUtil {

    companion object {
        fun ApplyWithOutCallBack(
            context: Context?,
            vararg permissions: String?
        ) {
            ApplyPermission(context, null, null, null, *permissions)
        }

        fun ApplyWithRationale(
            context: Context?,
            rationale: Rationale?,
            vararg permissions: String?
        ) {
            ApplyPermission(context, rationale, null, null, *permissions)
        }

        fun ApplyPermission(
            context: Context?,
            rationale: Rationale?,
            yesAc: Action?,
            noAc: Action?,
            vararg permissions: String?
        ) {
            AndPermission.with(context!!)
                .permission(permissions)
                .rationale(rationale)
                .onGranted(yesAc).onDenied(noAc).start()
        }
    }
//    AndPermission.with(this)
//    .
//    fun permission(): Unit
//    .open
//
//    fun rationale(): Unit
//    .open
//
//    fun onGranted() {
//        var onAction: Unit
//        List<String> permissions
//        run {
//            // TODO 同意
//            AmapLocationManager.getInstance(this@UserMainActivity)
//                .getLocation(object : AMapLocationListener() {
//                    fun onLocationChanged(aMapLocation: AMapLocation) {
//                        UserManager.getInstance().saveLongitude(
//                            this@UserMainActivity,
//                            GsonUtil.format0(aMapLocation.getLongitude())
//                        )
//                        UserManager.getInstance().saveLatitude(
//                            this@UserMainActivity,
//                            GsonUtil.format0(aMapLocation.getLatitude())
//                        )
//                    }
//                })
//        }
//    }).
//    fun onDenied() {
//        var onAction: Unit
//        List<String> permissions
//        run {
//            // TODO 拒绝
//            if (AndPermission.hasAlwaysDeniedPermission(this@UserMainActivity, permissions)) {
//                这些权限被用户总是拒绝
//                这里使用一个Dialog展示没有这些权限应用程序无法继续运行
//                询问用户是否去设置中授权
//                val settingService: SettingService = AndPermission.permissionSetting(mContext)
//                如果用户同意去设置
//                settingService.execute()
//                如果用户不同意去设置
//                settingService.cancel()
//            }
//        }
//    }).
//    fun start()
//
//    private val mRationale =
//        Rationale { context, permissions, executor ->
//            // 这里使用一个Dialog询问用户是否继续授权。
//            // 如果用户继续：
//            executor.execute()
//            // 如果用户中断：
//            executor.cancel()
//        }

}