package config.providers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import config.IConfigurationProvider;
import config.ReportingConfiguration;

public class YamlConfigurationProvider implements IConfigurationProvider {

	private final static String ENABLED_PROPERTY = "reporting.enabled";
	private final static String PROJECT_KEY_PROPERTY = "reporting.project-key";

	private final static String HOSTNAME_PROPERTY = "reporting.server.hostname";
	private final static String ACCESS_TOKEN_PROPERTY = "reporting.server.access-token";

	private final static String RUN_RETRY_KNOWN_ISSUES_PROPERTY = "reporting.run.retry-known-issues";
	private final static String RUN_TREAT_SKIPS_AS_FAILURES_PROPERTY = "reporting.run.treat-skips-as-failures";
	private final static String RUN_TEST_CASE_STATUS_ON_PASS_PROPERTY = "reporting.run.test-case-status.on-pass";
	private final static String RUN_TEST_CASE_STATUS_ON_FAIL_PROPERTY = "reporting.run.test-case-status.on-fail";
	private final static String RUN_TEST_CASE_STATUS_ON_SKIP_PROPERTY = "reporting.run.test-case-status.on-skip";

	private static final String[] DEFAULT_FILE_NAMES = { "agent.yaml", "agent.yml" };
	private static final Yaml YAML_MAPPER = new Yaml();

	@Override
	public ReportingConfiguration getConfiguration() {
		try {
			Map<String, Object> yamlProperties = loadYaml();

			String enabled = getProperty(yamlProperties, ENABLED_PROPERTY);

			String projectKey = getProperty(yamlProperties, PROJECT_KEY_PROPERTY);
			String hostname = getProperty(yamlProperties, HOSTNAME_PROPERTY);
			String accessToken = getProperty(yamlProperties, ACCESS_TOKEN_PROPERTY);

			Boolean runRetryKnownIssues = Boolean.valueOf(getProperty(yamlProperties, RUN_RETRY_KNOWN_ISSUES_PROPERTY));
			Boolean treatSkipsAsFailures = Boolean
					.valueOf(getProperty(yamlProperties, RUN_TREAT_SKIPS_AS_FAILURES_PROPERTY));
			String testCaseStatusOnPass = getProperty(yamlProperties, RUN_TEST_CASE_STATUS_ON_PASS_PROPERTY);
			String testCaseStatusOnFail = getProperty(yamlProperties, RUN_TEST_CASE_STATUS_ON_FAIL_PROPERTY);
			String testCaseStatusOnSkip = getProperty(yamlProperties, RUN_TEST_CASE_STATUS_ON_SKIP_PROPERTY);

			return ReportingConfiguration.builder().reportingEnabled(Boolean.valueOf(enabled))
					.projectKey(projectKey)
                    .server(new ReportingConfiguration.ServerConfiguration(
                            hostname, accessToken
                    ))
                    .run(new ReportingConfiguration.RunConfiguration(
                            runRetryKnownIssues,
                            treatSkipsAsFailures,
                            new ReportingConfiguration.RunConfiguration.TestCaseStatus(
                                    testCaseStatusOnPass, testCaseStatusOnFail, testCaseStatusOnSkip
                            )
                    ))
					.build();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	private static Map<String, Object> loadYaml() throws IOException {
		for (String filename : DEFAULT_FILE_NAMES) {
			try (InputStream resource = YamlConfigurationProvider.class.getClassLoader()
					.getResourceAsStream(filename)) {
				if (resource != null) {
					return YAML_MAPPER.load(resource);
				}
			} catch (Exception e) {
				throw new IOException("Unable to load agent configuration from YAML file");
			}
		}
		return Collections.emptyMap();
	}

	private static String getProperty(Map<String, Object> yamlProperties, String key) {
		String result = null;

		String[] keySlices = key.split("\\.");

		Map<String, Object> slice = new HashMap<>(yamlProperties);
		for (int i = 0; i < keySlices.length; i++) {
			String keySlice = keySlices[i];
			Object sliceValue = slice.get(keySlice);
			if (sliceValue != null) {
				if (sliceValue instanceof Map) {
					slice = (Map<String, Object>) sliceValue;
				} else {
					if (i == keySlices.length - 1) {
						result = sliceValue.toString();
					}
					break;
				}
			} else {
				break;
			}
		}
		return result;
	}

}
