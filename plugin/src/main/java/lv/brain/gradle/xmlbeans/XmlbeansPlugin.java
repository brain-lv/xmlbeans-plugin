package lv.brain.gradle.xmlbeans;

import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Arrays;

import static lv.brain.gradle.xmlbeans.XmlbeansExtension.*;

public class XmlbeansPlugin implements Plugin<Project> {

    @Override
    public void apply(@NotNull Project project) {
        XmlbeansExtension xsd = project.getExtensions().create("xsd", XmlbeansExtension.class, project);
        initPlugin(project);
        initTasks(project);

        project.afterEvaluate(p -> validate(project, xsd));
    }

    private void validate(Project project, XmlbeansExtension config) {
        final StringBuilder errorMessage = new StringBuilder();
        boolean hasError = false;

        // If xmlbeans is not found, add it as an api dependency
        if (!isHasXmlBeans(project)) {
            errorMessage.append("Missing  dependency xmlbeans\n").append("\n");
            hasError = true;
        }

        File schemaFile = config.getSchemaFileLocation();
        File schemaConfigFile = config.getSchemaConfigFileLocation();

        if (!schemaFile.exists()) {
            errorMessage.append("Missing schema file, please check that file exists: ").append(schemaFile).append("\n");
            hasError = true;
        }

        if (!schemaConfigFile.exists()) {
            errorMessage.append("Missing schema file, please check that file exists: ").append(schemaConfigFile).append("\n");
            hasError = true;
        }

        if (hasError) {
            errorMessage.append("\nPlease correct the above issues. Here's a sample configuration:\n");
            errorMessage.append("xsd{\n\s\sschema \"")
                    .append(config.getSchema())
                    .append("\"").
                    append("\n")
                    .append("\s\sconfig \"")
                    .append(config.getConfig())
                    .append("\"")
                    .append("\n")
                    .append("}")
                    .append("\n")
                    .append("dependencies {\n  api \"org.apache.xmlbeans:xmlbeans:3.1.0\"\n}");
            throw new GradleException("Your configuration is not complete:\n" + errorMessage);
        }
    }

    private void initTasks(Project project) {
        GenerateSourceFilesTask generateTask = project.getTasks().create("generate-source", GenerateSourceFilesTask.class);
        Task compileTask = project.getTasks().getByName("compileJava");
        compileTask.dependsOn(generateTask);

        CleanUpSourceFilesTask cleanupTask = project.getTasks().create("cleanup-source", CleanUpSourceFilesTask.class);
        Task clean = project.getTasks().getByName("clean");
        clean.dependsOn(cleanupTask);
    }

    private static boolean isHasXmlBeans(Project project) {
        DependencyHandler dependencies = project.getDependencies();
        // Check all configurations for xmlbeans dependency
        for (Dependency dependency : project.getConfigurations().detachedConfiguration(dependencies.create("xmlbeans")).getAllDependencies()) {
            if (dependency.getGroup() != null && dependency.getGroup().contains("xmlbeans") ||
                    dependency.getName().contains("xmlbeans")) {
                return true;
            }
        }
        return false;
    }

    public void initPlugin(Project project) {
        final PluginContainer plugins = project.getPlugins();
        boolean isJavaPluginApplied = plugins.hasPlugin("java");
        boolean isJavaLibraryPluginApplied = plugins.hasPlugin("java-library");

        // If neither is applied, apply 'java-library'
        if (!isJavaPluginApplied && !isJavaLibraryPluginApplied) {
            plugins.apply("java-library");
        }
        JavaPluginExtension javaPluginExtension = project.getExtensions().getByType(JavaPluginExtension.class);
        SourceSetContainer sourceSets = javaPluginExtension.getSourceSets();

        SourceSet mainSourceSet = sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME);
        mainSourceSet.getJava().setSrcDirs(Arrays.asList(
                project.file(PATH_SRC_MAIN_JAVA),
                project.file(PATH_SRC_MAIN_JAVA_GENERATED)
        ));
        mainSourceSet.getResources().setSrcDirs(Arrays.asList(
                project.file(PATH_SRC_MAIN_RESOURCES),
                project.file(PATH_SRC_MAIN_RESOURCES_GENERATED)
        ));

        SourceSet xsd = sourceSets.create("xsd");
        xsd.getResources().setSrcDirs(Arrays.asList(
                project.file(PATH_XSD_SCHEMA),
                project.file(PATH_XSD_SCHEMA_CONFIG)
        ));

        project.getDependencies().add("implementation", "com.github.javaparser:javaparser-symbol-solver-core:3.+");
    }
}
