'use client';

import { Tabs, Tab, Avatar } from '@nextui-org/react';
import {
  Dropdown,
  DropdownTrigger,
  DropdownMenu,
  DropdownItem,
  Button,
} from '@nextui-org/react';

import GoogleTrendsLogo from '../public/google_trends.svg';

import { siteConfig } from '@/config/site';
import NextLink from 'next/link';

import { useRouter } from 'next/navigation';
import { usePathname } from 'next/navigation';

import React from 'react';
import Loader from './loader';
import Image from 'next/image';

export const Navbar = () => {
  //Router
  const router = useRouter();
  const pathname = usePathname();
  const [username, setUsername] = React.useState('');
  const [loading, setLoading] = React.useState(false);
  React.useEffect(() => {
    setLoading(true);
    const username = localStorage.getItem('username');
    if (!username) {
      router.push('/');
    } else {
      setUsername(username);
      setLoading(false);
    }
  }, [router]);

  const logOut = () => {
    setLoading(true);
    localStorage.removeItem('username');
    router.push('/');
  };

  if (loading) return <Loader />;

  return (
    <div className="flex space-x-64 items-center">
      {/* LOGO */}
      <Image src={GoogleTrendsLogo} alt="Google Trends Logo" width={200} />

      {/* TABS */}
      <Tabs
        aria-label="Options"
        color="primary"
        variant="bordered"
        size="lg"
        selectedKey={pathname}
      >
        {siteConfig.navItems.map((item) => (
          <Tab
            key={item.href}
            title={
              <div className="flex items-center space-x-2 w-20 justify-center">
                <NextLink href={item.href}> {item.label} </NextLink>
              </div>
            }
          />
        ))}
      </Tabs>

      {/* USER */}
      <Dropdown>
        <DropdownTrigger>
          <Button variant="light">
            <>
              <Avatar
                alt={username}
                className="w-6 h-6"
                src="https://cdnjs.cloudflare.com/ajax/libs/design-system/3.0.0-dev/icons/standard/avatar.svg"
              />
              {username}
            </>
          </Button>
        </DropdownTrigger>
        <DropdownMenu aria-label="Static Actions">
          <DropdownItem
            key="logOut"
            className="text-danger text-center"
            color="danger"
            onClick={logOut}
          >
            Log out
          </DropdownItem>
        </DropdownMenu>
      </Dropdown>
    </div>
  );
};
