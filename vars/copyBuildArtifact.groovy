def call(Map config = [:]) {

    copyArtifacts(
        projectName: config.projectName ?: env.JOB_NAME,
        selector: config.selector ?: lastSuccessful(),
        fingerprintArtifacts: true
    )
}
