import { Avatar, Select, SelectItem } from '@nextui-org/react';
import React from 'react';
import { Country } from '@/interfaces/Country';

type Props = {
  countries: Country[];
  values: string[];
  setValues: ({}: any) => any;
};

const SelectCountries = ({ countries, values, setValues }: Props) => {
  return (
    <Select
      label="Countries"
      placeholder="Select country"
      selectionMode="multiple"
      selectedKeys={values}
      onSelectionChange={setValues}
      className="max-w-xs"
    >
      {countries &&
        countries.map(({ name, code }) => (
          <SelectItem
            key={code}
            startContent={
              <Avatar
                alt={name}
                className="w-6 h-6"
                src={`https://flagcdn.com/${code.toLowerCase()}.svg`}
              />
            }
          >
            {name}
          </SelectItem>
        ))}
    </Select>
  );
};

export default SelectCountries;
