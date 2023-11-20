import { Navbar } from '@/components/navbar';

export default function PricingLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <section className="flex flex-col items-center justify-center gap-4 w-full">
      {children}
    </section>
  );
}
