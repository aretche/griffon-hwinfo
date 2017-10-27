package io.github.aretche.hwlist

import griffon.core.artifact.GriffonModel
import griffon.metadata.ArtifactProviderFor
import griffon.transform.FXObservable

@ArtifactProviderFor(GriffonModel)
class HwInfoModel {

    String osName = ""
    String osVersion = ""
    String motherMarca = ""
    String motherModelo = ""
    String cpu = ""
    String cpuID = ""
    String netIF = ""
    String macAdress = ""
    @FXObservable
    String detail = ""


    String toString(){
        String out = ""
        if(osName)
            out += "OS name: ${osName}\n"
        if(osVersion)
            out += "OS version: ${osVersion}\n"
        if(motherMarca)
            out += "Base board: ${motherMarca}\n"
        if(motherModelo)
            out += "Base board model: ${motherModelo}\n"
        if(cpu)
            out += "Processor: ${cpu}\n"
        if(cpuID)
            out += "CPU ID: ${cpuID}\n"
        if(netIF)
            out += "Network IF: ${netIF}\n"
        if(macAdress)
            out += "MAC Address: ${macAdress}\n"
        out
    }
}