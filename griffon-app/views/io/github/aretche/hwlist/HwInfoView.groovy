package io.github.aretche.hwlist

import griffon.core.artifact.GriffonView
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import javafx.beans.property.StringProperty
import javafx.scene.control.Tab
import org.kordamp.ikonli.fontawesome.FontAwesome
import org.kordamp.ikonli.javafx.FontIcon

import javax.annotation.Nonnull

@ArtifactProviderFor(GriffonView)
class HwInfoView {
    @MVCMember @Nonnull
    FactoryBuilderSupport builder
    @MVCMember @Nonnull
    HwInfoModel model
    @MVCMember @Nonnull
    AppView parentView

    void initUI() {

        builder.with {
            content = anchorPane {
                button(leftAnchor: 420, topAnchor: 5, prefWidth: 200,
                        text: application.messageSource.getMessage('hwInfo.refeshInfo.label'),
                        refreshInfoAction)
                textArea(leftAnchor: 20, topAnchor: 40, prefHeight: 360, prefWidth: 600,
                        text: bind(model.detailProperty()))
            }
        }

        Tab tab = new Tab(text: application.messageSource.getMessage('hwInfo.tab.label'))
        tab.graphic = new FontIcon(FontAwesome.GEARS)
        tab.content = builder.content
        tab.closable = false
        parentView.tabPane.tabs.add(tab)
    }
}