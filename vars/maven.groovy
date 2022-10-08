def lintCheck() {
    sh ''' 
         echo Starting lint checks ${COMPONENT}
         mvn checkstyle:check || true                        # lint checks
         echo Lint Checks Completed for ${COMPONENT}
       
       ''' 
}

def sonarCheck() {
    sh ''' 
        sonar-scanner -Dsonar.host.url=http://172.31.0.180:9000  -Dsonar.sources=.  -Dsonar.projectKey=${COMPONENT} -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW}
       ''' 
}
def call() {
    pipeline {
        agent any 
    environment {
        SONAR      = credentials('SONAR') 
    }
        stages {
            stage('Lint Check') {
                steps {
                    script { 
                        lintCheck()
                    }
                }
            }
             stage('Sonar Check') {
                steps {
                    script { 
                        sonarCheck()
                        
                    }
                }
            }
        }    // end of statges 
    }
}