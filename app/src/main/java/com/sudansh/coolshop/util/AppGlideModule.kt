package com.sudansh.coolshop.util

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class GlideModule : AppGlideModule() {

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}
