import DataFilterDTO from './DataFilterDTO';

interface QueryResponseDTO {
  id: string;
  queryName: string;
  username: string;
  description: string;
  dataFilter: DataFilterDTO;
  comments: Comment[];
}

interface Comment {
  id: string;
  comment: string;
  username: string;
  query: string;
}

export default QueryResponseDTO;
