package me.sathish.aws.create.vpc;

import me.sathish.aws.common.CSVFileParserCDKInterface;
import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.ec2.Vpc;

public class VPCCreate extends Stack implements CSVFileParserCDKInterface {
    static final String SERVICE_TYPE = "vpc";

    public VPCCreate(final Construct parent, final String id) throws Exception {
        this(parent, id, null);
    }

    public VPCCreate(final Construct parent, final String id, final StackProps props) throws Exception {
        super(parent, id, props);
        String vpcIdentifierInformation = makeCDKIdentifier(SERVICE_TYPE);
        Vpc.Builder vpcBuilder = Vpc.Builder.create(this, "CSVFileParserVPC")
                .cidr("10.0.0.0/16");
        vpcBuilder.enableDnsSupport(Boolean.TRUE);
        vpcBuilder.enableDnsHostnames(Boolean.TRUE);
        vpcBuilder.natGateways(0);
        vpcBuilder.maxAzs(1);
        Vpc vpc = vpcBuilder.build();
        System.out.println("The created VPC is " + vpc.toString());
    }

}
