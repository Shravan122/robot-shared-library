def sonarCheck() {
   stage('Sonar Checks') {
    sh ''' 
        # sonar-scanner -Dsonar.host.url=http:/172.31.0.180/:9000 -Dsonar.projectKey=${COMPONENT} -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW} ${ARGS}
        # sonar-quality-gate.sh ${SONAR_USR} ${SONAR_PSW} 172.31.0.180 ${COMPONENT} || true
        echo sonarchecks for ${COMPONENT}
        
      '''
   } 
}