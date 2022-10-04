import { authToken } from './types/auth/AuthToken';
import {getRefreshToken, getTestRunStart} from './apiMethods/apiCalls';
import { TestRunStart } from './types/testRun/TestRunStart';

let authToken: authToken;
//getRefreshToken(authToken);
//console.log(authToken);
let testRunStart: TestRunStart = {
  projectKey: 'ALPHA',
  name: "TestNG run api",
  startedAt: "2021-06-06T10:12:14.333+01:00",
  status: "IN_PROGRESS",
  framework: "testng"
};
getTestRunStart(testRunStart);