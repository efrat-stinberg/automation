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
        cron('0 14 * * *')  // כל יום בשעה 14:00
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
        
        stage('Code Validation') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    echo "Starting code validation stage"
                    
                    script {
                        // בדיקה שהקבצים קיימים
                        sh 'ls -la'
                        sh 'find . -name "*.java" | head -10'
                        sh 'find . -name "pom.xml" || echo "No pom.xml found"'
                        
                        // בדיקה שיש Java
                        sh 'java -version'
                        
                        echo "Found Java files:"
                        sh 'find . -name "*.java" -exec basename {} \\;'
                    }
                    
                    echo "Code validation stage completed successfully"
                }
            }
        }
        
        stage('Project Structure Check') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    echo "Starting project structure check"
                    
                    script {
                        sh 'echo "=== Project Structure ==="'
                        sh 'tree . || find . -type d | head -20'
                        
                        sh 'echo "=== Source Files ==="'
                        sh 'find src/ -name "*.java" || echo "No src directory found"'
                        
                        sh 'echo "=== Test Files ==="'
                        sh 'find . -name "*Test*.java" || echo "No test files found"'
                        
                        sh 'echo "=== Configuration Files ==="'
                        sh 'ls -la *.xml *.properties *.yml *.yaml 2>/dev/null || echo "No config files found"'
                    }
                    
                    echo "Project structure check completed successfully"
                }
            }
        }
    }
    
    post {
        success {
            echo "🎉 Pipeline completed successfully! Code validation passed."
            echo "Branch executed: ${params.NAME_BRANCH}"
            echo "Repository: ${params.REPO_URL}"
            echo "Note: This pipeline validates code structure. To run actual compilation and tests, Maven wrapper is needed."
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
