interface DataFilterDTO {
  term?: string;
  countries?: string[];
  regions?: string[];
  dmaList?: string[];
  startDate?: string;
  endDate?: string;
  limit?: number;
}

export default DataFilterDTO;
