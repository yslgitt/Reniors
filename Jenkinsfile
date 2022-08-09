pipeline{
    agent any

    environment {
       BACK_CONTAINER_NAME="RENIORS_BACK"
       BACK_NAME = "RENIORS_BACK"

       FRONT_CONTAINER_NAME="RENIORS_FRONT"
       FRONT_NAME = "RENIORS_FRONT"
    }
    stages {
        stage('Clean'){
            steps{
                script {
                    try{
                        sh "docker stop ${BACK_CONTAINER_NAME}"
                        sleep 1
                        sh "docker rm ${BACK_CONTAINER_NAME}"
                        sleep 1
                        sh "docker stop ${BACK_CONTAINER_NAME}"
                        sleep 1
                        sh "docker rm ${BACK_CONTAINER_NAME}"
                    }catch(e){
                        sh 'exit 0'
                    }
                }
            }
        }
        stage('Build') {
            steps {
                sh "cd /var/jenkins_home/workspace/reniors/Back_end/reniors/"
                sh "docker build -t ${BACK_NAME} ."
                sh "cd /var/jenkins_home/workspace/reniors/Front_end/reniors/"
                sh "docker build -t ${FRONT_NAME} ."
            }
        }
        stage('Deploy'){
            steps {
                sh "docker run -d --name=${BACK_CONTAINER_NAME} -p 8080:8080 ${BACK_NAME}"
                sh "docker run -d --name=${FRONT_CONTAINER_NAME} -p 8081:80 ${FRONT_NAME}"
            }
        }
    }
}