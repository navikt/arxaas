gcloud auth activate-service-account --key-file google_cloud_api_key.json
gcloud --quiet config set project carbon-sunup-229608
gcloud --quiet config set container/cluster aaaas-service
gcloud --quiet config set compute/zone europe-north1-a
gcloud --quiet container clusters get-credentials aaaas-service
kubectl config view
kubectl config current-context
kubectl apply  -f gcloud_deploy.yaml