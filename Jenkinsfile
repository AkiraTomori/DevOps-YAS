/*
 * Jenkinsfile: CI/CD Monorepo - Parallel Build Backend & Frontend
 * GitOps Integration với ArgoCD
 */

def VALID_BACKEND_SERVICES = [
    'cart', 'customer', 'inventory', 'location', 'media', 'order', 
    'product', 'rating', 'search', 'tax', 'recommendation', 'payment', 
    'payment-paypal', 'sampledata', 'webhook', 'promotion', 'backoffice-bff', 'storefront-bff'
    
]

def VALID_FRONTEND_SERVICES = [
    'backoffice-ui': [dir: 'backoffice', image: 'yas-backoffice'],
    'storefront-ui': [dir: 'storefront', image: 'yas-storefront']
]

// Hàm ghi đè file YAML GitOps (Phân biệt backend và ui)
def writeGitOpsServiceOverride(String environmentName, String service, String tag, String imageRoot) {
    writeFile(
        file: "environments/${environmentName}/services/${service}.yaml",
        text: """\
            ${imageRoot}:
              image:
                tag: "${tag}"
        """.stripIndent()
    )
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
                    git commit -m "ci(${envName}): update images to ${imageTag} [skip ci]"
                    git push origin HEAD:main
                fi
            """
        }
    }
}

// Hàm Retag Image cho cả Backend & Frontend
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
        stage('Checkout') {
            steps { checkout scm }
        }

        stage('Detect Scope & Tag') {
            steps {
                script {
                    env.BRANCH_NAME_RESOLVED = env.BRANCH_NAME ?: env.GIT_BRANCH ?: sh(script: 'git rev-parse --abbrev-ref HEAD', returnStdout: true).trim()
                    env.GIT_SHA = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
                    env.IS_MAIN = (env.BRANCH_NAME_RESOLVED == 'main' || env.BRANCH_NAME_RESOLVED.endsWith('/main')).toString()
                    env.IS_RELEASE = (env.TAG_NAME != null).toString()
                    env.IMAGE_TAG = env.TAG_NAME ?: env.GIT_SHA
                }
            }
        }

        stage('Build & Push Parallel') {
            when { expression { return env.IS_RELEASE.toBoolean() == false } }
            steps {
                script {
                    def backendToBuild = []
                    def frontendToBuild = []

                    if (env.IS_MAIN.toBoolean()) {
                        backendToBuild = VALID_BACKEND_SERVICES
                        frontendToBuild = VALID_FRONTEND_SERVICES.keySet() as List
                    } else {
                        // Nhận diện service từ branch (vd: dev_media_service hoặc dev_backoffice-ui_service)
                        def target = env.BRANCH_NAME_RESOLVED.replaceFirst(/^dev_/, '').replaceFirst(/_service$/, '')
                        if (VALID_BACKEND_SERVICES.contains(target)) backendToBuild = [target]
                        else if (VALID_FRONTEND_SERVICES.containsKey(target)) frontendToBuild = [target]
                        else error "Không xác định được service từ branch: ${env.BRANCH_NAME_RESOLVED}"
                    }

                    def tasks = [:]

                    // Tạo task build Backend
                    backendToBuild.each { svc ->
                        tasks["Backend-${svc}"] = {
                            node {
                                stage("Build Backend ${svc}") {
                                    checkout scm
                                    sh "mvn -B clean package -pl ${svc} -am -DskipTests"
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
                    }

                    // Tạo task build Frontend
                    frontendToBuild.each { svc ->
                        tasks["Frontend-${svc}"] = {
                            node {
                                stage("Build Frontend ${svc}") {
                                    checkout scm
                                    def config = VALID_FRONTEND_SERVICES[svc]
                                    dir(config.dir) {
                                        sh "npm install && npm run build" // Bỏ qua test/lint
                                        sh "docker build -t ${DOCKERHUB_USER}/${config.image}:${IMAGE_TAG} ."
                                        withCredentials([usernamePassword(credentialsId: "${DOCKER_CREDENTIALS_ID}", usernameVariable: 'U', passwordVariable: 'P')]) {
                                            sh 'echo "$P" | docker login -u "$U" --password-stdin'
                                            sh "docker push ${DOCKERHUB_USER}/${config.image}:${IMAGE_TAG}"
                                        }
                                    }
                                }
                            }
                        }
                    }

                    parallel tasks
                }
            }
        }

        stage('CD Dev Update') {
            when { expression { return env.IS_MAIN.toBoolean() } }
            steps {
                script {
                    // Retag sang 'main' cho tất cả
                    VALID_BACKEND_SERVICES.each { retagAndPushImage("yas-${it}", env.IMAGE_TAG, 'latest') }
                    VALID_FRONTEND_SERVICES.each { k, v -> retagAndPushImage(v.image, env.IMAGE_TAG, 'latest') }

                    updateGitOpsRepo('dev', env.IMAGE_TAG, VALID_BACKEND_SERVICES, VALID_FRONTEND_SERVICES.keySet() as List)
                }
            }
        }

        stage('CD Staging Update') {
            when { expression { return env.IS_RELEASE.toBoolean() } }
            steps {
                script {
                    // Lấy từ main retag sang tag version
                    VALID_BACKEND_SERVICES.each { retagAndPushImage("yas-${it}", 'latest', env.IMAGE_TAG) }
                    VALID_FRONTEND_SERVICES.each { k, v -> retagAndPushImage(v.image, 'latest', env.IMAGE_TAG) }

                    updateGitOpsRepo('staging', env.IMAGE_TAG, VALID_BACKEND_SERVICES, VALID_FRONTEND_SERVICES.keySet() as List)
                }
            }
        }
    }
    post { always { cleanWs() } }
}