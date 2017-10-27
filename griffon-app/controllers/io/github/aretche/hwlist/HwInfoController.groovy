package io.github.aretche.hwlist

import griffon.core.artifact.GriffonController
import griffon.core.controller.ControllerAction
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import griffon.transform.Threading
import oshi.SystemInfo
import oshi.hardware.HardwareAbstractionLayer
import oshi.hardware.NetworkIF
import oshi.software.os.OperatingSystem

import javax.annotation.Nonnull

@ArtifactProviderFor(GriffonController)
class HwInfoController {
    @MVCMember @Nonnull
    HwInfoModel model

    @ControllerAction
    @Threading(Threading.Policy.INSIDE_UITHREAD_ASYNC)
    void refreshInfo() {
        SystemInfo si = new SystemInfo()
        OperatingSystem os = si.getOperatingSystem()
        model.osName = os.family
        model.osVersion = os.version

        HardwareAbstractionLayer hal = si.getHardware()
        model.motherMarca = hal.computerSystem.baseboard.manufacturer
        model.motherModelo = hal.computerSystem.baseboard.model

        model.cpuID = hal.processor.getProcessorID()
        model.cpu = hal.processor.getIdentifier()

        // Tomo la primera placa de red con tráfico
        hal.getNetworkIFs().any { NetworkIF net ->
            if(net.getBytesRecv() > 0){
                setNetworkInfo(net.name)
                true
            } else {
                return
            }
        }

        model.detail = model.toString()
    }

    def setNetworkInfo(String ifName){
        SystemInfo si = new SystemInfo()
        HardwareAbstractionLayer hal = si.getHardware()
        model.netIF = model.macAdress = ""
        hal.getNetworkIFs().any { NetworkIF net ->
            if(ifName == net.name){
                model.netIF = net.name
                model.macAdress = net.getMacaddr()
            }
        }
    }
}