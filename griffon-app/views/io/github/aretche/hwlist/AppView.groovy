package io.github.aretche.hwlist

import griffon.core.artifact.GriffonView
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import javafx.scene.Node
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.control.TabPane
import javafx.scene.paint.Color
import javafx.stage.Stage

import javax.annotation.Nonnull

/**
 * Created by arellanog on 27/10/17.
 */
@ArtifactProviderFor(GriffonView)
class AppView {
    @MVCMember @Nonnull
    FactoryBuilderSupport builder

    private TabPane tabPane

    @Override
    public void mvcGroupInit(@Nonnull Map<String, Object> args) {
        // Construyo los paneles
        createMVCGroup("hwInfo")
    }

    @Override
    void initUI() {
        // Stage sería la ventana
        Stage stage = (Stage) application.createApplicationContainer([:])
        // Definimos el título de la ventana
        stage.title = application.configuration.getAsString('application.title')
        // Definimos el tamaño de la ventana
        stage.width = 640
        stage.height = 480
        // Deshabilitamos el botón de maximizar
        stage.setResizable(false)
        // Scene es el contenido de la ventana
        stage.scene = init()
        application.windowManager.attach('mainWindow', stage)
    }

    // Construye la Interfaz
    private Scene init() {
        tabPane = new TabPane()
        Scene scene = new Scene(tabPane)
        scene.fill = Color.WHITE
        scene
    }
}
