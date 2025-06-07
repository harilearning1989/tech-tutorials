import {Topic} from './topic';

export interface Technology {
  id: string;
  name: string;
  topics: Topic[];
}
