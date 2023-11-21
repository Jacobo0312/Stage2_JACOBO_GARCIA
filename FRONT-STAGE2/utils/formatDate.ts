export function formatDateToYYYYMMDD(date: Date): string {
  const year = date.getFullYear();
  const month = (date.getMonth() + 1).toString().padStart(2, '0');
  const day = date.getDate().toString().padStart(2, '0');

  return `${year}-${month}-${day}`;
}

export function parseDateString(dateString: string): Date {
  const dateParts = dateString.match(/^(\d{4})-(\d{2})-(\d{2})$/);

  if (dateParts) {
    const [, year, month, day] = dateParts;
    const parsedDate = new Date(
      parseInt(year),
      parseInt(month) - 1,
      parseInt(day),
    );

    // Check if the parsed date is valid
    if (!isNaN(parsedDate.getTime())) {
      return parsedDate;
    }
  }

  return new Date(); // Return null for invalid date strings
}
