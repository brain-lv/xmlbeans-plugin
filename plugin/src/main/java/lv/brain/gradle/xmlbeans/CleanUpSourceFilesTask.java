package lv.brain.gradle.xmlbeans;

import org.gradle.api.tasks.Delete;

import java.util.Arrays;
import java.util.HashSet;

import static lv.brain.gradle.xmlbeans.XmlbeansExtension.*;

public class CleanUpSourceFilesTask extends Delete {
    public CleanUpSourceFilesTask() {
        this.setGroup(GROUP);
        this.setDelete(new HashSet<>(Arrays.asList(
                getProject().file(PATH_SRC_MAIN_JAVA_GENERATED),
                getProject().file(PATH_SRC_MAIN_RESOURCES_GENERATED)
        )));
    }
}
