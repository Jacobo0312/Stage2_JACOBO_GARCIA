'use client';

import {
  Kbd,
  Input,
  Navbar as NextUINavbar,
  NavbarContent,
  NavbarMenu,
  NavbarMenuToggle,
  NavbarBrand,
  NavbarItem,
  NavbarMenuItem,
  Tabs,
  Tab,
} from '@nextui-org/react';
import {
  Dropdown,
  DropdownTrigger,
  DropdownMenu,
  DropdownItem,
  Button,
} from '@nextui-org/react';

import { link as linkStyles } from '@nextui-org/theme';

import { siteConfig } from '@/config/site';
import NextLink from 'next/link';

import { ThemeSwitch } from '@/components/theme-switch';
import {
  TwitterIcon,
  GithubIcon,
  DiscordIcon,
  HeartFilledIcon,
  SearchIcon,
} from '@/components/icons';

import { useRouter } from 'next/navigation';
import React from 'react';
import Loader from './loader';

export const Navbar = () => {
  //Router
  const router = useRouter();
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

  //   const searchInput = (
  //     <Input
  //       aria-label="Search"
  //       classNames={{
  //         inputWrapper: "bg-default-100",
  //         input: "text-sm",
  //       }}
  //       endContent={
  //         <Kbd className="hidden lg:inline-block" keys={["command"]}>
  //           K
  //         </Kbd>
  //       }
  //       labelPlacement="outside"
  //       placeholder="Search..."
  //       startContent={
  //         <SearchIcon className="text-base text-default-400 pointer-events-none flex-shrink-0" />
  //       }
  //       type="search"
  //     />
  //   );

  // return (
  // 	<NextUINavbar maxWidth="xl" position="sticky">
  // 		<NavbarContent className="basis-1/5 sm:basis-full" justify="start">
  // 			<NavbarBrand className="gap-3 max-w-fit">
  // 				<NextLink className="flex justify-start items-center gap-1" href="/">
  // 					<Logo />
  // 					<p className="font-bold text-inherit">ACME</p>
  // 				</NextLink>
  // 			</NavbarBrand>
  // 			<div className="hidden lg:flex gap-4 justify-start ml-2">
  // 				{siteConfig.navItems.map((item) => (
  // 					<NavbarItem key={item.href}>
  // 						<NextLink
  // 							className={clsx(
  // 								linkStyles({ color: "foreground" }),
  // 								"data-[active=true]:text-primary data-[active=true]:font-medium"
  // 							)}
  // 							color="foreground"
  // 							href={item.href}
  // 						>
  // 							{item.label}
  // 						</NextLink>
  // 					</NavbarItem>
  // 				))}
  // 			</div>
  // 		</NavbarContent>

  //   <NavbarContent className="hidden sm:flex basis-1/5 sm:basis-full" justify="end">
  // 			<NavbarItem className="hidden sm:flex gap-2">
  // 				<Link isExternal href={siteConfig.links.twitter}>
  // 					<TwitterIcon className="text-default-500" />
  // 				</Link>
  // 				<Link isExternal href={siteConfig.links.discord}>
  // 					<DiscordIcon className="text-default-500" />
  // 				</Link>
  // 				<Link isExternal href={siteConfig.links.github}>
  // 					<GithubIcon className="text-default-500" />
  // 				</Link>
  // 				<ThemeSwitch />
  // 			</NavbarItem>
  // 			<NavbarItem className="hidden lg:flex">{searchInput}</NavbarItem>
  // 			<NavbarItem className="hidden md:flex">
  // 				<Button
  // 					isExternal
  // 					as={Link}
  // 					className="text-sm font-normal text-default-600 bg-default-100"
  // 					href={siteConfig.links.sponsor}
  // 					startContent={<HeartFilledIcon className="text-danger" />}
  // 					variant="flat"
  // 				>
  // 					Sponsor
  // 				</Button>
  // 			</NavbarItem>
  // 		</NavbarContent>

  // 		<NavbarContent className="sm:hidden basis-1 pl-4" justify="end">
  //     <Link isExternal href={siteConfig.links.github}>
  //       <GithubIcon className="text-default-500" />
  //     </Link>
  //     <ThemeSwitch />
  // 			<NavbarMenuToggle />
  //   </NavbarContent>

  //   <NavbarMenu>
  // 			{searchInput}
  // 			<div className="mx-4 mt-2 flex flex-col gap-2">
  // 				{siteConfig.navMenuItems.map((item, index) => (
  // 					<NavbarMenuItem key={`${item}-${index}`}>
  // 						<Link
  // 							color={
  // 								index === 2
  // 									? "primary"
  // 									: index === siteConfig.navMenuItems.length - 1
  // 									? "danger"
  // 									: "foreground"
  // 							}
  // 							href="#"
  // 							size="lg"
  // 						>
  // 							{item.label}
  // 						</Link>
  // 					</NavbarMenuItem>
  // 				))}
  // 			</div>
  // 		</NavbarMenu>
  // 	</NextUINavbar>
  // );

  if (loading) return <Loader />;

  return (
    <NextUINavbar maxWidth="xl" position="sticky">
      <NavbarContent className="basis-1/5 sm:basis-full" justify="center">
        <Tabs aria-label="Options" color="primary" variant="bordered" size="lg">
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
          <Tab
            key="user"
            title={
              <Dropdown>
                <DropdownTrigger>
                  <Button variant="light">{username}</Button>
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
            }
          />
        </Tabs>
      </NavbarContent>
    </NextUINavbar>
  );
};
