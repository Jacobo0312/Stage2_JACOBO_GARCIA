import React from 'react';
import { CircularProgress } from '@nextui-org/react';

const Loader = () => {
  return (
    <div className="grid h-screen place-items-center">
      <CircularProgress size="lg" aria-label="Loading..." />
    </div>
  );
};

export default Loader;
