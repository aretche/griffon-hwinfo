package io.github.aretche.hwlist

import griffon.core.artifact.GriffonView
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import javafx.beans.property.StringProperty
import javafx.scene.control.Tab
import org.codehaus.griffon.runtime.javafx.artifact.AbstractJavaFXGriffonView
import org.kordamp.ikonli.fontawesome.FontAwesome
import org.kordamp.ikonli.javafx.FontIcon

import javax.annotation.Nonnull

@ArtifactProviderFor(GriffonView)
class HwInfoView extends AbstractJavaFXGriffonView {
    @MVCMember @Nonnull
    FactoryBuilderSupport builder
    @MVCMember @Nonnull
    HwInfoModel model
    @MVCMember @Nonnull
    HwInfoController controller
    @MVCMember @Nonnull
    AppView parentView

    void initUI() {
        // Creo un tab usando GroovyFX
        Tab tab1 = new Tab(text: application.messageSource.getMessage('hwInfo.tabGroovyFX.label'))
        builder.with {
            content = anchorPane {
                button(leftAnchor: 420, topAnchor: 5, prefWidth: 200,
                        text: application.messageSource.getMessage('hwInfo.refeshButton.label'),
                        refreshInfoAction)
                textArea(leftAnchor: 20, topAnchor: 40, prefHeight: 360, prefWidth: 600,
                        text: bind(model.detailProperty()))
            }
        }
        tab1.graphic = new FontIcon(FontAwesome.GEARS)
        tab1.content = builder.content
        tab1.closable = false
        parentView.tabPane.tabs.add(tab1)

        //Creo otro tab en este caso usando un archivo FXML
        Tab tab2 = new Tab(text: application.messageSource.getMessage('hwInfo.tabFXML.label'))
        builder.with {
            content = builder.fxml(resource('/io/github/aretche/hwlist/HwInfoTab.fxml')) {
                refreshButton.text = application.messageSource.getMessage('hwInfo.refeshButton.label')
                bean(output, text: bind(model.detailProperty()))
            }
        }
        connectActions(builder.content, controller)
        tab2.graphic = new FontIcon(FontAwesome.ADN)
        tab2.content = builder.content
        tab2.closable = false
        parentView.tabPane.tabs.add(tab2)
    }
}