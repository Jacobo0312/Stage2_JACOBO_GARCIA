import React from 'react';
import SearchInput from './searchInput';
import SelectCountries from './selectCountries';
import SelectRegions from './selectRegions';
import { Button, Select, SelectItem } from '@nextui-org/react';
import DatePickerWithRange from './dateRangePicker';
import { PlayIcon } from './icons';
import { DateRange } from 'react-day-picker';
import { setDate } from 'date-fns';

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

type Props = {
  term: string;
  setTerm: (value: string) => void;
  countries: any[];
  setValuesCountries: (values: any) => void;
  valuesCountries: any;
  regions: any[];
  setValuesRegions: (values: any) => void;
  valuesRegions: any;
  dmaList: any[];
  setValuesDma: (values: any) => void;
  valuesDma: any;
  date: DateRange | undefined;
  setDate: (date: DateRange | undefined) => void;
  limit: string;
  setLimit: (value: any) => void;
  run: () => void;
};

const FilterBar = ({
  term,
  setTerm,
  countries,
  setValuesCountries,
  valuesCountries,
  regions,
  setValuesRegions,
  valuesRegions,
  dmaList,
  setValuesDma,
  valuesDma,
  date,
  setDate,
  limit,
  setLimit,
  run,
}: Props) => {
  return (
    <div className="flex w-full space-x-5 justify-between items-center py-2">
      {' '}
      <SearchInput term={term} setTerm={setTerm} />
      {Array.from(valuesDma).length === 0 && (
        <SelectCountries
          countries={countries}
          setValues={setValuesCountries}
          values={valuesCountries}
        />
      )}
      {/* Select regions */}
      {regions.length > 0 && Array.from(valuesCountries).length > 0 && (
        <SelectRegions
          regions={regions}
          values={valuesRegions}
          setValues={setValuesRegions}
        />
      )}
      {Array.from(valuesCountries).length === 0 && (
        <Select
          label="DMA'S"
          placeholder='Select DMA"s'
          selectionMode="multiple"
          selectedKeys={valuesDma}
          onSelectionChange={setValuesDma}
          className="max-w-xs"
        >
          {dmaList &&
            dmaList.map(({ name, id }) => (
              <SelectItem key={id}>{name}</SelectItem>
            ))}
        </Select>
      )}
      {/* Limit */}
      <Select
        items={limits}
        label="Limit"
        placeholder="Select limit"
        value={limit}
        onChange={setLimit}
        // className="max-w-10"
      >
        {(limit) => <SelectItem key={limit.value}>{limit.label}</SelectItem>}
      </Select>
      {/* Calendar */}
      <DatePickerWithRange date={date} setDate={setDate} />
      {/* Run button */}
      <Button
        color="success"
        endContent={<PlayIcon />}
        className="text-white"
        onClick={run}
      >
        <b>Run</b>
      </Button>
    </div>
  );
};

export default FilterBar;
