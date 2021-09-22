package me.sathish.aws.create.s3;

import me.sathish.aws.common.CSVFileParserCDKInterface;
import software.amazon.awscdk.core.*;
import software.amazon.awscdk.services.s3.BlockPublicAccess;
import software.amazon.awscdk.services.s3.Bucket;
import software.amazon.awscdk.services.s3.BucketAccessControl;

import java.time.LocalDateTime;

import static software.amazon.awscdk.services.s3.Bucket.Builder.create;

public class S3BucketCreate extends Stack implements CSVFileParserCDKInterface {
    static final String SERVICE_TYPE = "bucket";

    public S3BucketCreate(final Construct parent, final String id) throws Exception {
        this(parent, id, null);
    }

    public S3BucketCreate(final Construct parent, final String id, final StackProps props) throws Exception {
        super(parent, id, props);
        String s3identifierInformation = makeCDKIdentifier(SERVICE_TYPE);
        Bucket.Builder mainBucketBuilder = create(this, s3identifierInformation).versioned(true).removalPolicy(RemovalPolicy.DESTROY).autoDeleteObjects(true);
        mainBucketBuilder.bucketName(s3identifierInformation);
        mainBucketBuilder.accessControl(BucketAccessControl.PRIVATE);
        mainBucketBuilder.blockPublicAccess(BlockPublicAccess.BLOCK_ALL);
        mainBucketBuilder.enforceSsl(Boolean.TRUE);
        Bucket createdBucket = mainBucketBuilder.build();
        createTagsForCsvBucket
                (createdBucket);
        System.out.println("The created bucket is " + createdBucket.getBucketName());
    }

}
