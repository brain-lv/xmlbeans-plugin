package lv.brain.gradle.xmlbeans;

public class XmlbeansExtension {
    private String schema = "schema.xsd";
    private String config = "default.xsdconfig";

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }
}
