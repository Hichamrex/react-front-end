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

def deployApplication(String imageName) {
    echo "Deploying the application to EC2..."
    def shellCmd = "bash ./server-script-back.sh $imageName"
     withCredentials([usernamePassword(credentialsId: 'azure-vm', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
        sh "scp -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no ./server-script-back.sh $USERNAME@20.166.72.53:/home/$USERNAME"
        sh "scp -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no ./docker-compose-spring.yaml $USERNAME@20.166.72.53:/home/$USERNAME"
        sh "echo $PASSWORD | sshpass --password-stdin ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no $USERNAME@20.166.72.53 ${shellCmd}"
    }
}

// def deployApplication(String imageName) {
//     echo "Deploying docker image to EC2"
//     def shellCmd = "bash ./server-script.sh $imageName"
//     sshagent(['ec2-server-key']) {
//         sh "scp ./server-script.sh ec2-user@15.188.59.125:/home/ec2-user"
//         sh "scp ./docker-compose.yaml ec2-user@15.188.59.125:/home/ec2-user"
//         sh "ssh -o StrictHostKeyChecking=no ec2-user@15.188.59.125 ${shellCmd}"
//     }
// }

return this
