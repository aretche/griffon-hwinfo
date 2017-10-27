package io.github.aretche.hwlist

import griffon.core.artifact.GriffonController
import griffon.core.controller.ControllerAction
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import griffon.transform.Threading
import javax.annotation.Nonnull

@ArtifactProviderFor(GriffonController)
class HwInfoController {
    @MVCMember @Nonnull
    HwInfoModel model

    @ControllerAction
    @Threading(Threading.Policy.INSIDE_UITHREAD_ASYNC)
    void refreshInfo() {
        model.detail = new Date().format("yyyyMMdd-HH:mm:ss.SSS", TimeZone.getTimeZone('UTC'))
        println model.detail
    }
}