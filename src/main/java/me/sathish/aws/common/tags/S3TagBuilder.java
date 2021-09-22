package me.sathish.aws.common.tags;

import lombok.Data;

import javax.annotation.sql.DataSourceDefinition;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Data
public class S3TagBuilder {
//    {{teamName}} - {{environment}} - {{scope}} - bucket
    private List<String> S3TagKeys= Arrays.asList("teamName","environment","scope");
    private List<String> S3TagValues;
    public List<String> buildS3TagValues(){
        Properties values= new Properties();
        return getS3TagValues();
    }
}
