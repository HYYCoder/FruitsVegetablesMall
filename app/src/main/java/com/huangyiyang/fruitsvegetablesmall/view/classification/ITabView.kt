package com.huangyiyang.fruitsvegetablesmall.view.classification

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.View

import q.rorbin.badgeview.Badge

interface ITabView {
    fun setBadge(badge: TabBadge?): ITabView?

    fun setIcon(icon: TabIcon?): ITabView?

    fun setTitle(title: TabTitle?): ITabView?

    fun setBackground(resid: Int): ITabView?

    fun getBadge(): TabBadge?

    fun getIcon(): TabIcon?

    fun getTitle(): TabTitle?

    fun getTabView(): View?

    class TabIcon{
        private var mBuilder : Builder

        constructor(mBuilder : Builder) {
            this.mBuilder = mBuilder
        }

        val selectedIcon: Int
            get() = mBuilder.mSelectedIcon

        val normalIcon: Int
            get() = mBuilder.mNormalIcon

        val iconGravity: Int
            get() = mBuilder.mIconGravity

        val iconWidth: Int
            get() = mBuilder.mIconWidth

        val iconHeight: Int
            get() = mBuilder.mIconHeight

        val margin: Int
            get() = mBuilder.mMargin

        class Builder {
            var mSelectedIcon = 0
            var mNormalIcon = 0
            var mIconGravity: Int
            var mIconWidth: Int
            var mIconHeight: Int
            var mMargin: Int
            fun setIcon(selectIconResId: Int, normalIconResId: Int): Builder {
                mSelectedIcon = selectIconResId
                mNormalIcon = normalIconResId
                return this
            }

            fun setIconSize(width: Int, height: Int): Builder {
                mIconWidth = width
                mIconHeight = height
                return this
            }

            fun setIconGravity(gravity: Int): Builder {
                check(
                    !(gravity != Gravity.START && (gravity != Gravity.END
                            ) and (gravity != Gravity.TOP) and (gravity != Gravity.BOTTOM))
                ) {
                    "iconGravity only support Gravity.START " +
                            "or Gravity.END or Gravity.TOP or Gravity.BOTTOM"
                }
                mIconGravity = gravity
                return this
            }

            fun setIconMargin(margin: Int): Builder {
                mMargin = margin
                return this
            }

            fun build(): TabIcon {
                return TabIcon(
                    this
                )
            }

            init {
                mIconWidth = -1
                mIconHeight = -1
                mIconGravity = Gravity.START
                mMargin = 0
            }
        }

    }

    class TabTitle {

        private var mBuilder : Builder

        constructor(mBuilder : Builder) {
            this.mBuilder = mBuilder
        }

        val colorSelected: Int
            get() = mBuilder.mColorSelected

        val colorNormal: Int
            get() = mBuilder.mColorNormal

        val titleTextSize: Int
            get() = mBuilder.mTitleTextSize

        val content: String
            get() = mBuilder.mContent

        class Builder {
            var mColorSelected: Int
            var mColorNormal: Int
            var mTitleTextSize: Int
            var mContent: String
            fun setTextColor(colorSelected: Int, colorNormal: Int): Builder {
                mColorSelected = colorSelected
                mColorNormal = colorNormal
                return this
            }

            fun setTextSize(sizeSp: Int): Builder {
                mTitleTextSize = sizeSp
                return this
            }

            fun setContent(content: String): Builder {
                mContent = content
                return this
            }

            fun build(): TabTitle {
                return TabTitle(
                    this
                )
            }

            init {
                mColorSelected = -0xcccccd
                mColorNormal = -0x99999a
                mTitleTextSize = 14
                mContent = ""
            }
        }

    }

    class TabBadge{

        private var mBuilder : Builder

        constructor(mBuilder : Builder) {
            this.mBuilder = mBuilder
        }

        val backgroundColor: Int
            get() = mBuilder.colorBackground

        val badgeTextColor: Int
            get() = mBuilder.colorBadgeText

        val badgeTextSize: Float
            get() = mBuilder.badgeTextSize

        val badgePadding: Float
            get() = mBuilder.badgePadding

        val badgeNumber: Int
            get() = mBuilder.badgeNumber

        val badgeText: String?
            get() = mBuilder.badgeText

        val badgeGravity: Int
            get() = mBuilder.badgeGravity

        val gravityOffsetX: Int
            get() = mBuilder.gravityOffsetX

        val gravityOffsetY: Int
            get() = mBuilder.gravityOffsetY

        val isExactMode: Boolean
            get() = mBuilder.exactMode

        val isShowShadow: Boolean
            get() = mBuilder.showShadow

        val drawableBackground: Drawable?
            get() = mBuilder.drawableBackground

        val strokeColor: Int
            get() = mBuilder.colorStroke

        val isDrawableBackgroundClip: Boolean
            get() = mBuilder.drawableBackgroundClip

        val strokeWidth: Float
            get() = mBuilder.strokeWidth

        val onDragStateChangedListener: Badge.OnDragStateChangedListener?
            get() = mBuilder.dragStateChangedListener

        class Builder {
            var colorBackground: Int
            var colorBadgeText: Int
            var colorStroke: Int
            var drawableBackground: Drawable?
            var drawableBackgroundClip: Boolean
            var strokeWidth: Float
            var badgeTextSize: Float
            var badgePadding: Float
            var badgeNumber: Int
            var badgeText: String?
            var badgeGravity: Int
            var gravityOffsetX: Int
            var gravityOffsetY: Int
            var exactMode: Boolean
            var showShadow: Boolean
            var dragStateChangedListener: Badge.OnDragStateChangedListener? = null
            fun build(): TabBadge {
                return TabBadge(
                    this
                )
            }

            fun stroke(color: Int, strokeWidth: Int): Builder {
                colorStroke = color
                this.strokeWidth = strokeWidth.toFloat()
                return this
            }

            fun setDrawableBackground(
                drawableBackground: Drawable?,
                clip: Boolean
            ): Builder {
                this.drawableBackground = drawableBackground
                drawableBackgroundClip = clip
                return this
            }

            fun setShowShadow(showShadow: Boolean): Builder {
                this.showShadow = showShadow
                return this
            }

            fun setOnDragStateChangedListener(dragStateChangedListener: Badge.OnDragStateChangedListener?): Builder {
                this.dragStateChangedListener = dragStateChangedListener
                return this
            }

            fun setExactMode(exactMode: Boolean): Builder {
                this.exactMode = exactMode
                return this
            }

            fun setBackgroundColor(colorBackground: Int): Builder {
                this.colorBackground = colorBackground
                return this
            }

            fun setBadgePadding(dpValue: Float): Builder {
                badgePadding = dpValue
                return this
            }

            fun setBadgeNumber(badgeNumber: Int): Builder {
                this.badgeNumber = badgeNumber
                badgeText = null
                return this
            }

            fun setBadgeGravity(badgeGravity: Int): Builder {
                this.badgeGravity = badgeGravity
                return this
            }

            fun setBadgeTextColor(colorBadgeText: Int): Builder {
                this.colorBadgeText = colorBadgeText
                return this
            }

            fun setBadgeTextSize(badgeTextSize: Float): Builder {
                this.badgeTextSize = badgeTextSize
                return this
            }

            fun setBadgeText(badgeText: String?): Builder {
                this.badgeText = badgeText
                badgeNumber = 0
                return this
            }

            fun setGravityOffset(offsetX: Int, offsetY: Int): Builder {
                gravityOffsetX = offsetX
                gravityOffsetY = offsetY
                return this
            }

            init {
                colorBackground = -0x17b1c0
                colorBadgeText = -0x1
                colorStroke = Color.TRANSPARENT
                drawableBackground = null
                drawableBackgroundClip = false
                strokeWidth = 0f
                badgeTextSize = 11f
                badgePadding = 5f
                badgeNumber = 0
                badgeText = null
                badgeGravity = Gravity.END or Gravity.TOP
                gravityOffsetX = 1
                gravityOffsetY = 1
                exactMode = false
                showShadow = true
            }
        }

    }

}