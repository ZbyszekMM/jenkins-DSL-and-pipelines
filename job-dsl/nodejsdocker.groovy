job('minimal-NodeJS-container-DSL') {
    scm {
        git('git://github.com/ZbyszekMM/minimal-nodejs-app.git') {  node -> // is hudson.plugins.git.GitSCM
            node / gitConfigName('DSL User')
            node / gitConfigEmail('jenkins-dsl@newtech.academy')
        }
    }
    triggers {
        scm('H/5 * * * *')
    }
    wrappers {
        nodejs('ZMNodeJS15-3-0') // this is the name of the NodeJS installation in 
                         // Manage Jenkins -> Configure Tools -> NodeJS Installations -> Name
    }
    steps {
        dockerBuildAndPublish {
            repositoryName('zbyszekm/minimal-nodejs-app')
            tag('${GIT_REVISION,length=9}')
            registryCredentials('dockerhub')  // to wywoła prośbę o dodanie nowych lub użycie credentials - ich id to 'dockerhub', tak są nazwane w Jenkins 
            forcePull(false)
            forceTag(false)
            createFingerprints(false)
            skipDecorate()
        }
    }
}
