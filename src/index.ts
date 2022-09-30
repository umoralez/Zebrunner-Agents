import { AuthToken } from './types/auth/AuthToken';
import {getRefreshToken} from './apiMethods/apiCalls';

let authToken: AuthToken;
getRefreshToken(authToken);
console.log(authToken);

