export type testExecutionStart = {
  id?: string;
  name: string;
  className: string;
  methodName: string;
  argumentsIndex?: number;
  startedAt: string;
  maintainer?: string;
  labels?: Label[];
}

type Label = {
  key: string;
  value: string;
}