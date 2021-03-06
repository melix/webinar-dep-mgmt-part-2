plugins {
    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.camel:camel-cdi:2.15.0")
    implementation("org.apache.spark:spark-core_2.12:2.4.3")
    implementation("org.apache.ivy:ivy:2.3.0")
    implementation("org.slf4j:slf4j-api:1.7.16")

    testImplementation("junit:junit:4.12")
    testImplementation("net.sourceforge.htmlunit:htmlunit:2.9")
    testImplementation("org.slf4j:slf4j-simple:1.7.16")
}

