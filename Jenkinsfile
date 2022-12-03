def grScript
pipeline {
    agent any
    environment {
        IMAGE_NAME = 'hichamrex/apps-repo:react-app-1.1.0'
    }
    stages {
        stage("init") {
            steps {
                // script {
                //     echo "Loading the script..."
                //     grScript = load "script.groovy"
                // }
            }
        }
        stage("build image") {
            steps {
                // script {
                //     grScript.buildDockerImage(env.IMAGE_NAME)
                //     grScript.dockerLogin()
                //     grScript.dockerPush(env.IMAGE_NAME)
                // }
            }
        }
        stage("deploy") {
            steps {
                // script {
                //     grScript.deployApplication(env.IMAGE_NAME)
                // }
            }
        }
    }
}