package triage;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import software.amazon.lambda.powertools.logging.Logging;
import software.amazon.lambda.powertools.metrics.FlushMetrics;
import software.amazon.lambda.powertools.metrics.Metrics;
import software.amazon.lambda.powertools.metrics.MetricsFactory;
import software.amazon.lambda.powertools.metrics.model.MetricUnit;
import software.amazon.lambda.powertools.tracing.Tracing;

/**
 * Lambda function handler for triaging Powertools issues.
 * This is a minimal setup using Powertools core utilities: Logging, Tracing,
 * and Metrics.
 */
public class TriageHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final Logger logger = LoggerFactory.getLogger(TriageHandler.class);
    private static final Metrics metrics = MetricsFactory.getMetricsInstance();

    @Logging(logEvent = true)
    @Tracing
    @FlushMetrics(namespace = "TriagingTemplate", service = "triage")
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        logger.info("Processing triage request");

        // Add custom metric
        metrics.addMetric("TriageRequests", 1, MetricUnit.COUNT);

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        String responseBody = """
                {
                    "message": "Hello from Powertools Triaging Template!",
                    "service": "triage",
                    "version": "1.0.0"
                }
                """;

        logger.info("Triage request processed successfully");

        return new APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withHeaders(headers)
                .withBody(responseBody);
    }
}
