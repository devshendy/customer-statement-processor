package task.shendy.customer_statement_validator.enums;

import org.apache.commons.io.FilenameUtils;

public enum FileExtension {
    CSV("csv"),
    XML("xml");

    private final String extension;

    FileExtension(String extension) {
        this.extension = extension;
    }

    public static boolean isFileSupported(String fileName) {
        String extension = FilenameUtils.getExtension(fileName);

        for (FileExtension ext : values()) {
            if (ext.extension.equals(extension)) return true;
        }

        return false;
    }
}
