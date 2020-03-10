package com.huangyiyang.fruitsvegetablesmall.manage

import android.content.Context
import com.huangyiyang.fruitsvegetablesmall.api.FrameConst
import com.huangyiyang.fruitsvegetablesmall.util.ACacheUtil
import com.huangyiyang.fruitsvegetablesmall.util.GsonUtil
import org.json.JSONArray
import org.json.JSONException
import java.util.*

class UserManager {
    /**
     * 用户信息Key
     */
    companion object {
        private val USER_ID = "userId"

        private val USER_TOKEN = "userToken"

        private val USER_NAME = "userName"
        private val NAME = "name"
        private val USER_NICK_NAME = "userNickName"

        private val USER_SEX = "userSex"

        private val USER_PHONE = "userPhone"

        private val USER_ICON = "userIcon"

        private val USER_ADDRESS = "userAddress"

        private val USER_LEVEL = "userLevel"

        private val USER_STATUS = "userStatus"

        private val USER_PASSWORD = "userPassword"

        private val KEY_WORDS = "keyWords"

        private val Pay_ID = "PayID"

        private val Pay_SUCCESS = "PaySuccess"

        private val USER_CODE = "userCode"
        private val ORDER_OVERDUETIME = "orderOverdueTime"
        private val ACTIVITY_ID = "activityId"
        private val Token_EXPIRED = "tokenIS"
        private val CLIENT_ID = "clientID"
        private val USER_CODE2 = "userCode2"
        private val RECIEVE_PHONE = "receivingPhone"

        @Volatile
        private var sInstance: UserManager? = null

        fun getInstance(): UserManager? {
            if (sInstance == null) {
                synchronized(UserManager::class.java) {
                    if (sInstance == null) {
                        sInstance = UserManager()
                    }
                }
            }
            return sInstance
        }

        fun checkUser(context: Context?): Boolean {
            return !GsonUtil.format0(ACacheUtil.get()?.getAsString(FrameConst.USER_ID)).equals("0")
        }
    }

    constructor()


    fun getUserId(): String? {
        return ACacheUtil.get()?.getAsString(USER_ID)
    }

    fun saveUserId(userId: String?) {
        ACacheUtil.get()?.put(USER_ID, userId)
    }

    fun getOrderOverdueTime(): Int {
        var d = 0
        d = try {
            ACacheUtil.get()?.getAsString(ORDER_OVERDUETIME)?.toInt()!!
        } catch (e: Exception) {
            0
        }
        return d
    }

    fun saveOrderOverdueTime(userId: String?) {
        ACacheUtil.get()?.put(ORDER_OVERDUETIME, userId)
    }

    fun getActivityId(): String? {
        return ACacheUtil.get()?.getAsString(ACTIVITY_ID)
    }

    fun saveActivityId(userId: String?) {
        ACacheUtil.get()?.put(ACTIVITY_ID, userId)
    }

    fun getToken_EXPIRED(): String? {
        return ACacheUtil.get()?.getAsString(Token_EXPIRED)
    }

    fun saveToken_EXPIRED(userId: String?) {
        ACacheUtil.get()?.put(Token_EXPIRED, userId)
    }

    fun getPayId(): String? {
        return ACacheUtil.get()?.getAsString(Pay_ID)
    }

    fun savePaySuccess(paySuccess: String?) {
        ACacheUtil.get()?.put(Pay_SUCCESS, paySuccess)
    }

    fun getPay_SUCCESS(): String? {
        return ACacheUtil.get()?.getAsString(Pay_SUCCESS)
    }

    fun savePayId(payId: String?) {
        ACacheUtil.get()?.put(Pay_ID, payId)
    }

    fun getUserToken(): String? {
        return ACacheUtil.get()?.getAsString(USER_TOKEN)
    }

    fun saveUserToken(userToken: String?) {
        ACacheUtil.get()?.put(USER_TOKEN, userToken)
    }

    fun getUserName(): String? {
        return ACacheUtil.get()?.getAsString(USER_NAME)
    }

    fun saveUserName(userName: String?) {
        ACacheUtil.get()?.put(USER_NAME, userName)
    }

    fun getName(): String? {
        return ACacheUtil.get()?.getAsString(NAME)
    }

    fun saveName(userName: String?) {
        ACacheUtil.get()?.put(NAME, userName)
    }

    fun getUserNickName(): String? {
        return ACacheUtil.get()?.getAsString(USER_NICK_NAME)
    }

    fun saveUserNickName(userNickName: String?) {
        ACacheUtil.get()?.put(USER_NICK_NAME, userNickName)
    }

    fun getUserSex(): String? {
        return ACacheUtil.get()?.getAsString(USER_SEX)
    }

    fun saveUserSex(userSex: String?) {
        ACacheUtil.get()?.put(USER_SEX, userSex)
    }

    fun getUserPhone(): String? {
        return ACacheUtil.get()?.getAsString(USER_PHONE)
    }

    fun saveReceivingPhone(userPhone: String?) {
        ACacheUtil.get()?.put(RECIEVE_PHONE, userPhone)
    }

    fun getReceivingPhone(): String? {
        return ACacheUtil.get()?.getAsString(RECIEVE_PHONE)
    }

    fun saveUserPhone(userPhone: String?) {
        ACacheUtil.get()?.put(USER_PHONE, userPhone)
    }

    fun getUserIcon(): String? {
        return ACacheUtil.get()?.getAsString(USER_ICON)
    }

    fun saveUserIcon(userIcon: String?) {
        ACacheUtil.get()?.put(USER_ICON, userIcon)
    }

    fun getUserAddress(): String? {
        return ACacheUtil.get()?.getAsString(USER_ADDRESS)
    }

    fun saveUserAddress(userAddress: String?) {
        ACacheUtil.get()?.put(USER_ADDRESS, userAddress)
    }

    fun getUserLevel(): String? {
        return ACacheUtil.get()?.getAsString(USER_LEVEL)
    }

    fun saveUserCode(userCode: String?) {
        ACacheUtil.get()?.put(USER_CODE, userCode)
    }

    fun getUserCode(): String? {
        return ACacheUtil.get()?.getAsString(USER_CODE)
    }

    fun saveUserCode2(userCode: String?) {
        ACacheUtil.get()?.put(USER_CODE2, userCode)
    }

    fun getUserCode2(): String? {
        return ACacheUtil.get()?.getAsString(USER_CODE2)
    }

    fun saveClientId(userCode: String?) {
        ACacheUtil.get()?.put(CLIENT_ID, userCode)
    }

    fun getClientId(): String? {
        return ACacheUtil.get()?.getAsString(CLIENT_ID)
    }

    fun saveUserLevel(userLevel: String?) {
        ACacheUtil.get()?.put(USER_LEVEL, userLevel)
    }

    fun getUserStatus(): String? {
        return ACacheUtil.get()?.getAsString(USER_STATUS)
    }

    fun saveUserSatus(userStatus: String?) {
        ACacheUtil.get()?.put(USER_STATUS, userStatus)
    }

    fun getUserPassword(): String? {
        return ACacheUtil.get()?.getAsString(USER_PASSWORD)
    }

    fun saveUserPassword(userPassword: String?) {
        ACacheUtil.get()?.put(USER_PASSWORD, userPassword)
    }

    fun clearCache() {
        ACacheUtil.get()?.clear()
    }

    fun saveKeyValue(key: String, value: String?) {
        ACacheUtil.get()?.put(key, value)
    }

    fun getValue(key: String): String? {
        return ACacheUtil.get()?.getAsString(key)
    }

    fun logout(context: Context?) {
        ACacheUtil.get()?.clear()
    }

    fun saveKeyWord(key: String) {
        if (null != ACacheUtil.get()?.getAsJSONArray(KEY_WORDS)) { //            ACache.get().put(KEY_WORDS, ACache.get().getAsJSONArray(KEY_WORDS).put(key));
            // 删除重复KeyWord
            val list = ArrayList<String?>()
            val jsonArray: JSONArray = ACacheUtil.get()?.getAsJSONArray(KEY_WORDS)!!
            val len = jsonArray.length()
            if (jsonArray != null) {
                for (i in 0 until len) {
                    try {
                        val value = jsonArray[i].toString() // 现有KeyWord
                        if (key != value) { // 现有KeyWord和新KeyWord不同，则加入list
                            list.add(value)
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }
            val newArray = JSONArray(list) // 没有新KeyWord的JSON
            ACacheUtil.get()?.put(KEY_WORDS, newArray)
            ACacheUtil.get()
                ?.put(KEY_WORDS, ACacheUtil.get()?.getAsJSONArray(KEY_WORDS)?.put(key)!!) // 最后插入新KeyWord
        } else {
            val jsonArray = JSONArray()
            jsonArray.put(key)
            ACacheUtil.get()?.put(KEY_WORDS, jsonArray)
        }
    }

    fun getKeyWord(): JSONArray? { //        return ACache.get().getAsJSONArray(KEY_WORDS);
    // 返回倒序KeyWords
        val list = ArrayList<String?>()
        val jsonArray: JSONArray? = ACacheUtil.get()?.getAsJSONArray(KEY_WORDS)
        if (jsonArray != null) {
            val len = jsonArray.length()
            for (i in len - 1 downTo 0) {
                try {
                    list.add(jsonArray[i].toString())
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }
        return JSONArray(list)
    }

    fun clearKeyWords() {
        if (null != ACacheUtil.get()?.getAsJSONArray(KEY_WORDS)) {
            val jsonArray = JSONArray()
            ACacheUtil.get()?.put(KEY_WORDS, jsonArray)
        }
    }

}