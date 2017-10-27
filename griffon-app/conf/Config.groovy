application {
    title = 'griffon-hwlist'
    startupGroups = ['griffonHwlist']
    autoShutdown = true
}
mvcGroups {
    // MVC Group for "griffonHwlist"
    'griffonHwlist' {
        model      = 'io.github.aretche.hwlist.GriffonHwlistModel'
        view       = 'io.github.aretche.hwlist.GriffonHwlistView'
        controller = 'io.github.aretche.hwlist.GriffonHwlistController'
    }
}