package lv.brain.gradle.xmlbeans;

import org.gradle.api.Project;

import java.io.File;

/**
 * Extension for configuring XMLBeans.
 * <p>
 * This extension allows you to specify settings for generating Java code from XSD schemas using XMLBeans.
 * </p>
 * <p>
 * <b>Example Usage in build.gradle:</b>
 * <pre>{@code
 * xsd {
 *     // Your XMLBeans specific configurations here
 * }
 * }</pre>
 * </p>
 */
public class XmlbeansExtension {
    public static final String GROUP = "xmlbeans";
    public static final String PATH_SRC_MAIN_JAVA = "src/main/java/";
    public static final String PATH_SRC_MAIN_JAVA_GENERATED = "src/main/java-generated/";
    public static final String PATH_SRC_MAIN_RESOURCES = "src/main/resources/";
    public static final String PATH_SRC_MAIN_RESOURCES_GENERATED = "src/main/resources-generated/";
    public static final String PATH_XSD_SCHEMA = "src/xsd/schema/";
    public static final String PATH_XSD_SCHEMA_CONFIG = "src/xsd/config/";
    private final Project project;
    private String schema = "schema.xsd";
    private String config = "default.xsdconfig";

    public XmlbeansExtension(Project project) {
        this.project = project;
    }

    public String getSchema() {
        return schema;
    }

    /**
     * Sets the name of the XSD schema file that will be used for generating Java code.
     * <p>
     * The schema file should be located in the project folder under <b>/src/xsd/schema/</b>.
     * </p>
     * <p>
     * <b>Default File Name:</b> schema.xsd
     * </p>
     *
     * @param schema The name of the XSD schema file (e.g., "mySchema.xsd").
     */
    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getConfig() {
        return config;
    }


    /**
     * Sets the configuration file for XSD schema generation.
     * <p>
     * The configuration file should be located in the project folder under <b>/src/xsd/config/</b>.
     * </p>
     * <p>
     * <b>Default File Name:</b> default.xsdconfig
     * </p>
     *
     * @param config The name of the XSD schema generation configuration file (e.g., "myConfig.xsdconfig").
     */
    public void setConfig(String config) {
        this.config = config;
    }

    public File getSchemaFileLocation() {
        return project.file(PATH_XSD_SCHEMA + schema);
    }

    public File getSchemaConfigFileLocation() {
        return project.file(PATH_XSD_SCHEMA_CONFIG + config);
    }
}
