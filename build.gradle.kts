// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        mavenCentral()
        maven(url = "https://www.jitpack.io")
        maven ( url ="https://oss.sonatype.org/content/repositories/snapshots/" )

    }
    dependencies {
        classpath(BuildPlugins.android)
        classpath(BuildPlugins.kotlin)
        classpath(BuildPlugins.daggerHilt)
        classpath("com.google.gms:google-services:4.3.10")
        classpath ("com.github.ben-manes:gradle-versions-plugin:0.38.0")

    }
}

task("clean", Delete::class) {
    delete(rootProject.buildDir)
}