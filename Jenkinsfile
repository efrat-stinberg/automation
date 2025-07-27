pipeline {
    agent any
    
    parameters {
        string(name: 'REPO_URL', defaultValue: 'https://github.com/efrat-stinberg/automation.git', description: 'Repository URL')
        string(name: 'NAME_BRANCH', defaultValue: 'main', description: 'Branch name to execute')
    }
    
    environment {
        MAIN_BRANCH = 'main'
    }
    
    triggers {
        cron('30 5 * * 1')  // יום שני בשעה 05:30
    }
    
    stages {
        stage('Code Checkout') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    script {
                        echo "Starting code checkout stage"
                        
                        if (params.NAME_BRANCH == env.MAIN_BRANCH) {
                            echo "Using SCM checkout for main branch"
                            checkout scm
                        } else {
                            echo "Using manual git checkout for branch: ${params.NAME_BRANCH}"
                            git(
                                url: params.REPO_URL,
                                branch: params.NAME_BRANCH
                            )
                        }
                        
                        echo "Code checkout stage completed successfully"
                    }
                }
            }
        }
        
        stage('Check Maven') {
            steps {
                script {
                    echo "Checking for Maven installation..."
                    
                    if (isUnix()) {
                        try {
                            sh 'which mvn'
                            sh 'mvn --version'
                            echo "Maven found in system PATH"
                        } catch (Exception e) {
                            echo "Maven not found in PATH, trying alternative paths..."
                            sh 'ls -la /usr/bin/mvn* || echo "No mvn in /usr/bin"'
                            sh 'ls -la /usr/local/bin/mvn* || echo "No mvn in /usr/local/bin"'
                        }
                    } else {
                        try {
                            bat 'where mvn'
                            bat 'mvn --version'
                            echo "Maven found in system PATH"
                        } catch (Exception e) {
                            echo "Maven not found in PATH"
                        }
                    }
                }
            }
        }
        
        stage('Compilation') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    echo "Starting compilation stage"
                    
                    script {
                        if (isUnix()) {
                            // נסה מספר דרכים למצוא Maven
                            try {
                                sh 'mvn clean compile'
                            } catch (Exception e) {
                                echo "Standard mvn failed, trying /usr/bin/mvn"
                                try {
                                    sh '/usr/bin/mvn clean compile'
                                } catch (Exception e2) {
                                    echo "Trying /usr/local/bin/mvn"
                                    sh '/usr/local/bin/mvn clean compile'
                                }
                            }
                        } else {
                            bat 'mvn clean compile'
                        }
                    }
                    
                    echo "Compilation stage completed successfully"
                }
            }
        }
        
        stage('Run Tests') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    echo "Starting test execution stage"
                    
                    script {
                        if (isUnix()) {
                            try {
                                sh 'mvn test'
                            } catch (Exception e) {
                                echo "Standard mvn failed, trying /usr/bin/mvn"
                                try {
                                    sh '/usr/bin/mvn test'
                                } catch (Exception e2) {
                                    echo "Trying /usr/local/bin/mvn"
                                    sh '/usr/local/bin/mvn test'
                                }
                            }
                        } else {
                            bat 'mvn test'
                        }
                    }
                    
                    echo "Test execution stage completed successfully"
                }
            }
        }
    }
    
    post {
        success {
            echo "🎉 Pipeline completed successfully! All tests passed."
            echo "Branch executed: ${params.NAME_BRANCH}"
            echo "Repository: ${params.REPO_URL}"
        }
        
        failure {
            echo "❌ Pipeline failed. Please check the logs for details."
            echo "Failed branch: ${params.NAME_BRANCH}"
            echo "Repository: ${params.REPO_URL}"
        }
        
        always {
            echo "Pipeline execution finished at: ${new Date()}"
            cleanWs()
        }
    }
}
