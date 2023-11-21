import React, { useEffect } from 'react';
import { useState } from 'react';
import { Chip, Input } from '@nextui-org/react';
import { Textarea } from '@nextui-org/react';
import {
  Modal,
  ModalContent,
  ModalHeader,
  ModalBody,
  ModalFooter,
  Button,
  useDisclosure,
} from '@nextui-org/react';
import QueryRequestDTO from '@/interfaces/QueryRequestDTO';
import useQuery from '@/hooks/useQuery';
import { set } from 'date-fns';
import { SaveIcon } from 'lucide-react';

type Props = {
  query: QueryRequestDTO;
  onOpen: () => void;
  isOpen: boolean;
  onOpenChange: () => void;
};
const ModalSaveQuery = ({ query, isOpen, onOpen, onOpenChange }: Props) => {
  const [loading, setLoading] = useState(false);
  const { saveQuery } = useQuery();
  const [queryName, setQueryName] = useState('');
  const [description, setDescription] = useState('');
  const [error, setError] = useState('');

  const handleSaveQuery = async () => {
    setLoading(true);

    //Validate

    if (!queryName) {
      setError('Please enter the name');
      setLoading(false);
      return;
    }

    if (!description) {
      setError('Please enter the description');
      setLoading(false);
      return;
    }
    const queryToSave = {
      ...query,
      queryName: queryName,
      description: description,
    };

    const res = await saveQuery(queryToSave);
    if (res.error) {
      setError("Couldn't save the query");
      setLoading(false);
      return;
    }
    setLoading(false);
    onOpenChange();
  };

  return (
    <>
      <Modal isOpen={isOpen} onOpenChange={onOpenChange}>
        <ModalContent>
          {(onClose) => (
            <>
              <ModalHeader className="flex flex-row gap-1 space-x-5">
                <div className="flex flex-row space-x-2">
                  <SaveIcon />
                  <span>Save Query</span>
                </div>
                {error && (
                  <Chip
                    color="danger"
                    onClose={() => setError('')}
                    variant="bordered"
                  >
                    {error}
                  </Chip>
                )}
              </ModalHeader>
              <ModalBody>
                <Input
                  value={queryName}
                  onChange={(value) => setQueryName(value.target.value)}
                  type="text"
                  label="Name Query"
                  placeholder="Enter the name of the query"
                />
                <Textarea
                  value={description}
                  label="Description"
                  placeholder="Enter your description"
                  onChange={(value) => setDescription(value.target.value)}
                />
              </ModalBody>
              <ModalFooter>
                <Button color="danger" variant="light" onPress={onClose}>
                  Close
                </Button>
                <Button
                  color="primary"
                  onClick={handleSaveQuery}
                  isLoading={loading}
                >
                  Save
                </Button>
              </ModalFooter>
            </>
          )}
        </ModalContent>
      </Modal>
    </>
  );
};

export default ModalSaveQuery;
