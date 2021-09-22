package me.sathish.aws.create.eventbridge;

import me.sathish.aws.common.CSVFileParserCDKInterface;
import org.jetbrains.annotations.Nullable;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.events.EventBusAttributes;
import software.amazon.awscdk.services.events.EventPattern;
import software.amazon.awscdk.services.events.Rule;
import software.constructs.Construct;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/*
{
  "source": ["aws.s3"],
  "detail-type": ["AWS API Call via CloudTrail"],
  "detail": {
    "eventSource": ["s3.amazonaws.com"],
    "eventName": ["PutObject"],
    "requestParameters": {
      "bucketName": ["sathishctbucket"]
    }
  }
}
 */
public class EventBridgeCreator extends Stack implements CSVFileParserCDKInterface {
    static final String SERVICE_TYPE = "eventbridge";
    Properties properties;

    public EventBridgeCreator(@Nullable Construct scope, @Nullable String id, @Nullable StackProps props, Properties properties) throws Exception {
        super(scope, id, props);
        Rule.Builder ruleBuilder = Rule.Builder.create(this, "s3_upload_cron");
        this.properties = properties;
        String eventIdentifierName = makeCDKIdentifier();
        ruleBuilder.ruleName(eventIdentifierName);
        Map eventPatternMap = new HashMap();
        eventPatternMap.put("eventSource", "s3.amazonaws.com");
        eventPatternMap.put("eventName", "PutObject");
        EventPattern.Builder eventPattern = EventPattern.builder();
        eventPattern.detail(eventPatternMap);
        EventPattern pattern = eventPattern.build();
        ruleBuilder.eventPattern(pattern);
        Rule createdRule = ruleBuilder.build();
        System.out.println("Created Rule Arn" + createdRule.getRuleArn());
    }

    public String makeCDKIdentifier() throws Exception {
        return new StringBuilder().append(CSVFileParserCDKInterface.makeCDKIdentifier()).append(SERVICE_TYPE).toString();
    }
}
