package config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ReportingConfiguration {

	private Boolean reportingEnabled;
	private String projectKey;
	private ServerConfiguration server;
	private RunConfiguration run;

	public boolean isReportingEnabled() {
		return reportingEnabled != null && reportingEnabled;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@ToString
	public static class ServerConfiguration {

		private String hostname;
		private String accessToken;

	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@ToString
	public static class RunConfiguration {

		private Boolean retryKnownIssues;
		private Boolean treatSkipsAsFailures;
		private TestCaseStatus testCaseStatus = new TestCaseStatus();

		@Getter
		@Setter
		@NoArgsConstructor
		@AllArgsConstructor
		@ToString
		public static class TestCaseStatus {

			private String onPass;
			private String onFail;
			private String onSkip;

		}

	}

}
