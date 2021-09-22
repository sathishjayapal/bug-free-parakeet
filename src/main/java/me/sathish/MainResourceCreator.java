package me.sathish;

import lombok.extern.slf4j.Slf4j;
import me.sathish.aws.create.s3.S3BucketCreate;
import me.sathish.aws.create.vpc.VPCCreate;
import software.amazon.awscdk.core.App;

@Slf4j
public final class MainResourceCreator {
    public static void main(final String[] args) throws Exception {
        App app = new App();
//        S3BucketCreate s3Stack = new S3BucketCreate(app, "S3BucketCreate");
//        s3Stack.getNotificationArns().forEach(data -> System.out.println("data " + data));
        VPCCreate vpcCreate = new VPCCreate(app, "VPCCreate");
        vpcCreate.getNotificationArns().forEach(data -> System.out.println("data " + data));
        app.synth();
    }
}


