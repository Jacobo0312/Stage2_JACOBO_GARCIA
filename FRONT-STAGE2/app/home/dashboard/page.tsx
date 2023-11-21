'use client';

import HorizontalBarChart from '@/components/horizontalBarChart';
import { GoogleTrendsResponseDTO } from '@/interfaces/GoogleTrendsResponseDTO';
import React from 'react';
import { subtitle } from '@/components/primitives';

async function getTopTermGlobal() {
  const response = await fetch(
    `${process.env.API_URL}google-trends/top_terms_global`,
  );
  const data = await response.json();
  return data;
}

const Page = () => {
  const [data, setData] = React.useState<GoogleTrendsResponseDTO>();
  const [loading, setLoading] = React.useState(true);

  React.useEffect(() => {
    getTopTermGlobal().then((data) => {
      setData(data);
      setLoading(false);
    });
  }, []);

  return (
    <>
      <h1 className={subtitle({})} style={{ textAlign: 'center' }}>
        TOP 25 TRENDING GOOGLE SEARCH TERMS IN LAST WEEK
      </h1>
      <div className="grid grid-cols-2 gap-4 p-2">
        <HorizontalBarChart
          titleText="Top 25 Term USA"
          chartData={data?.termUSAList || []}
          loading={loading}
        />
        <HorizontalBarChart
          titleText="Top Term 25 International"
          chartData={data?.termInternationalList || []}
          loading={loading}
        />
        <HorizontalBarChart
          titleText="Top Term 25 Rising USA"
          chartData={data?.termRisingUSAList || []}
          loading={loading}
        />
        <HorizontalBarChart
          titleText="Top Term 25 Rising International"
          chartData={data?.termRisingInternationalList || []}
          loading={loading}
        />
      </div>
    </>
  );
};

export default Page;
