'use client';
import { Country } from '@/interfaces/Country';
import React from 'react';
import { DMA } from '@/interfaces/DMA';
import FilterBar from '@/components/filterBar';
import DataFilterDTO from '@/interfaces/DataFilterDTO';
import { addDays, set } from 'date-fns';
import { DateRange } from 'react-day-picker';
import { formatDateToYYYYMMDD, parseDateString } from '@/utils/formatDate';
import HorizontalBarChart from '@/components/horizontalBarChart';
import Loader from '@/components/loader';
import useQuery from '@/hooks/useQuery';
import { useDisclosure } from '@nextui-org/react';
import ModalSaveQuery from '@/components/modalSaveQuery';
import QueryRequestDTO from '@/interfaces/QueryRequestDTO';
import { useSearchParams } from 'next/navigation';

async function getCountries() {
  const response = await fetch(`${process.env.API_URL}google-trends/countries`);
  const data: Country[] = await response.json();
  return data;
}

async function getRegions(countryCode: string) {
  const response = await fetch(
    `${process.env.API_URL}google-trends/regions/${countryCode}`,
  );
  const data = await response.json();
  return data;
}

async function getDMAList(): Promise<DMA[]> {
  const response = await fetch(`${process.env.API_URL}google-trends/dma`);
  const data = await response.json();
  return data;
}

const Page = () => {
  const searchParams = useSearchParams();
  //States
  const [countries, setCountries] = React.useState<Country[]>([]);
  const [regions, setRegions] = React.useState<any[]>([]);
  const [dmaList, setDMAList] = React.useState<DMA[]>([]);
  const [valuesCountries, setValuesCountries] = React.useState([]);
  const [valuesRegions, setValuesRegions] = React.useState([]);
  const [valuesDma, setValuesDma] = React.useState([]);
  const [term, setTerm] = React.useState<string>('');
  const [limit, setLimit] = React.useState<string>(
    searchParams.get('limit') || '',
  );
  const [date, setDate] = React.useState<DateRange | undefined>({
    from: new Date(2023, 0, 20),
    to: addDays(new Date(2023, 0, 20), 20),
  });
  const [query, setQuery] = React.useState<QueryRequestDTO>();

  const { isOpen, onOpen, onOpenChange } = useDisclosure();

  //Hooks
  const { data, loading, runQuery } = useQuery();

  React.useEffect(() => {
    getCountries().then((data) => {
      setCountries(data);
      if (searchParams.has('countries')) {
        const countries = searchParams.get('countries')?.split(',') || [];
        if (countries.length > 0) {
          setValuesCountries(countries as never[]);
        }
      }
    });

    getDMAList().then((data) => {
      setDMAList(data);
      if (searchParams.has('dmaList')) {
        const dmaList = searchParams.get('dmaList')?.split(',') || [];
        if (dmaList?.length > 0) {
          setValuesDma(dmaList as never[]);
        }
      }
    });

    //Set date range by query params
    if (searchParams.has('startDate') && searchParams.has('endDate')) {
      const startDate = searchParams.get('startDate') || '';
      const endDate = searchParams.get('endDate') || '';
      const dateRange: DateRange = {
        from: parseDateString(startDate),
        to: parseDateString(endDate),
      };
      setDate(dateRange);
    }
  }, [searchParams]);

  React.useEffect(() => {
    const array = Array.from(valuesCountries);

    if (array.length === 1) {
      setValuesRegions([]);

      getRegions(array[0]).then((data) => {
        setRegions(data);
        if (searchParams.has('regions')) {
          const regions = searchParams.get('regions')?.split(',') || [];
          if (regions.length > 0) {
            console.log(regions);
            setValuesRegions(regions as never[]);
          }
        }
      });
    } else {
      setRegions([]);
      setValuesRegions([]);
    }
  }, [valuesCountries, searchParams]);

  const handleRunQuery = async () => {
    const dataFilterDTO: DataFilterDTO = {
      term: term,
      countries: Array.from(valuesCountries),
      regions: Array.from(valuesRegions),
      dmaList: Array.from(valuesDma),
      limitData: parseInt(limit),
    };

    if (date && date.from && date.to) {
      dataFilterDTO.startDate = formatDateToYYYYMMDD(date.from);
      dataFilterDTO.endDate = formatDateToYYYYMMDD(date.to);
    }
    runQuery(dataFilterDTO);
  };

  const handleSaveQuery = async () => {
    const dataFilterDTO: DataFilterDTO = {
      term: term,
      countries: Array.from(valuesCountries),
      regions: Array.from(valuesRegions),
      dmaList: Array.from(valuesDma),
      limitData: parseInt(limit),
    };

    if (date && date.from && date.to) {
      dataFilterDTO.startDate = formatDateToYYYYMMDD(date.from);
      dataFilterDTO.endDate = formatDateToYYYYMMDD(date.to);
    }

    const queryToSave: QueryRequestDTO = {
      dataFilter: dataFilterDTO,
      username: localStorage.getItem('username') || '',
      description: '',
      queryName: '',
    };

    setQuery(queryToSave);

    onOpen();
  };

  return (
    <div className="flex-col w-full ">
      <FilterBar
        term={term}
        setTerm={setTerm}
        countries={countries}
        setValuesCountries={setValuesCountries}
        valuesCountries={valuesCountries}
        regions={regions}
        setValuesRegions={setValuesRegions}
        valuesRegions={valuesRegions}
        dmaList={dmaList}
        setValuesDma={setValuesDma}
        valuesDma={valuesDma}
        date={date}
        setDate={setDate}
        limit={limit}
        setLimit={setLimit}
        run={handleRunQuery}
        save={handleSaveQuery}
      />
      {data && (
        <div className="grid grid-cols-2 gap-4 p-2">
          {data.termUSAList.length > 0 && (
            <HorizontalBarChart
              titleText="Top Term USA"
              chartData={data.termUSAList}
              loading={loading}
            />
          )}
          {data.termInternationalList.length > 0 && (
            <HorizontalBarChart
              titleText="Top Term International"
              chartData={data.termInternationalList}
              loading={loading}
            />
          )}
          {data.termRisingUSAList.length > 0 && (
            <HorizontalBarChart
              titleText="Top Term Rising USA"
              chartData={data.termRisingUSAList}
              loading={loading}
            />
          )}
          {data.termRisingInternationalList.length > 0 && (
            <HorizontalBarChart
              titleText="Top Term Rising International"
              chartData={data.termRisingInternationalList}
              loading={loading}
            />
          )}
        </div>
      )}
      {loading && (
        <div className="flex justify-center">
          <Loader />
        </div>
      )}
      {isOpen && query && (
        <ModalSaveQuery
          isOpen={isOpen}
          onOpenChange={onOpenChange}
          onOpen={onOpen}
          query={query}
        />
      )}
    </div>
  );
};

export default Page;
