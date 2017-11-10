#!groovy

//noinspection GroovyAssignabilityCheck Jenkins requires this format
properties(
  [[$class: 'GithubProjectProperty', displayName: 'CMC Performance tests', projectUrlStr: 'https://github.com/hmcts/cmc-performance-tests/'],
   pipelineTriggers([
     [$class: 'hudson.triggers.TimerTrigger', spec: '0 0 * * *']
   ])]
)

//noinspection GroovyUnusedAssignment Jenkins requires an _ if not importing any classes
@Library('Reform') _

String channel = '#cmc-tech-notification'

node('docker') {
  try {
    stage('Checkout') {
      deleteDir()
      checkout scm
    }

    stage('Run performance tests') {
      env.IDAM_API_URL = 'http://betaDevBccidamAppLB.reform.hmcts.net:4551'
      env.URL = 'https://www-dev.moneyclaim.reform.hmcts.net'
      sh "./gradlew gatlingRun"

      gatlingArchive

      publishHTML target: [
        alwaysLinkToLastBuild: true,
        reportDir            : "build/results",
        reportFiles          : "*",
        reportName           : "Performance test report",
        keepAll              : true
      ]
    }
  } catch (e) {
    notifyBuildFailure channel: channel
    throw e
  }
  notifyBuildFixed channel: channel
}
