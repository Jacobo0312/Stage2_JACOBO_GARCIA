'use client';

import React from 'react';
import { title, subtitle } from '@/components/primitives';
import { Divider, Input, Button } from '@nextui-org/react';
import { useRouter } from 'next/navigation';
import Loader from '@/components/loader';
import GoogleTrendsLogo from '../public/google_trends.svg';
import Image from 'next/image';

export default function Home() {
  const [username, setUsername] = React.useState('');
  const [loading, setLoading] = React.useState(false);
  const [error, setError] = React.useState('');
  //Router
  const router = useRouter();

  React.useEffect(() => {
    const username = localStorage.getItem('username');
    if (username) {
      router.push('/home/dashboard');
    }
  }, [router]);

  const handleStartApplication = async () => {
    //Reset errors
    setError('');

    //Validate data

    if (username === '') {
      setError('Username is required');
      return;
    }

    localStorage.setItem('username', username);
    setLoading(true);
    //Redirect to application
    router.push('/home/dashboard');
  };

  if (loading) return <Loader />;

  return (
    <section className="flex flex-col items-center justify-center gap-4 pb-8 md:pb-10">
      <div className="inline-block max-w-lg text-center justify-center">
        <Image src={GoogleTrendsLogo} alt="Google Trends Logo" />
        <h1 className={title()}>Query builder</h1>
        <br />
        <h2 className={subtitle({ class: 'mt-4' })}>
          Build queries for Google Trends effortlessly and efficiently.
        </h2>
      </div>
      {/* Id */}
      <div className="mt-8"></div>
      <Divider className="my-4" />
      <Input
        type="text"
        label="Username"
        placeholder="Input your username"
        variant="bordered"
        className="max-w-xs"
        onChange={(e) => setUsername(e.target.value)}
        errorMessage={error}
      />

      <Button color="primary" onClick={handleStartApplication}>
        Login
      </Button>
    </section>
  );
}
