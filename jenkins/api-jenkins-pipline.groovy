timeout(180) {
    node('maven') {

        wrap([$class: 'BuildUser']) {
            summary = """|<b>Owner:</b> ${env.BUILD_USER}
	                            |<b>Branch:</b> ${BRANCH}""".stripMargin()
            currentBuild.description = summary.replaceAll("\n", "<br>")
            owner_user_id = "${env.BUILD_USER_ID}"
        }
        stage('Checkout') {
            checkout scm
        }
        stage('Run tests') {
            def tests_exit_code = sh(
                    returnStatus: true,
                    script: "mvn test",
            )

            if (tests_exit_code != 0) {
                currentBuild.result = 'UNSTABLE'
            }
        }
        stage('Publish allure results') {
            allure([
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'allure-results']]
            ])
        }
    }
}