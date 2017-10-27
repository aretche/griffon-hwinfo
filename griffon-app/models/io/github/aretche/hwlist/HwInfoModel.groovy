package io.github.aretche.hwlist

import griffon.core.artifact.GriffonModel
import griffon.metadata.ArtifactProviderFor
import griffon.transform.FXObservable

@ArtifactProviderFor(GriffonModel)
class HwInfoModel {

    @FXObservable
    String osInfo = ""
    @FXObservable
    String macAdress = ""
    @FXObservable
    String detail = ""
}