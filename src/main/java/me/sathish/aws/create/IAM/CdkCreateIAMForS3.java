package me.sathish.aws.create.IAM;

import me.sathish.aws.common.CSVFileParserCDKInterface;
import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;

public class CdkCreateIAMForS3 extends Stack implements CSVFileParserCDKInterface {
    String CDKTesterProjectString= "csvfileparser";
    public CdkCreateIAMForS3(final Construct parent, final String id) {

    }
}
