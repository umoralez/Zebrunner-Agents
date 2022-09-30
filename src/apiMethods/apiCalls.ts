import * as dotenv from 'dotenv';
dotenv.config();
import { AuthToken } from '../types/auth/AuthToken';
import axios from 'axios';

//Return AuthToken type with the api response
export function getRefreshToken(authToken: AuthToken): AuthToken {
  axios.post(process.env.BASE_URL + process.env.ENP_TOKEN_GENERATE, {
    "refreshToken": process.env.ZEBRUNNER_API_TOKEN,
  }).then((response) => {
    authToken = response.data;
    console.log(authToken);
  }).catch((error) => {
    console.log(error);
  });
  return authToken;
}