def call() {
    node {
        git branch: 'main', url: "https://github.com/Shravan122/${COMPONENT}.git"
        env.APPTYPE="nodejs"
        common.sonarCheck()
        common.lintCheck()
        env.ARGS="-Dsonar.sources=."
        common.testCases()
        if (env.TAG_NAME != null) {
            common.artifact()
        }
    }
} 






        

    
        
