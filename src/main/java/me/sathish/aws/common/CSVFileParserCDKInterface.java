package me.sathish.aws.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awscdk.core.Tags;
import software.amazon.awscdk.services.s3.Bucket;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Properties;

public interface CSVFileParserCDKInterface {
    Logger LOGGER = LoggerFactory.getLogger("CSVFileParserCDKInterface");
    String fileName = "config.properties";
    ThreadLocal<String> PROJ_ENV = ThreadLocal.withInitial(() -> "project.env");
    String PROJ_NAME = "project.name";
    String PROJ_TECH = "project.technology";
    String PROJECT_FUNCTIONAL = "project.functional";
    ThreadLocal<Properties> properties = ThreadLocal.withInitial(() -> {
        try {
            return loadAndReturnProps();
        } catch (Exception e) {
            return null;
        }
    });

    static String makeCDKIdentifier() throws Exception {
        return new StringBuilder().append(properties.get().getProperty(PROJ_NAME)).append("-").append(properties.get().getProperty(PROJ_ENV.get())).append("-").append(properties.get().get(PROJ_TECH)).append("-").append(properties.get().get(PROJECT_FUNCTIONAL)).toString();
    }

    static Properties loadAndReturnProps() throws Exception {
        Properties prop = null;
        try (InputStream input = CSVFileParserCDKInterface.class.getClassLoader().getResourceAsStream(fileName)) {
            prop = new Properties();
            if (input == null) {
                LOGGER.error("Properties file not loaded");
                throw new Exception("Properties file not loaded");
            }
            prop.load(input);
        } catch (IOException ex) {
            LOGGER.error("Error for props file", ex.fillInStackTrace());
        }
        return prop;
    }

    default void createTagsForCsvBucket(Bucket createdBucket) {
        Tags.of(createdBucket).add("createdate", LocalDateTime.now().toString());
        Tags.of(createdBucket).add("expiredate", LocalDateTime.now().plusDays(60).toString());
    }

    default String makeCDKIdentifier(String SERVICE_TYPE) throws Exception {
        return new StringBuilder().append(CSVFileParserCDKInterface.makeCDKIdentifier()).append("-").append(SERVICE_TYPE).toString();
    }
}
