def  call() {

if(!env.TERRAFORM_DIR) {
    env.TERRAFORM_DIR = "./"
}

properties([
        parameters([
            choice(choices: 'dev\nprod', description: "Chose the Env", name: "ENV"),
            choice(choices: 'apply\ndestroy', description: "Choose apply or destroy", name: "ACTION"),
            string(choices: 'APP_VERSION', description: "Enter the version to deploy", name: "APP_VERSION"),
        ]) ,
    ])

    node {
      ansiColor('xterm') {
        sh "rm -rf *"
        git branch: 'main', url: "https://github.com/Shravan122/${REPONAME}.git"  

        stage('Terraform Init'){
            sh ''' 
                cd ${TERRAFORM_DIR}
                terrafile -f env-${ENV}/Terrafile
                terraform init -backend-config=env-${ENV}/${ENV}-backend.tfvars\
            '''
        }  

        stage('Terraform Plan'){
            sh ''' 
                cd ${TERRAFORM_DIR}
                export TF_VAR_APP_VERSION=${APP_VERSION}
                terraform plan -var-file=env-${ENV}/${ENV}.tfvars
            '''
        }  

        stage('Terraform Apply'){
            sh ''' 
                cd ${TERRAFORM_DIR}
                export TF_VAR_APP_VERSION=${APP_VERSION}
                terraform ${ACTION} -var-file=env-${ENV}/${ENV}.tfvars -auto-approve
            '''
            }            
        }
    }
}