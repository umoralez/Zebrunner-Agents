import { authToken } from './types/auth/AuthToken';
import {getRefreshToken, getTestRunStart, setTestExecutionStart} from './apiMethods/apiCalls';
import { testRunStart } from './types/testRun/TestRunStart';
import { testExecutionStart } from './types/testRun/TestExecutionStart';

let testRunStart: testRunStart = {
  projectKey: 'ALPHA',
  name: "TypeScript Test ULI",
  startedAt: "2022-10-04T10:12:14.333+01:00",
  status: "IN_PROGRESS",
  framework: "cypress"
}

let testExecutionStart: testExecutionStart = {
  "name": "Test ULI",
  "className": "com.test.MyTests",
  "methodName": "uliTest()",
  "startedAt": "2022-10-04T10:12:15.444+01:00"
}

//Promise.all verlo uwu
async function main() {

  //let refreshToken: authToken = await getRefreshToken();
  //console.log(refreshToken.authToken);

  let preparedTestData : testRunStart = await getTestRunStart(testRunStart)
  console.log(preparedTestData);

  let executionStart : testExecutionStart = await setTestExecutionStart(preparedTestData.id, testExecutionStart);
  console.log(executionStart);
  
}

main();