package triage;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * Simple Lambda context implementation for unit tests
 */
public class TestContext implements Context {
    @Override
    public String getAwsRequestId() {
        return "test-request-id";
    }

    @Override
    public String getLogGroupName() {
        return "test-log-group";
    }

    @Override
    public String getLogStreamName() {
        return "test-log-stream";
    }

    @Override
    public String getFunctionName() {
        return "test-function";
    }

    @Override
    public String getFunctionVersion() {
        return "test-version";
    }

    @Override
    public String getInvokedFunctionArn() {
        return "test-arn";
    }

    @Override
    public com.amazonaws.services.lambda.runtime.CognitoIdentity getIdentity() {
        return null;
    }

    @Override
    public com.amazonaws.services.lambda.runtime.ClientContext getClientContext() {
        return null;
    }

    @Override
    public int getRemainingTimeInMillis() {
        return 1000;
    }

    @Override
    public int getMemoryLimitInMB() {
        return 128;
    }

    @Override
    public com.amazonaws.services.lambda.runtime.LambdaLogger getLogger() {
        return null;
    }
}
