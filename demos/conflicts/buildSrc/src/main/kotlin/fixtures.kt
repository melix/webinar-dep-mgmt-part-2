import org.gradle.api.artifacts.ModuleDependency

// tag::exclude[]
fun ModuleDependency.withoutFeatureWhichRequires(group: String, module: String) {
    exclude(mapOf("group" to group, "module" to module))
    because("we don't use the features of ${name} which require ${group}:${module}")
}
// tag::exclude[]
