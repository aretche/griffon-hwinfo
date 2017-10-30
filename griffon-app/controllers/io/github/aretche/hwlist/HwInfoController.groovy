package io.github.aretche.hwlist

import griffon.core.artifact.GriffonController
import griffon.core.controller.ControllerAction
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import griffon.transform.Threading

import javax.annotation.Nonnull
import javax.inject.Inject

@ArtifactProviderFor(GriffonController)
class HwInfoController {
    @MVCMember @Nonnull
    HwInfoModel model

    @Inject
    private HwInfoService hwInfoService

    @ControllerAction
    @Threading(Threading.Policy.INSIDE_UITHREAD_ASYNC)
    void refreshInfo() {
        model.osName = hwInfoService.getOsName()
        model.osVersion = hwInfoService.getOsVersion()

        model.motherMarca = hwInfoService.getbaseBoardMaker()
        model.motherModelo = hwInfoService.getbaseBoardModel()

        model.cpuID = hwInfoService.getProcessorId()
        model.cpu = hwInfoService.getProcessor()

        String ifaceName = hwInfoService.getPrimaryIfaceName()
        if(ifaceName){
            model.netIF = ifaceName
            model.macAdress = hwInfoService.getIfaceMacAddress(ifaceName)
        }

        model.rootFsDevice = hwInfoService.getRootFsDeviceName()
        model.rootDiskSerial = hwInfoService.getDiscSerialNumber(model.rootFsDevice)

        model.detail = model.toString()
    }
}