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
        cron('30 5 * * 1\n0 14 * * *')  // יום שני בשעה 05:30 וכל יום בשעה 14:00
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
        
        stage('Verify Environment') {
            steps {
                script {
                    echo "Starting environment verification"
                    
                    // בדיקת Java
                    sh 'java -version'
                    
                    // בדיקת Maven Wrapper
                    sh 'ls -la mvnw* || echo "Maven wrapper script not found"'
                    sh 'ls -la .mvn/wrapper/ || echo "Maven wrapper directory not found"'
                    
                    // הרשאות לקובץ mvnw
                    sh 'chmod +x mvnw'
                    
                    echo "Environment verification completed successfully"
                }
            }
        }
        
        stage('Compilation') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    echo "Starting compilation stage"
                    
                    script {
                        sh './mvnw clean compile -B'
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
                        sh './mvnw test -B'
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
            
            // ניקוי workspace
            cleanWs()
        }
    }
}
