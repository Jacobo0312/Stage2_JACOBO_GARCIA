import DataFilterDTO from './DataFilterDTO';

interface QueryRequestDTO {
  queryName: string;
  username: string;
  description: string;
  dataFilter: DataFilterDTO;
}

export default QueryRequestDTO;
