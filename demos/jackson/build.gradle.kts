
plugins {
    `java-library`
}

repositories {
    jcenter()
}

dependencies {
    implementation(platform("com.fasterxml.jackson:jackson-bom:2.9.5"))
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml")
    implementation("io.vertx:vertx-core:3.7.1")

    testImplementation("junit:junit:4.12")
    
    components.all(JacksonAlignmentRule::class.java)
}

open class JacksonAlignmentRule : ComponentMetadataRule {
    override fun execute(ctx: ComponentMetadataContext) {
        ctx.details.run {
            if (id.group.startsWith("com.fasterxml.jackson")) {
                belongsTo("my.jackson:jackson-platform:${id.version}")
            }
        }
    }

}
