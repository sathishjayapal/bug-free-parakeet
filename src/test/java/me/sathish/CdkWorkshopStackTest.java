package me.sathish;

import me.sathish.aws.create.snssqs.CdkQueueSnsCreate;
import org.junit.jupiter.api.Disabled;
import software.amazon.awscdk.core.App;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
public class CdkWorkshopStackTest {
    private final static ObjectMapper JSON =
        new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true);

    @Test
    public void testStack() throws IOException {
        App app = new App();
        CdkQueueSnsCreate stack = new CdkQueueSnsCreate(app, "test");
        JsonNode actual = JSON.valueToTree(app.synth().getStackArtifact(stack.getArtifactId()).getTemplate());
        assertThat(actual.toString())
            .contains("AWS::SQS::Queue")
            .contains("AWS::SNS::Topic");
    }
}
