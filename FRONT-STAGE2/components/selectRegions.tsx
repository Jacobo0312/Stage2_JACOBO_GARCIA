import { Avatar, Select, SelectItem } from '@nextui-org/react';
import React from 'react';
import { Country } from '@/interfaces/Country';
import { Region } from '@/interfaces/Region';

type Props = {
  regions: Region[];
  values: string[];
  setValues: ({}: any) => any;
};

const SelectRegions = ({ regions, values, setValues }: Props) => {
  return (
    <Select
      label="Select region"
      selectionMode="multiple"
      className="max-w-xs"
      selectedKeys={values}
      onSelectionChange={setValues}
    >
      {regions &&
        regions.map(({ name, code }) => (
          <SelectItem key={code}>{name}</SelectItem>
        ))}
    </Select>
  );
};

export default SelectRegions;
