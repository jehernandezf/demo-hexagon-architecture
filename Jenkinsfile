pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "demo-hexagon-architecture:latest"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master',
                    url: 'https://github.com/jehernandezf/demo-hexagon-architecture.git',
                    credentialsId: 'jehernandezf'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests=true'
            }
        }

        stage('Unit Tests') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Docker Build') {
            steps {
                sh "docker build -t $DOCKER_IMAGE ."
            }
        }

        stage('Docker Run & Validate') {
            steps {
                sh "docker run $DOCKER_IMAGE"
            }
        }
    }

    post {
        success {
            echo 'Pipeline ejecutado correctamente: build, test, docker y push.'
        }
        failure {
            echo 'Pipeline falló, revisar logs.'
        }
    }
}