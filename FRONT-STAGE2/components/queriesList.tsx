import React from 'react';
import {
  Accordion,
  AccordionItem,
  Avatar,
  Button,
  Card,
  ScrollShadow,
} from '@nextui-org/react';
import QueryResponseDTO from '@/interfaces/QueryResponseDTO';
import AddComment from './addComment';
import CommentRequestDTO from '@/interfaces/CommentRequestDTO';
import useQuery from '@/hooks/useQuery';
import DataFilterDTO from '@/interfaces/DataFilterDTO';
import { useRouter } from 'next/navigation';

type Props = {
  queries: QueryResponseDTO[];
  mutate: () => void;
};

export default function QueriesList({ queries, mutate }: Props) {
  const { createComment } = useQuery();
  const router = useRouter();
  const handleCreateComment = async (queryId: string, comment: string) => {
    const newComment: CommentRequestDTO = {
      comment,
      username: localStorage.getItem('username') || '',
      queryId: queryId,
    };

    const res = await createComment(newComment);
    if (res) {
      mutate();
    }
  };

  const handleGoToQuery = (dataFilter: DataFilterDTO) => {
    if (!dataFilter) return;
    const { countries, dmaList, endDate, limitData, regions, startDate, term } =
      dataFilter;
    //Add to query params
    const params = new URLSearchParams();

    if (countries && countries.length > 0) {
      params.append('countries', countries.join(','));
    }

    if (dmaList && dmaList.length > 0) {
      params.append('dmaList', dmaList.join(','));
    }

    if (endDate && endDate.length > 0) {
      params.append('endDate', endDate);
    }

    if (limitData && limitData > 0) {
      params.append('limit', limitData.toString());
    }

    if (regions && regions.length > 0) {
      params.append('regions', regions.join(','));
    }

    if (startDate && startDate.length > 0) {
      params.append('startDate', startDate);
    }

    if (term && term.length > 0) {
      params.append('term', term);
    }

    router.push(`/home/create?${params.toString()}`);
  };
  return (
    <Accordion variant="splitted" className="w-full">
      {queries.map((query) => (
        <AccordionItem
          key={query.id}
          startContent={
            <Avatar
              isBordered
              color="primary"
              radius="lg"
              size="sm"
              src="https://cdnjs.cloudflare.com/ajax/libs/design-system/3.0.0-dev/icons/standard/avatar.svg"
            />
          }
          subtitle={`${query.comments.length} comments`}
          title={
            <div className="flex justify-between items-center">
              {' '}
              <span>{query.username}</span>
              <span className="font-bold pr-5">
                Query name: {query.queryName}
              </span>
              <Button
                color="primary"
                size="sm"
                onClick={() => handleGoToQuery(query.dataFilter)}
              >
                Go
              </Button>
            </div>
          }
        >
          <span>
            {' '}
            <b>Description:</b> {query.description}
          </span>
          {/* Comments Bar */}
          <div className="mt-4 p-4 border rounded">
            <h2 className="text-lg font-semibold mb-3">Comments:</h2>
            <ScrollShadow className="h-[300px]">
              {query.comments.map((comment) => (
                <Card
                  key={comment.id}
                  className="flex items-start m-3 flex-row p-5 space-x-2"
                >
                  <span className="font-semibold">{comment.username}:</span>
                  <p className="text-default-400">{comment.comment}</p>
                </Card>
              ))}
            </ScrollShadow>
            <AddComment onCreate={handleCreateComment} queryId={query.id} />
          </div>
        </AccordionItem>
      ))}
    </Accordion>
  );
}
