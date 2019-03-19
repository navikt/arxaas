#!/usr/bin/env groovy

import java.text.*

node {
   def yaml_path

   def app_name = 'aaas'
   def namespace = 'default'
   def cluster = 'preprod.local'


   try {
       stage('Clean workspace') {
           cleanWs()
       }

       stage('Upload nais.yaml to nexus server') {
           yaml_path = "https://repo.adeo.no/repository/raw/nais/${app_name}/latest/nais.yaml"
           sh "curl -s -S --upload-file nais.yaml ${yaml_path}"
       }

       stage('Deploy app to nais') {
           sh "curl --fail -k -d '{\"application\": \"${app_name}\", \"version\": \"latest\", \"skipFasit\": true, \"namespace\": \"${namespace}\", \"manifesturl\": \"${yaml_path}\"}' https://daemon.nais.${cluster}/deploy"
       }
   } catch(e) {
       echo "Build failed"
       throw e
   }
}
