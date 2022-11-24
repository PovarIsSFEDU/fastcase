package com.holydev.fastcase.utilities.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {
    /**
     * Folder location for storing files
     */
    @Getter
    @Setter
    private String location = "upload-dir";


}
