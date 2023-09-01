#!/usr/bin/env groovy

def buildDockerImage(String imageName) {
    echo "Building the docker image..."
    sh "docker build -t $imageName ."
}

def dockerLogin() {
    echo "Login to docker repository..."
    withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
        sh "echo $PASS | docker login -u $USER --password-stdin"
    }
}

def dockerPush(String imageName) {
    echo "Pushing the docker image..."
    sh "docker push $imageName"
}

// sss
def deployApplication(String imageName) {
    echo "Deploying the appication to EC2..."
    def shellCmd = "bash ./server-script-back.sh $imageName"
    sshagent(['jenkins-ssh-private-key']) {
        sh "scp ./server-script-back.sh azureuser@20.166.72.53:/home/azureuser"
        sh "scp ./docker-compose-spring.yaml azureuser@20.166.72.53:/home/azureuser"
        sh "ssh -o StrictHostKeyChecking=no azureuser@20.166.72.53 ${shellCmd}"
    }
}

return this
