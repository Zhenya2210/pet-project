pipeline {
    agent any

    tools {
        gradle "7.1"
    }

    parameters{
        booleanParam(defaultValue: false, description: 'run rest tests', name: 'rest')
        booleanParam(defaultValue: true, description: 'run web tests', name: 'web')
        choice(choices: ['chrome', 'firefox'], description: 'run web tests via specific browser', name: 'browser')
        string(defaultValue:'', description: 'run web tests via specific version', name: 'version')
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
                        sh "./gradlew webTests -Pbrowser=${params.browser} -PbrowserVersion=${params.version}"
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
