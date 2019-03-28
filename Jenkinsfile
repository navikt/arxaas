#!/usr/bin/env groovy

import java.text.*

node {
    def yaml_path

    def app_name = 'arxaas'
    def dependency_name = 'arx_dependency'
    def namespace = 'default'
    def cluster = 'preprod.local'
    def date = new Date()
    def datestring = new SimpleDateFormat("yyyy-MM-dd").format(date)

    try {
        stage('Clean workspace') {
            cleanWs()
        }

        stage('Checkout ARX dependency') {
            checkout(
                    scm: [$class           : 'GitSCM',
                          branches         : [[name: '*/master']],
                          extensions       : [[$class: 'RelativeTargetDirectory', relativeTargetDir: "${dependency_name}"]],
                          userRemoteConfigs: [[url: "https://github.com/oslomet-arx-as-a-service/arx-dependency.git"]]]
            )
        }

        stage('Checkout ARXaaS') {
            checkout(
                    scm: [$class           : 'GitSCM',
                          branches         : [[name: '*/master']],
                          extensions       : [[$class: 'RelativeTargetDirectory', relativeTargetDir: "${app_name}"]],
                          userRemoteConfigs: [[url: "https://github.com/oslomet-arx-as-a-service/AaaS.git"]]]
            )
            def git_commit_hash = sh(script: "git --git-dir=./${app_name}/.git rev-parse --short HEAD", returnStdout: true).trim()
            version_tag = "${datestring}-${git_commit_hash}"
        }

        stage('maven build') {
            dir("${dependency_name}") {
                sh "mvn -q install:install-file -Dfile=libarx-3.7.1.jar -DgroupId=org.deidentifier -DartifactId=libarx -Dversion=3.7.1 -Dpackaging=jar"
            }
        }

        stage('Build docker image') {
            dir("${app_name}") {
                app = docker.build("${app_name}")
            }
        }
        stage('Push docker image') {
            docker.withRegistry('https://repo.adeo.no:5443', 'nexus-credentials') {
                app.push("${version_tag}")
            }
        }

        stage('Upload nais.yaml to nexus server') {
            yaml_path = "https://repo.adeo.no/repository/raw/nais/${app_name}/latest/nais.yaml"
            sh "curl -s -S --upload-file nais.yaml ${yaml_path}"
        }

        stage('Deploy app to nais') {
            sh "curl --fail -k -d '{\"application\": \"${app_name}\", \"version\": \"${version_tag}\", \"skipFasit\": true, \"namespace\": \"${namespace}\", \"manifesturl\": \"${yaml_path}\"}' https://daemon.nais.${cluster}/deploy"
        }
    } catch (e) {
        echo "Build failed"
        throw e
    }
}
