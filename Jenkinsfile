def VALID_BACKEND_SERVICES = [
    'cart', 'customer', 'inventory', 'location', 'media', 'order', 
    'product', 'rating', 'search', 'tax', 'recommendation', 'payment', 
    'sampledata', 'webhook', 'promotion', 'backoffice-bff', 'storefront-bff'
]

def VALID_FRONTEND_SERVICES = [
    'backoffice-ui': [dir: 'backoffice', image: 'yas-backoffice'],
    'storefront-ui': [dir: 'storefront', image: 'yas-storefront']
]

def writeGitOpsServiceOverride(String environmentName, String service, String tag, String imageRoot) {
    def filePath = "environments/${environmentName}/services/${service}.yaml"
    def cfg = fileExists(filePath) ? (readYaml(file: filePath) ?: [:]) : [:]
    def serviceCfg = (cfg[imageRoot] instanceof Map) ? cfg[imageRoot] : [:]
    def imageCfg = (serviceCfg.image instanceof Map) ? serviceCfg.image : [:]

    imageCfg.tag = tag
    serviceCfg.image = imageCfg
    cfg[imageRoot] = serviceCfg

    def tempFilePath = "${filePath}.tmp"
    writeYaml file: tempFilePath, data: cfg, overwrite: true
    sh "mv -f ${tempFilePath} ${filePath}"
}

def updateGitOpsRepo(String envName, String imageTag, List backendSvcs, List frontendSvcs) {
    withCredentials([string(credentialsId: env.GITOPS_TOKEN_CREDENTIALS_ID, variable: 'GITOPS_TOKEN')]) {
        def repoNoProtocol = env.GITOPS_REPO_URL.replaceFirst('https://', '')
        sh "rm -rf ${env.GITOPS_DIR} && git clone https://x-access-token:${GITOPS_TOKEN}@${repoNoProtocol} ${env.GITOPS_DIR}"
        
        dir("${env.GITOPS_DIR}") {
            backendSvcs.each { writeGitOpsServiceOverride(envName, it, imageTag, 'backend') }
            frontendSvcs.each { writeGitOpsServiceOverride(envName, it, imageTag, 'ui') }

            sh """
                git config user.name "${env.GITOPS_COMMIT_USER}"
                git config user.email "${env.GITOPS_COMMIT_EMAIL}"
                git add environments/${envName}/services
                if ! git diff --cached --quiet; then
                    git commit -m "ci(${envName}): update images to ${imageTag}"
                    git push origin HEAD:main
                fi
            """
        }
    }
}

def retagAndPushImage(String imageName, String sourceTag, String targetTag) {
    withCredentials([usernamePassword(credentialsId: env.DOCKER_CREDENTIALS_ID, usernameVariable: 'U', passwordVariable: 'P')]) {
        def source = "${env.DOCKERHUB_USER}/${imageName}:${sourceTag}"
        def target = "${env.DOCKERHUB_USER}/${imageName}:${targetTag}"
        sh """
            echo "$P" | docker login -u "$U" --password-stdin
            docker pull ${source}
            docker tag ${source} ${target}
            docker push ${target}
        """
    }
}

pipeline {
    agent any
    options {
        timestamps()
        disableConcurrentBuilds()
    }

    environment {
        DOCKERHUB_USER        = 'akiratomori'
        DOCKER_CREDENTIALS_ID = 'dockerhub-creds'
        GITOPS_REPO_URL       = "https://github.com/AkiraTomori/ArgoCD-Advanced.git"
        GITOPS_TOKEN_CREDENTIALS_ID = 'gitops-token'
        GITOPS_DIR            = 'gitops-yas'
        GITOPS_COMMIT_USER    = 'jenkins-bot'
        GITOPS_COMMIT_EMAIL   = 'jenkins@local'
    }

    stages {
        stage('Checkout & Detect Scope') {
            steps {
                checkout scm
                script {
                    env.BRANCH_NAME_RESOLVED = env.BRANCH_NAME ?: env.GIT_BRANCH ?: sh(script: 'git rev-parse --abbrev-ref HEAD', returnStdout: true).trim()
                    
                    if (env.BRANCH_NAME_RESOLVED == 'HEAD') {
                        env.BRANCH_NAME_RESOLVED = sh(script: 'git describe --tags --exact-match 2>/dev/null || echo HEAD', returnStdout: true).trim()
                    }

                    env.GIT_SHA = sh(script: 'git rev-parse --short=8 HEAD', returnStdout: true).trim()
                    env.IS_MAIN = (env.BRANCH_NAME_RESOLVED == 'main' || env.BRANCH_NAME_RESOLVED.endsWith('/main')).toString()
                    
                    def isTagFormat = env.BRANCH_NAME_RESOLVED ==~ /^v\d+\.\d+\.\d+.*$/
                    env.IS_RELEASE = (env.TAG_NAME != null || isTagFormat).toString()
                    
                    env.IMAGE_TAG = env.TAG_NAME ?: (isTagFormat ? env.BRANCH_NAME_RESOLVED : env.GIT_SHA)

                    env.BACKEND_TO_BUILD = ""
                    env.FRONTEND_TO_BUILD = ""

                    if (env.IS_MAIN.toBoolean()) {
                        env.BACKEND_TO_BUILD = VALID_BACKEND_SERVICES.join(',')
                        env.FRONTEND_TO_BUILD = VALID_FRONTEND_SERVICES.keySet().join(',')
                    }
                    else if (env.IS_RELEASE.toBoolean()) {
                        env.BACKEND_TO_BUILD = VALID_BACKEND_SERVICES.join(',')
                        env.FRONTEND_TO_BUILD = VALID_FRONTEND_SERVICES.keySet().join(',')
                    }
                    else {
                        def target = env.BRANCH_NAME_RESOLVED.replaceFirst(/^dev_/, '').replaceFirst(/_service$/, '')
                        if (VALID_BACKEND_SERVICES.contains(target)) env.BACKEND_TO_BUILD = target
                        else if (VALID_FRONTEND_SERVICES.containsKey(target)) env.FRONTEND_TO_BUILD = target
                        else error "Cannot determine service from branch: ${env.BRANCH_NAME_RESOLVED}"
                    }
                }
            }
        }

        stage('Compile Source Code (Fast)') {
            when { expression { return env.IS_RELEASE.toBoolean() == false } }
            steps {
                script {
                    if (env.BACKEND_TO_BUILD) {
                        echo "Building Maven modules: ${env.BACKEND_TO_BUILD}"
                        sh "mvn -B clean package -pl ${env.BACKEND_TO_BUILD} -am -DskipTests"
                    }
                }
            }
        }

        stage('Build & Push Docker (Parallel)') {
            when { expression { return env.IS_RELEASE.toBoolean() == false } }
            steps {
                script {
                    def dockerTasks = [:]

                    if (env.BACKEND_TO_BUILD) {
                        def beList = env.BACKEND_TO_BUILD.split(',')
                        beList.each { svc ->
                            dockerTasks["Docker-${svc}"] = {
                                dir(svc) {
                                    sh "docker build -t ${DOCKERHUB_USER}/yas-${svc}:${IMAGE_TAG} ."
                                    withCredentials([usernamePassword(credentialsId: "${DOCKER_CREDENTIALS_ID}", usernameVariable: 'U', passwordVariable: 'P')]) {
                                        sh 'echo "$P" | docker login -u "$U" --password-stdin'
                                        sh "docker push ${DOCKERHUB_USER}/yas-${svc}:${IMAGE_TAG}"
                                    }
                                }
                            }
                        }
                    }

                    if (env.FRONTEND_TO_BUILD) {
                        def feList = env.FRONTEND_TO_BUILD.split(',')
                        feList.each { svc ->
                            def config = VALID_FRONTEND_SERVICES[svc]
                            dockerTasks["Docker-${svc}"] = {
                                dir(config.dir) {
                                    sh "docker build -t ${DOCKERHUB_USER}/${config.image}:${IMAGE_TAG} ."
                                    withCredentials([usernamePassword(credentialsId: "${DOCKER_CREDENTIALS_ID}", usernameVariable: 'U', passwordVariable: 'P')]) {
                                        sh 'echo "$P" | docker login -u "$U" --password-stdin'
                                        sh "docker push ${DOCKERHUB_USER}/${config.image}:${IMAGE_TAG}"
                                    }
                                }
                            }
                        }
                    }

                    parallel dockerTasks
                }
            }
        }

        stage('CD Dev Update (Parallel Retag)') {
            when { expression { return env.IS_MAIN.toBoolean() } }
            steps {
                script {
                    def retagTasks = [:]
                    
                    VALID_BACKEND_SERVICES.each { svc ->
                        retagTasks["Retag-${svc}"] = { retagAndPushImage("yas-${svc}", env.IMAGE_TAG, 'latest') }
                    }
                    VALID_FRONTEND_SERVICES.each { k, v ->
                        retagTasks["Retag-${k}"] = { retagAndPushImage(v.image, env.IMAGE_TAG, 'latest') }
                    }
                    
                    parallel retagTasks

                    updateGitOpsRepo('dev', env.IMAGE_TAG, VALID_BACKEND_SERVICES, VALID_FRONTEND_SERVICES.keySet() as List)
                }
            }
        }

        stage('CD Staging Update (Parallel Retag)') {
            when { expression { return env.IS_RELEASE.toBoolean() } }
            steps {
                script {
                    def retagTasks = [:]
                    
                    VALID_BACKEND_SERVICES.each { svc ->
                        retagTasks["Retag-${svc}"] = { retagAndPushImage("yas-${svc}", 'latest', env.IMAGE_TAG) }
                    }
                    VALID_FRONTEND_SERVICES.each { k, v ->
                        retagTasks["Retag-${k}"] = { retagAndPushImage(v.image, 'latest', env.IMAGE_TAG) }
                    }
                    
                    parallel retagTasks

                    updateGitOpsRepo('staging', env.IMAGE_TAG, VALID_BACKEND_SERVICES, VALID_FRONTEND_SERVICES.keySet() as List)
                }
            }
        }
    }
    post { always { cleanWs() } }
}
