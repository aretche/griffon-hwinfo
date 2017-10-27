application {
    title = 'Info de Hardware con Griffon'
    startupGroups = ['app']
    autoShutdown = true
}
mvcGroups {
    'app' {
        view       = 'io.github.aretche.hwlist.AppView'
    }
    'hwInfo' {
        model      = 'io.github.aretche.hwlist.HwInfoModel'
        view       = 'io.github.aretche.hwlist.HwInfoView'
        controller = 'io.github.aretche.hwlist.HwInfoController'
    }
}