import * as dotenv from 'dotenv';
dotenv.config();
import axios from 'axios';
import { authToken } from '../types/auth/AuthToken';
import { TestRunStart} from 'src/types/testRun/TestRunStart';

/**
 * Get the refresh authorization token
 * @param  {[authToken]} authToken We give our authorization token to get a new one.
 * @return {[authToken]}      Return the object with the new authorization token.
 */
export function getRefreshToken(authToken: authToken): authToken {
  axios.post(process.env.BASE_URL + process.env.ENP_TOKEN_GENERATE, {
    "refreshToken": process.env.ZEBRUNNER_API_TOKEN,
  }).then((response) => {
    authToken = response.data;
    console.log("Refresh Token");
    console.log(authToken.authToken);
  }).catch((error) => {
    console.log(error);
  });
  return authToken;
}

/**
 * Get the Test Run ID and the information about the test run.
 * @param  {[TestRunStart]} testRunStart We give the testRunStart object to get the test run ID.
 * @return {[TestRunStart]}      Return the object with the new test run ID.
 */
export function getTestRunStart( testRunStart: TestRunStart): TestRunStart{
  axios.post(process.env.BASE_URL + process.env.ENP_TEST_RUN_START+testRunStart.projectKey, {
    "name": testRunStart.name,
    "startedAt": testRunStart.startedAt,
    "status": testRunStart.status,
    "framework": testRunStart.framework
  }, {
    headers: {
      'Authorization': 'Bearer ' + process.env.ZEBRUNNER_REFRESH_TOKEN
    }
  }).then((response) => {
    testRunStart = response.data;
    console.log(response.data);
    console.log("Data de TestRunStart");
    console.log(testRunStart);
    
  }).catch((error) => {
    console.log(error);
  });
  return testRunStart;
}