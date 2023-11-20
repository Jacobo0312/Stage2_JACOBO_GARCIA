'use client';
import SearchInput from '@/components/searchInput';
import SelectCountries from '@/components/selectCountries';
import { Country } from '@/interfaces/Country';
import { Button, Select, SelectItem } from '@nextui-org/react';
import React from 'react';
import { PlayIcon } from '@/components/icons';
import SelectRegions from '@/components/selectRegions';
import { DMA } from '@/interfaces/DMA';
import DatePickerWithRange from '@/components/dateRangePicker';

async function getCountries() {
  const response = await fetch(
    'http://localhost:8000/api/google-trends/countries',
  );
  const data: Country[] = await response.json();
  console.log('data', data);
  return data;
}

async function getRegions(countryCode: string) {
  const response = await fetch(
    `http://localhost:8000/api/google-trends/regions/${countryCode}`,
  );
  const data = await response.json();
  return data;
}

async function getDMAList(): Promise<DMA[]> {
  const response = await fetch('http://localhost:8000/api/google-trends/dma');
  const data = await response.json();
  return data;
}

const limits = [
  {
    label: '10',
    value: 10,
  },
  {
    label: '15',
    value: 15,
  },
  {
    label: '20',
    value: 20,
  },
  {
    label: '25',
    value: 25,
  },
  {
    label: '30',
    value: 30,
  },
];

const Page = () => {
  const [countries, setCountries] = React.useState<Country[]>([]);
  const [regions, setRegions] = React.useState<any[]>([]);
  const [dmaList, setDMAList] = React.useState<DMA[]>([]);
  const [valuesCountries, setValuesCountries] = React.useState([]);
  const [valuesRegions, setValuesRegions] = React.useState([]);
  const [valuesDma, setValuesDma] = React.useState([]);

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
    }
  }, [valuesCountries]);

  const runQuery = async () => {
    console.log(Array.from(valuesCountries));
  };

  return (
    <div className="flex w-full space-x-5 justify-between items-center py-2">
      <SearchInput />
      <SelectCountries
        countries={countries}
        setValues={setValuesCountries}
        values={valuesCountries}
      />
      {/* Select regions */}
      {regions.length > 0 && (
        <SelectRegions
          regions={regions}
          values={valuesRegions}
          setValues={setValuesRegions}
        />
      )}
      {/* Limit */}
      <Select
        items={limits}
        label="Limit"
        placeholder="Select limit"
        className="max-w-xs"
      >
        {(limit) => <SelectItem key={limit.value}>{limit.label}</SelectItem>}
      </Select>
      <Select
        label="DMA'S"
        placeholder='Select DMA"s'
        selectionMode="multiple"
        selectedKeys={valuesDma}
        onSelectionChange={() => setValuesDma}
        className="max-w-xs"
      >
        {dmaList &&
          dmaList.map(({ name, id }) => (
            <SelectItem key={id}>{name}</SelectItem>
          ))}
      </Select>
      {/* Calendar */}
      <DatePickerWithRange />
      {/* Run button */}
      <Button
        color="success"
        endContent={<PlayIcon />}
        className="text-white"
        onClick={runQuery}
      >
        <b>Run</b>
      </Button>
    </div>
  );
};

export default Page;
