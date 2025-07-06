// Jenkinsfile for a Spring Boot Backend Project (Maven example)

pipeline {
    agent any // Specifies that the pipeline can run on any available Jenkins agent

    // It's crucial to have your JDK and Maven/Gradle configured in Jenkins!
    // Go to Dashboard -> Manage Jenkins -> Tools to set these up.
    tools {
        // Replace 'JDK 17' with the name you gave your JDK installation in Jenkins Tools
        jdk 'JDK 17'
        // Replace 'Maven 3.8.8' with the name you gave your Maven installation in Jenkins Tools
        maven 'Maven 3.8.8'
    }

    stages {
        stage('Checkout Code') {
            steps {
                // When using 'Pipeline script from SCM', Jenkins handles the initial checkout
                // of the Jenkinsfile itself. For the rest of the code, 'checkout scm'
                // tells Jenkins to check out the current SCM configuration (your repo).
                checkout scm
            }
        }

        stage('Build Project') {
            steps {
                // Use the configured Maven tool to clean and install dependencies.
                // -DskipTests is added to make the build faster initially, you'll add tests later.
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Run Tests') {
            steps {
                // Run your unit and integration tests
                sh 'mvn test'
                // Publish test results so Jenkins can display them
                junit '**/target/surefire-reports/*.xml'
            }
        }

        stage('Package Application') {
            steps {
                // Create the final runnable JAR file
                sh 'mvn package'
                // Archive the JAR artifact so you can download it from Jenkins later
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        // You can add more stages here like 'Deploy to Dev', 'Docker Build', etc.
        // But for a beginner, these core stages are a great start.
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