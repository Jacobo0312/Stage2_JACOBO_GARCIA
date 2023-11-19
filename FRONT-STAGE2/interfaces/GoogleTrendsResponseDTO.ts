export interface GoogleTrendsResponseDTO {
  termInternationalList: Term[];
  termRisingInternationalList: Term[];
  termUSAList: Term[];
  termRisingUSAList: Term[];
}

interface Term {
  term: string;
  score: number;
}
