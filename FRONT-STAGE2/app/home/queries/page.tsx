'use client';
import React from 'react';
import QueriesList from '@/components/queriesList';
import QueryResponseDTO from '@/interfaces/QueryResponseDTO';
import Loader from '@/components/loader';

async function getQueries() {
  const response = await fetch(`${process.env.API_URL}queries/all`);
  const data = (await response.json()) as QueryResponseDTO[];
  return data;
}

const Page = () => {
  const [queries, setQueries] = React.useState<QueryResponseDTO[]>([]);
  const [loading, setLoading] = React.useState<boolean>(false);

  React.useEffect(() => {
    getQueries().then((data) => {
      setLoading(true);
      setQueries(data);
      console.log(data);
      setLoading(false);
    });
  }, []);

  const mutate = () => {
    getQueries().then((data) => {
      setLoading(true);
      setQueries(data);
      setLoading(false);
    });
  };

  return (
    <div className="flex items-center justify-center w-[700px]">
      {queries.length > 0 && !loading && (
        <QueriesList queries={queries} mutate={mutate} />
      )}
      {loading && <Loader />}
    </div>
  );
};

export default Page;
