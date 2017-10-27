package io.github.aretche.hwlist

import griffon.core.artifact.GriffonModel
import griffon.transform.FXObservable
import griffon.metadata.ArtifactProviderFor

@ArtifactProviderFor(GriffonModel)
class GriffonHwlistModel {
    @FXObservable String clickCount = "0"
}