export type TestRunStart = {
  id?: number;
  projectKey: string;
  uuid?: string;
  name: string;
  startedAt: string;
  status: string;
  framework: string;
  config?: string;
  milestone?: {
    id: number;
    name: string;
  }
  notifications?: {
    targets?: {
      type: string;
      value: string;
    };
    notifyOnEachFailure?: boolean;
  }
}