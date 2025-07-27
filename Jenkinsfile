pipeline {
    agent any
    
    tools {
        maven 'Maven'  // שם ה-Maven tool שמוגדר ב-Jenkins
    }
    
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
        
        stage('Compilation') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    echo "Starting compilation stage"
                    
                    sh 'mvn clean compile'
                    
                    echo "Compilation stage completed successfully"
                }
            }
        }
        
        stage('Run Tests') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    echo "Starting test execution stage"
                    
                    sh 'mvn test'
                    
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
