#!/usr/bin/env groovy

import java.text.*

node {
   def yaml_path = 'https://raw.githubusercontent.com/oslomet-arx-as-a-service/AaaS/master/nais.yaml'

   def app_name = 'aaas'
   def namespace = 'default'
   def cluster = 'preprod.local'


   try {
       stage('Clean workspace') {
           cleanWs()
       }

       stage('Deploy app to nais') {
           sh "curl --fail -k -d '{\"application\": \"${app_name}\", \"version\": \"latest\", \"skipFasit\": true, \"namespace\": \"${namespace}\", \"manifesturl\": \"${yaml_path}\"}' https://daemon.nais.${cluster}/deploy"
       }
   } catch(e) {
       echo "Build failed"
       throw e
   }
}
