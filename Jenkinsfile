pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "demo-hexagon-architecture:latest"
    }

    stages {
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
                sh "docker run -d --name demo-hexagon-architecture -p 8081:8081 $DOCKER_IMAGE"
            }
        }
        stage('Healthcheck') {
            steps {
                script {
                    sh "sleep 25"
                    sh "curl --fail http://localhost:8081/actuator/health"
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline ejecutado correctamente: build, test, docker y deploy.'
        }
        failure {
            echo 'Pipeline falló, revisar logs.'
        }
    }
}