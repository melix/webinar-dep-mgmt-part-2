import org.gradle.api.artifacts.ComponentMetadataContext
import org.gradle.api.artifacts.ComponentMetadataRule
import org.gradle.api.artifacts.dsl.DependencyHandler

// tag::rule[]
fun DependencyHandler.fixBadMetadata() = components {
    withModule("org.apache.camel:camel-cdi", FixApacheCamel::class.java)
}

class FixApacheCamel: ComponentMetadataRule {
    override fun execute(context: ComponentMetadataContext) = context.details.run {
        if (id.version.startsWith("2.15")) {
            allVariants {
                withDependencies {
                    removeAll { it.group.contains("deltaspike") }
                }
            }
        }
    }
}
// end::rule[]