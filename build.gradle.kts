plugins {
    id("org.gradle.presentation.asciidoctor")
}

presentation {
    githubUserName.set("ljacomet")
    githubRepoName.set("voxxed-lux-gestion-deps")
}

dependencies {
    asciidoctor("org.asciidoctor:asciidoctorj-diagram:1.5.11")}

tasks {
    asciidoctor {
        requires("asciidoctor-diagram")
    }

}