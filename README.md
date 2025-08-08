# Powertools for AWS Lambda (Java) - Triaging Template

This repository contains a sample Java project to deploy an AWS Lambda function using AWS SAM. The main goal of this project is to provide a testing ground for triaging issues and bug reports related to Powertools for AWS Lambda (Java).

## Prerequisites

This project requires:

- **Java 21**
- **Maven 3.9+**

### Installation using SDKMAN!

[SDKMAN!](https://sdkman.io/) is useful to switch Java versions quickly to reproduce issues on different Java versions.

```bash
# Install SDKMAN! (if not already installed)
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"

# Install Java 21 (Amazon Corretto)
sdk install java 21.0.8-amzn

# Install Maven
sdk install maven

# Verify installations
java -version
mvn -version
```

## Triaging workflow

1. Fork the repository using the "Use this template" button (green, top right).
2. Clone your forked repository to your local machine.
3. Run `mvn clean compile` to compile the project and install dependencies.
4. Make changes to the Lambda function code in `src/main/java/triage/TriageHandler.java`.
5. Deploy the stack using `sam build && sam deploy --guided` (first time) or `sam deploy` (subsequent deployments).
6. Test the Lambda function by invoking it with a sample event payload from the issue report or by using the provided event in the `events/` directory.
7. Observe the logs in CloudWatch to verify the function's behavior.
8. If you are able to reproduce the issue, update this `README.md` file with the details of the issue, including:
   - Steps to reproduce
   - Expected behavior
   - Actual behavior
   - Any relevant logs or error messages
9. Push your changes to your forked repository and report your findings in the original issue report on the Powertools for AWS Lambda GitHub repository.

## Project Structure

```
powertools-triaging-template-java/
├── pom.xml                           # Maven project configuration
├── template.yaml                     # SAM template defining infrastructure
├── src/
│   ├── main/java/triage/
│   │   └── TriageHandler.java        # Main Lambda function handler
│   └── test/java/triage/
│       └── TriageHandlerTest.java    # Unit tests for the handler
└── events/
    └── event.json                    # Sample API Gateway event payload
```

## Architecture

This application uses [SAM](https://docs.aws.amazon.com/serverless-application-model/) as IaC tool and creates:

1. A Lambda function running Java 21 called `TriageFunction`
2. An API Gateway endpoint at `/triage`
3. CloudWatch Log Group for the Lambda function
4. CloudFormation outputs for key resources

The Lambda function code is in `src/main/java/triage/TriageHandler.java`.

## Commands

### Compilation Commands

```bash
# Compile the project only (skip tests) - useful for reproducing issues quickly
mvn clean compile

# Compile both main code and test code (but don't run tests)
mvn clean test-compile

# Compile and run unit tests - useful for triaging unit test related issues
mvn clean test

# Clean and compile everything including tests
mvn clean compile test-compile
```

### Build and Deployment Commands

```bash
# Build the SAM application
sam build

# Build the SAM application without running unit tests
MAVEN_OPTS="-DskipTests=true" sam build

# Deploy the stack (first time - guided)
sam deploy --guided

# Deploy the stack (subsequent deployments)
sam deploy
```

### Local Testing Commands

```bash
# Invoke function locally with sample event
sam local invoke TriageFunction -e events/event.json

# Start API Gateway locally
sam local start-api

# Test the local API
curl http://localhost:3000/triage
```

### Testing Commands

```bash
# Run all unit tests
mvn test

# Run tests with verbose output
mvn test -X

# Run specific test class
mvn test -Dtest=TriageHandlerTest

# Skip tests during build (useful for quick issue reproduction)
mvn clean compile

# Run tests and generate reports
mvn clean test surefire-report:report
```

### Monitoring Commands

```bash
# Tail CloudWatch logs
sam logs -n TriageFunction --stack-name <your-stack-name> --tail

# View CloudWatch logs
sam logs -n TriageFunction --stack-name <your-stack-name>
```

## Powertools Configuration

The project is configured with the following Powertools environment variables in `template.yaml`:

- `POWERTOOLS_LOG_LEVEL`: Set to `INFO`
- `POWERTOOLS_LOGGER_SAMPLE_RATE`: Set to `0.1` (10% sampling)
- `POWERTOOLS_LOGGER_LOG_EVENT`: Set to `true` (logs incoming events)
- `POWERTOOLS_METRICS_NAMESPACE`: Set to `TriagingTemplate`
- `POWERTOOLS_SERVICE_NAME`: Set to `triage`

## Triaging Scenarios

### For Runtime Issues

When triaging runtime or deployment issues, you may want to skip tests for faster iteration:

```bash
mvn clean compile
sam build
sam deploy
```

### For Unit Test Issues

When customers report issues with Powertools in unit tests, run the full test suite:

```bash
mvn clean test
# Check test reports in target/surefire-reports/
```

### For Build Issues

When investigating compilation or dependency issues:

```bash
mvn clean compile -X  # Verbose output for debugging
mvn dependency:tree   # Check dependency conflicts
```
