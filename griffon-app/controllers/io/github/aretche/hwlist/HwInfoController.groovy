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

        String ifaceName = getPrimaryIfaceName()
        if(ifaceName)
            setNetworkInfo(ifaceName)

        model.detail = model.toString()
    }

    // Trata de obtener el nombre de la interfaz de red primaria
    String getPrimaryIfaceName(){
        SystemInfo si = new SystemInfo()
        OperatingSystem os = si.getOperatingSystem()
        HardwareAbstractionLayer hal = si.getHardware()

        String gatewayIP = os.getNetworkParams().ipv4DefaultGateway
        String iface
        // Trato de encontrar una interfaz en la misma subred que el gateway
        hal.getNetworkIFs().each { NetworkIF net ->
            if(!iface){
                net.IPv4addr.each {
                    if(!iface){
                        if(mismaRedClaseC(it, gatewayIP)){
                            iface = net.name
                        }
                    }
                }
            }
        }
        // Si no encontré una placa con IP en la red del default Gateway
        // tomo la primera placa de red con tráfico entrante
        if(!iface){
            hal.getNetworkIFs().any { NetworkIF net ->
                if(net.getBytesRecv() > 0){
                    iface = net.name
                    true
                } else {
                    return
                }
            }
        }
        iface
    }

    // Setea los parámetros de hardware con la interfaz de red ifName
    def setNetworkInfo(String ifName){
        SystemInfo si = new SystemInfo()
        HardwareAbstractionLayer hal = si.getHardware()
        model.netIF = model.macAdress = ""
        hal.getNetworkIFs().each { NetworkIF net ->
            if(ifName == net.name){
                model.netIF = net.name
                model.macAdress = net.getMacaddr()
            }
        }
    }

    //Compara los tres primeros octetos de dos direcciones IP
    Boolean mismaRedClaseC(String ip1, String ip2){
        (ip1?.split('\\.')[0..2]?.join('.') == ip2?.split('\\.')[0..2]?.join('.'))
    }
}