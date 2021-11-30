package me.sathish.aws.create.vpc;

import me.sathish.aws.common.CSVFileParserCDKInterface;
import org.jetbrains.annotations.NotNull;
import software.amazon.awscdk.core.CfnTag;
import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.ec2.CfnInternetGateway;
import software.amazon.awscdk.services.ec2.CfnSubnet;
import software.amazon.awscdk.services.ec2.CfnVPCGatewayAttachment;
import software.amazon.awscdk.services.ec2.Vpc;

import java.util.ArrayList;
import java.util.List;

public class VPCCreate extends Stack implements CSVFileParserCDKInterface {
    static final String SERVICE_TYPE = "vpc";
    static final String[] PUBLIC_CIDR_BLOCK = {"10.0.1.0/24", "10.0.3.0/24"};
    static final String[] PRIVATE_CIDR_BLOCK = {"10.0.2.0/24", "10.0.4.0/24"};

    public VPCCreate(final Construct parent, final String id) throws Exception {
        this(parent, id, null);
    }

    public VPCCreate(final Construct parent, final String id, final StackProps props) throws Exception {
        super(parent, id, props);
        String vpcIdentifierInformation = makeCDKIdentifier(SERVICE_TYPE);
        CfnInternetGateway gateway = buildIGW();
        Vpc vpc = buildVPC();
//        attachVPCGateway(gateway, vpc);
        buildPublicSubnet(vpc);
//        buildPrivateSubnet(vpc);
        System.out.println("The created VPC is " + vpc.toString());
    }

    private void buildPublicSubnet(Vpc vpc) {
        CfnSubnet.Builder cnfSubnetBuilder = CfnSubnet.Builder.create(this, "CSVFileParserPub1")
                .cidrBlock("10.0.2.0/24").vpcId(vpc.getVpcId()).tags(buildTag(1, "PubSubnet"));
        cnfSubnetBuilder.build();
        CfnSubnet.Builder cnfSubnetBuilder2 = CfnSubnet.Builder.create(this, "CSVFileParserPub2")
                .cidrBlock("10.0.2.0/24").vpcId(vpc.getVpcId()).tags(buildTag(2, "PubSubnet"));
    }


    private List<CfnTag> buildTag(int i, String privateSubnet) {
        List<CfnTag> tagList = new ArrayList<>();
        CfnTag.Builder cfnTagBuilder = CfnTag.builder();
        cfnTagBuilder.key("Name");
        cfnTagBuilder.value(privateSubnet + i);
        tagList.add(cfnTagBuilder.build());
        return tagList;
    }

    @NotNull
    private CfnInternetGateway buildIGW() {
        CfnInternetGateway.Builder igwBuilder = CfnInternetGateway.Builder.create(this, "CSVFileParserIgw");
        CfnInternetGateway gateway = igwBuilder.build();
        return gateway;
    }

    @NotNull
    private Vpc buildVPC() {
        Vpc.Builder vpcBuilder = Vpc.Builder.create(this, "CSVFileParserVPC")
                .cidr("10.0.0.0/16");
        vpcBuilder.enableDnsSupport(Boolean.TRUE);
        vpcBuilder.enableDnsHostnames(Boolean.TRUE);
        vpcBuilder.natGateways(0);
//        vpcBuilder.maxAzs(2);
        Vpc vpc = vpcBuilder.build();
        return vpc;
    }

    private void attachVPCGateway(CfnInternetGateway gateway, Vpc vpc) {
        CfnVPCGatewayAttachment.Builder gateWayBuilder = CfnVPCGatewayAttachment.Builder.create(this, "CSVFileParserGWYAttachment");
        gateWayBuilder.vpcId(vpc.getVpcId());
        gateWayBuilder.internetGatewayId(gateway.getRef());
        gateWayBuilder.build();
    }

}
