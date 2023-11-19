import dynamic from 'next/dynamic';
import { title } from './primitives';
import { Card, CardHeader, Skeleton } from '@nextui-org/react';
import { toCapitalCase } from '@/utils/toCapitalCase';

const Chart = dynamic(() => import('react-apexcharts'), { ssr: false });

type CharData = { term: string; score: number };
type Props = { titleText: string; chartData: CharData[]; loading: boolean };

const HorizontalBarChart = ({ titleText, chartData, loading }: Props) => {
  const chartLabels = chartData.map(
    (i: CharData, index: number) => index + 1 + ' - ' + toCapitalCase(i.term),
  );

  const chartOptions: ApexCharts.ApexOptions = {
    chart: { type: 'bar' as const, toolbar: { show: false } },
    tooltip: {
      y: {
        title: {
          formatter: () => 'Score:',
        },
      },
    },
    plotOptions: {
      bar: {
        horizontal: true,
        borderRadius: 3,
        barHeight: '30px',
        //distributed: true, //Colors
      },
    },
    annotations: {
      points: chartData.map((item: CharData, index: number) => ({
        x: item.score,
        y: index,
        marker: {
          size: 0,
        },
        text: {
          style: {
            fontSize: '12px',
            color: '#FFFFFF',
          },
        },
      })),
    },
    xaxis: {
      title: {
        text: 'AVERAGE SCORE (INDEX)',
        style: {
          fontSize: '12px',
        },
      },
      categories: chartLabels,
      axisBorder: { show: false },
      axisTicks: { show: false },
      labels: {
        show: false,
        style: {
          fontSize: '0px',
        },
      },
    },
    yaxis: {
      axisBorder: { show: true },
    },
  };

  const seriesData = chartData.map((item: CharData) => item.score);

  return (
    <Card>
      <Skeleton isLoaded={!loading}>
        <CardHeader className="justify-center flex bg-slate-200">
          <h2
            className={title({
              size: 'sm',
              color: 'blue',
            })}
          >
            {titleText}
          </h2>
        </CardHeader>
        <div className="px-4">
          <Chart
            type="bar"
            series={[{ data: seriesData }]}
            options={chartOptions}
            height={1000}
            width={600}
          />
        </div>
      </Skeleton>
    </Card>
  );
};
export default HorizontalBarChart;
