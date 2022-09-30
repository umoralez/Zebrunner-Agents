export type AuthToken = {
  userID: number;
  permissionsSuperset:  string[];
  projectAssignments:  projectAssignment[];
  authTokenType:  string;
  authToken: string;
  authTokenExpirationInSecs: number;
  refreshToken: string;
  tenantName: string;
}

type projectAssignment = {
  projectId: number;
  role: string;
}