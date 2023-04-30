pipeline {
    agent any

    tools {
        gradle "7.1"
    }

    parameters{
        booleanParam(defaultValue: true, description: 'run rest tests', name: 'rest')
        booleanParam(defaultValue: true, description: 'run web tests', name: 'web')
    }

    stages {
        stage('rest tests') {
            when {
                expression {return params.rest}
            }
            steps {
                sh "./gradlew restTests"
            }
        }
         stage('web tests') {
                    when {
                        expression {return params.web}
                    }
                    steps {
                        sh "./gradlew webTests"
                    }
         }
    }

    post {
        always {
            allure([
                reportBuildPolicy: 'ALWAYS',
                results: [[path: 'build/allure-results']]
            ])
        }
    }
}
