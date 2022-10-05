import * as dotenv from 'dotenv';
dotenv.config();
import axios from 'axios';
import { authToken } from '../types/auth/AuthToken';
import { testRunStart} from 'src/types/testRun/TestRunStart';
import { testExecutionStart } from 'src/types/testRun/TestExecutionStart';

/**
 * Get the refresh authorization token
 * @param  {[authToken]} authToken We give our authorization token to get a new one.
 * @return {[authToken]}      Return the object with the new authorization token.
 */
export const getRefreshToken = async () : Promise<authToken> => {
  const {data} = await axios.post<authToken>(process.env.BASE_URL + process.env.ENP_TOKEN_GENERATE, {
    "refreshToken": process.env.ZEBRUNNER_API_TOKEN,
  })
  return data;
}

/**
 * Get the Test Run ID and the information about the test run.
 * Act like a suite container for the test executions.
 * @param  {[testRunStart]} testRunStart We give the testRunStart object to get the test run ID.
 * @return {[testRunStart]}      Return the object with the new test run ID.
 */
export const getTestRunStart = async (testRunStart: testRunStart) : Promise<testRunStart> => {
  const { data } = await axios.post<testRunStart>(process.env.BASE_URL + process.env.ENP_TEST_RUN_START+testRunStart.projectKey, {
    ...testRunStart
  }, {
    headers: {
      'Authorization': 'Bearer ' + process.env.ZEBRUNNER_REFRESH_TOKEN
    }
  });
  return data;
}

/**
 * Get the Test Run ID and the information about the test run.
 * @param  {[testRunStart]} testRunStart We give the testRunStart object to get the test run ID.
 * @return {[testRunStart]}      Return the object with the new test run ID.
 */
export const setTestExecutionStart = async (testRunId: number, testExecutionStart: testExecutionStart) : Promise<testExecutionStart> => {
  let preparedURL : string = process.env.ENP_TEST_EXECUTION_START.replace("testRunId", testRunId.toString());
  
  const {data} = await axios.post<testExecutionStart>(process.env.BASE_URL + preparedURL, {
    ...testExecutionStart
  }, {
    headers: {
      'Authorization': 'Bearer ' + process.env.ZEBRUNNER_REFRESH_TOKEN
    }
  });
  return data;
}