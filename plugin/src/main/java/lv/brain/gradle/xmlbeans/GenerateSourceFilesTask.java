package lv.brain.gradle.xmlbeans;

import org.gradle.api.DefaultTask;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.TaskAction;

import java.io.File;

import static lv.brain.gradle.xmlbeans.XmlbeansExtension.*;

public class GenerateSourceFilesTask extends DefaultTask {
    public GenerateSourceFilesTask() {
        setGroup("xmlbeans");
    }


    @TaskAction
    public void generate() {
        XmlbeansExtension config = getProject().getExtensions().getByType(XmlbeansExtension.class);
        JavaExec javaExec = getProject().getTasks().create("scomp", JavaExec.class);
        javaExec.setGroup(GROUP);
        javaExec.getMainClass().set("org.apache.xmlbeans.impl.tool.SchemaCompiler");

        File file = config.getSchemaFileLocation();
        File file1 = config.getSchemaConfigFileLocation();

        Configuration runtimeClasspath = getProject().getConfigurations().getByName("runtimeClasspath");
        javaExec.classpath(runtimeClasspath);
        javaExec.args(
                "-src", getProject().file(PATH_SRC_MAIN_JAVA_GENERATED),
                "-d", getProject().file(PATH_SRC_MAIN_RESOURCES_GENERATED),
                "-srconly",
                file,
                file1

        );

        javaExec.systemProperty("user.dir", getProject().file(PATH_XSD_SCHEMA));
        javaExec.exec();
    }
}
