import { Input } from '@nextui-org/react';
import { SearchIcon } from '@/components/icons';

type Props = {
  term: string;
  setTerm: (value: string) => void;
};
const SearchInput = ({ term, setTerm }: Props) => {
  return (
    <Input
      aria-label="Search"
      classNames={{
        inputWrapper: 'bg-default-100',
        input: 'text-sm',
      }}
      labelPlacement="outside"
      placeholder="Search..."
      startContent={
        <SearchIcon className="text-base text-default-400 pointer-events-none flex-shrink-0" />
      }
      type="search"
      value={term}
      onChange={(e) => setTerm(e.target.value)}
    />
  );
};

export default SearchInput;
