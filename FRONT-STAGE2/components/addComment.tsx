import React, { useState, useEffect } from 'react';
import { Button, Textarea } from '@nextui-org/react';
import { SendIcon } from './icons';

interface AddCommentProps {
  queryId: string;
  onCreate: (queryId: string, comment: string) => void;
}

const AddComment = ({ onCreate, queryId }: AddCommentProps) => {
  const [commentText, setCommentText] = useState<string>('');

  const handleCommentText = (value: string) => {
    setCommentText(value);
  };

  useEffect(() => {
    setCommentText('');
  }, [onCreate]);
  return (
    <div className="flex items-center justify-between w-full mt-4">
      <div className="flex-grow pr-4">
        <Textarea
          value={commentText}
          label="Comment"
          placeholder="Enter your comment"
          onChange={(e) => handleCommentText(e.target.value)}
        />
      </div>
      <div>
        <Button
          color="primary"
          onClick={() => onCreate(queryId, commentText)}
          isIconOnly
        >
          <div className="flex justify-center items-center">
            <SendIcon />
          </div>
        </Button>
      </div>
    </div>
  );
};

export default AddComment;
