package lv.brain.gradle.xmlbeans;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Delete;

import java.util.Arrays;
import java.util.HashSet;

public class CleanUpSourceFilesTask extends Delete {
    public CleanUpSourceFilesTask() {
        this.setGroup("xmlbeans");
        this.setDelete(new HashSet<>(Arrays.asList(
                getProject().file("/src/main/java-generated"),
                getProject().file("/src/main/resources-generated")
        )));
    }
}
