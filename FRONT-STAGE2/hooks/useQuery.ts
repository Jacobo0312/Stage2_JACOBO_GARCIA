import CommentRequestDTO from '@/interfaces/CommentRequestDTO';
import DataFilterDTO from '@/interfaces/DataFilterDTO';
import { GoogleTrendsResponseDTO } from '@/interfaces/GoogleTrendsResponseDTO';
import QueryRequestDTO from '@/interfaces/QueryRequestDTO';
import { useState } from 'react';

const useQuery = () => {
  const [data, setData] = useState<GoogleTrendsResponseDTO | null>(null);
  const [loading, setLoading] = useState(false);

  const runQuery = async (dataFilterDTO: DataFilterDTO) => {
    setLoading(true);

    try {
      const response = await fetch(
        `${process.env.API_URL}google-trends/create`,
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(dataFilterDTO),
        },
      );

      const result = await response.json();
      setData(result);
    } catch (error) {
      console.error('Error running query:', error);
      setData(null);
    } finally {
      setLoading(false);
    }
  };

  const saveQuery = async (query: QueryRequestDTO) => {
    setLoading(true);

    try {
      const response = await fetch(`${process.env.API_URL}queries/create`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(query),
      });

      const result = await response.json();
      return result;
    } catch (error) {
      console.error('Error saving query:', error);
    } finally {
      setLoading(false);
    }
  };

  const createComment = async (comment: CommentRequestDTO) => {
    try {
      const response = await fetch(`${process.env.API_URL}comments/create`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(comment),
      });

      const result = await response.json();
      return result;
    } catch (error) {
      console.error('Error saving comment:', error);
    }
  };

  return { data, loading, runQuery, saveQuery, createComment };
};

export default useQuery;
