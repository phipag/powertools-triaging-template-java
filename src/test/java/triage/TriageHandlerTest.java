package triage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

class TriageHandlerTest {

    @Test
    void handleRequest_shouldReturnSuccessResponse() {
        // Given
        TriageHandler handler = new TriageHandler();
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
        Context context = new TestContext();

        // When
        APIGatewayProxyResponseEvent response = handler.handleRequest(request, context);

        // Then
        assertEquals(200, response.getStatusCode());
        assertEquals("application/json", response.getHeaders().get("Content-Type"));
        assertTrue(response.getBody().contains("Hello from Powertools Triaging Template!"));
        assertTrue(response.getBody().contains("\"service\": \"triage\""));
        assertTrue(response.getBody().contains("\"version\": \"1.0.0\""));
    }
}
