package io.github.aretche.hwlist

import griffon.core.artifact.GriffonService
import oshi.SystemInfo
import oshi.hardware.NetworkIF

/**
 * Created by arellanog on 27/10/17.
 */
@javax.inject.Singleton
@griffon.metadata.ArtifactProviderFor(GriffonService)
class HwInfoService {

    // Retorna el nombre del Sistema Operativo
    String getOsName(SystemInfo si = null){
        if(!si)
            si = new SystemInfo()
        si.operatingSystem.family
    }

    // Retorna el nombre del Sistema Operativo
    String getOsVersion(SystemInfo si = null){
        if(!si)
            si = new SystemInfo()
        si.operatingSystem.version
    }

    // Retorna el modelo del procesador
    String getProcessor(SystemInfo si = null){
        if(!si)
            si = new SystemInfo()
        si.hardware.processor.identifier
    }

    // Retorna el ID del procesador
    String getProcessorId(SystemInfo si = null){
        if(!si)
            si = new SystemInfo()
        si.hardware.processor.processorID
    }

    // Retorna la marca del MotherBoard
    String getbaseBoardMaker(SystemInfo si = null){
        if(!si)
            si = new SystemInfo()
        si.hardware.computerSystem.baseboard.manufacturer
    }

    // Retorna el modelo del MotherBoard
    String getbaseBoardModel(SystemInfo si = null){
        if(!si)
            si = new SystemInfo()
        si.hardware.computerSystem.baseboard.model
    }

    // Retorna la MAC address de la interfaz con nombre ifName
    String getIfaceMacAddress(String ifName, SystemInfo si = null){
        String ifaceMacAddress
        if(!si)
            si = new SystemInfo()
        si.hardware.networkIFs.each { NetworkIF net ->
            if(ifName == net.name){
               ifaceMacAddress = net.macaddr
            }
        }
        ifaceMacAddress
    }

    // Trata de obtener el nombre de la interfaz de red primaria
    String getPrimaryIfaceName(SystemInfo si = null){
         if(!si)
            si = new SystemInfo()

        String gatewayIP = si.operatingSystem.networkParams.ipv4DefaultGateway
        String iface
        // Trato de encontrar una interfaz en la misma subred que el gateway
        si.hardware.networkIFs.each { NetworkIF net ->
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
            si.hardware.networkIFs.any { NetworkIF net ->
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

    //Compara los tres primeros octetos de dos direcciones IP
    private Boolean mismaRedClaseC(String ip1, String ip2){
        (ip1?.split('\\.')[0..2]?.join('.') == ip2?.split('\\.')[0..2]?.join('.'))
    }
}
