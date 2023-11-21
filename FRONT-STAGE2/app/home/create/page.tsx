'use client';
import { Country } from '@/interfaces/Country';
import React from 'react';
import { DMA } from '@/interfaces/DMA';
import FilterBar from '@/components/filterBar';
import DataFilterDTO from '@/interfaces/DataFilterDTO';
import { addDays, set } from 'date-fns';
import { DateRange } from 'react-day-picker';
import { formatDateToYYYYMMDD } from '@/utils/formatDate';
import HorizontalBarChart from '@/components/horizontalBarChart';
import { GoogleTrendsResponseDTO } from '@/interfaces/GoogleTrendsResponseDTO';
import Loader from '@/components/loader';

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
  //States
  const [countries, setCountries] = React.useState<Country[]>([]);
  const [regions, setRegions] = React.useState<any[]>([]);
  const [dmaList, setDMAList] = React.useState<DMA[]>([]);
  const [valuesCountries, setValuesCountries] = React.useState([]);
  const [valuesRegions, setValuesRegions] = React.useState([]);
  const [valuesDma, setValuesDma] = React.useState([]);
  const [term, setTerm] = React.useState<string>('');
  const [limit, setLimit] = React.useState<any>();
  const [date, setDate] = React.useState<DateRange | undefined>({
    from: new Date(2022, 0, 20),
    to: addDays(new Date(2022, 0, 20), 20),
  });
  const [data, setData] = React.useState<any>();
  const [loading, setLoading] = React.useState(false);
  React.useEffect(() => {
    getCountries().then((data) => {
      setCountries(data);
    });

    getDMAList().then((data) => {
      setDMAList(data);
    });
  }, []);

  React.useEffect(() => {
    const array = Array.from(valuesCountries);

    if (array.length === 1) {
      setValuesRegions([]);

      getRegions(array[0]).then((data) => {
        setRegions(data);
      });
    } else {
      setRegions([]);
      setValuesRegions([]);
    }
  }, [valuesCountries]);

  const runQuery = async () => {
    const data: DataFilterDTO = {
      term: term,
      countries: Array.from(valuesCountries),
      regions: Array.from(valuesRegions),
      dmaList: Array.from(valuesDma),
    };

    if (date && date.from && date.to) {
      data.startDate = formatDateToYYYYMMDD(date.from);
      data.endDate = formatDateToYYYYMMDD(date.to);
    }

    const limitValue = limit?.target.value;

    const limitParam = limitValue ? `limit=${limitValue}` : '';

    setLoading(true);

    console.log(data);

    const response = await fetch(
      `${process.env.API_URL}google-trends/create?${limitParam}`,
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
      },
    );
    const result = (await response.json()) as GoogleTrendsResponseDTO;
    setData(result);
    setLoading(false);
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
        run={runQuery}
      />
      {data && (
        <div className="grid grid-cols-2 gap-4 p-2">
          {data.termUSAList.length > 0 && (
            <HorizontalBarChart
              titleText="Top 25 Term USA"
              chartData={data.termUSAList}
              loading={loading}
            />
          )}
          {data.termInternationalList.length > 0 && (
            <HorizontalBarChart
              titleText="Top Term 25 International"
              chartData={data.termInternationalList}
              loading={loading}
            />
          )}
          {data.termRisingUSAList.length > 0 && (
            <HorizontalBarChart
              titleText="Top Term 25 Rising USA"
              chartData={data.termRisingUSAList}
              loading={loading}
            />
          )}
          {data.termRisingInternationalList.length > 0 && (
            <HorizontalBarChart
              titleText="Top Term 25 Rising International"
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
    </div>
  );
};

export default Page;
