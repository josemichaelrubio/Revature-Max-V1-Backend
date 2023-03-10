pipeline {
   agent any

    environment {
        PORT="80"
        IMAGE_TAG="revature-max-image"
        CONTAINER_NAME="revature-max-container"
        DB_URL=credentials('DB_URL')
        DB_USER=credentials('DB_USER')
        DB_PASS=credentials('DB_PASS')
    }

   stages {
      stage('checkout'){
          steps {
               git branch: 'master',
               url: 'https://gitlab.com/210301-java-azure/p2-revengers/revature-max-backend.git'
           }
      }
      stage('clean') {
         steps {
            sh 'sh gradlew clean'
         }
      }
      stage('package') {
         steps {
            sh 'sh gradlew bootJar'
         }
      }
      stage('remove previous image') {
            steps {
                sh 'docker rmi -f ${IMAGE_TAG} || true'
            }
        }

       stage('create new image') {
            steps {
                sh 'docker build -t ${IMAGE_TAG} -f Dockerfile .'
            }
        }
        stage('remove previous container') {
            steps {
                sh 'docker stop ${CONTAINER_NAME} || true'
                sh 'docker system prune'
            }
        }
        stage('create/run new container') {
            steps {
                sh 'docker run -d --rm -p ${PORT}:${PORT} -e DB_USER -e DB_PASS -e DB_URL --name ${CONTAINER_NAME} ${IMAGE_TAG}'
            }
        }
    }
}
