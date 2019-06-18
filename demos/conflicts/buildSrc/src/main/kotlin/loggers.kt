import org.gradle.api.artifacts.ComponentMetadataContext
import org.gradle.api.artifacts.ComponentMetadataRule
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.DependencySubstitutions
import org.gradle.api.artifacts.dsl.DependencyHandler

// tag::rules[]
class CommonLoggingCapabilities : ComponentMetadataRule {
    override fun execute(context: ComponentMetadataContext) = context.run {
        details.allVariants {
            withCapabilities {
                addCapability("org.apache.commons", "commons-logging-impl", "1.0")
            }
        }
    }

}

class Slf4jImplementationCapabilities : ComponentMetadataRule {
    override fun execute(context: ComponentMetadataContext) = context.run {
        details.allVariants {
            withCapabilities {
                addCapability("org.slf4j", "slf4j-impl", "1.0")
            }
        }
    }
}
// end::rules[]

class Log4jCapabilities : ComponentMetadataRule {
    override fun execute(context: ComponentMetadataContext) = context.run {
        details.allVariants {
            withCapabilities {
                addCapability("log4j", "log4j-impl", "1.0")
            }
        }
    }
}

class CommonsLoggingCapabilities : ComponentMetadataRule {
    override fun execute(context: ComponentMetadataContext) = context.run {
        details.allVariants {
            withCapabilities {
                addCapability("commons-logging", "commons-logging-impl", "1.0")
            }
        }
    }
}

// tag::detect_conflicts[]
fun DependencyHandler.detectLoggerConflicts() = components {
    // both commons-logging and jcl-over-slf4j implement the commons-logging api
    withModule("org.slf4j:jcl-over-slf4j", CommonLoggingCapabilities::class.java)
    withModule("org.apache.commons:commons-logging", CommonLoggingCapabilities::class.java)

    // both slf4j-simple and slf4j-log4j12 implement the slf4j api
    withModule("org.slf4j:slf4j-simple", Slf4jImplementationCapabilities::class.java)
    withModule("org.slf4j:slf4j-log4j12", Slf4jImplementationCapabilities::class.java)

    // ...
// end::detect_conflicts[]

    // both log4j and log4j-over-slf4j implement the log4j api
    withModule("log4j:log4j", Log4jCapabilities::class.java)
    withModule("org.slf4j:log4j-over-slf4j", Log4jCapabilities::class.java)

    // both commons logging and slf4j implement the commons logging api
    withModule("commons-logging:commons-logging", CommonsLoggingCapabilities::class.java)
    withModule("org.slf4j:jcl-over-slf4j", CommonsLoggingCapabilities::class.java)
}

fun DependencySubstitutions.moduleInGraph(notation: String) = module("${notation}:0")

// tag::preference[]
fun Configuration.preferSlf4JSimple() = resolutionStrategy.dependencySubstitution {
    substitute(module("org.slf4j:slf4j-log4j12"))
            .because("prefer slf4j-simple over other implementations")
            .with(moduleInGraph("org.slf4j:slf4j-simple"))

    substitute(module("log4j:log4j"))
            .because("use the slf4j bridge for log4j")
            .with(module("org.slf4j:log4j-over-slf4j:1.7.16"))
}

fun Configuration.useSlf4jEverywhere() = resolutionStrategy.eachDependency {
    if (requested.name == "commons-logging") {
        useTarget("org.slf4j:jcl-over-slf4j:0")
    }
}
// end::preference[]