def call(Map config = [:]) {

    def imageName = config.imageName ?: 'kiruthik25/cvwj-devsecops-demo'
    def imageTag  = config.imageTag  ?: env.BUILD_NUMBER ?: 'latest'
    def registryCredentialsId = config.credentialsId ?: 'dockerhub-creds'

    sh """
        echo "Building Docker image ${imageName}:${imageTag}..."
        docker build -t ${imageName}:${imageTag} -t ${imageName}:latest .
    """

    withCredentials([usernamePassword(
        credentialsId: registryCredentialsId,
        usernameVariable: 'DOCKER_USER',
        passwordVariable: 'DOCKER_PASS'
    )]) {
        sh """
            echo "\$DOCKER_PASS" | docker login -u "\$DOCKER_USER" --password-stdin
            docker push ${imageName}:${imageTag}
            docker push ${imageName}:latest
        """
    }
}
