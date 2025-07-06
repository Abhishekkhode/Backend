// Jenkinsfile for a Spring Boot Backend Project (Maven example)

pipeline {
    agent any

    tools {
        jdk 'JDK 17'
        maven 'Maven 3.8.8'
    }

    stages {
        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Build Project') {
            steps {
                // CHANGE THIS LINE: from sh to bat
                bat 'mvn clean install -DskipTests'
            }
        }

        stage('Run Tests') {
            steps {
                // CHANGE THIS LINE: from sh to bat
                bat 'mvn test'
                junit '**/target/surefire-reports/*.xml'
            }
        }

        stage('Package Application') {
            steps {
                // CHANGE THIS LINE: from sh to bat
                bat 'mvn package'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished.'
        }
        success {
            echo 'Pipeline succeeded! 🎉'
        }
        failure {
            echo 'Pipeline failed! 😭 Check console output for errors.'
        }
    }
}