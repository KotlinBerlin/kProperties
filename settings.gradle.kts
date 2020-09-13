rootProject.name = "kProperties"

include("kProperties-core")
include("kProperties-jfx")

rootProject.children.forEach {
    it.buildFileName = "${it.name}.gradle.kts"
    if(!it.buildFile.exists()) {
        println("Build File not existing: ${it.buildFile.absolutePath}")
    }
}
