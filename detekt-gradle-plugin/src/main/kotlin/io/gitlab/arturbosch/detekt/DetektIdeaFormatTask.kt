package io.gitlab.arturbosch.detekt

import io.gitlab.arturbosch.detekt.extensions.IdeaExtension
import io.gitlab.arturbosch.detekt.invoke.ProcessExecutor.startProcess
import org.gradle.api.file.FileCollection
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.SourceTask
import org.gradle.api.tasks.TaskAction
import org.gradle.language.base.plugins.LifecycleBasePlugin

/**
 * @author Artur Bosch
 * @author Marvin Ramin
 */
open class DetektIdeaFormatTask : SourceTask() {

    init {
        description = "Uses an external idea installation to format your code."
        group = LifecycleBasePlugin.VERIFICATION_GROUP
    }

    @Deprecated("Replace with getSource/setSource")
    var input: FileCollection
        get() = source
        set(value) = setSource(value)

    @Internal
    @Optional
    var debug: Property<Boolean> = project.objects.property(Boolean::class.javaObjectType)

    @Internal
    lateinit var ideaExtension: IdeaExtension

    @TaskAction
    fun format() {
        val debugState = debug.getOrElse(false)
        if (debugState) {
            println("$ideaExtension")
        }
        startProcess(ideaExtension.formatArgs(source.asPath), debugState)
    }
}
