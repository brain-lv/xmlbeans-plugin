package lv.brain.gradle.xmlbeans;

import org.gradle.api.DefaultTask;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.TaskAction;

public class GenerateSourceFilesTask extends DefaultTask {
    public GenerateSourceFilesTask() {
        setGroup("xmlbeans");
    }



    @TaskAction
    public void generate() {
        JavaExec javaExec = getProject().getTasks().create("scomp", JavaExec.class);
        javaExec.setGroup("xmlbeans");
        javaExec.getMainClass().set("org.apache.xmlbeans.impl.tool.SchemaCompiler");

        Configuration runtimeClasspath = getProject().getConfigurations().getByName("runtimeClasspath");
        javaExec.classpath(runtimeClasspath);
        javaExec.args(
                "-src", getProject().file("src/main/java-generated"),
                "-d", getProject().file("src/main/resources-generated"),
                "-srconly",
                getProject().file("src/xsd/schema/" + getProject().getExtensions().getByType(XmlbeansExtension.class).getSchema()),
                getProject().file("src/xsd/config/" + getProject().getExtensions().getByType(XmlbeansExtension.class).getConfig())

        );

        javaExec.systemProperty("user.dir", getProject().file("src/xsd/schema"));
        javaExec.exec();
    }
}
