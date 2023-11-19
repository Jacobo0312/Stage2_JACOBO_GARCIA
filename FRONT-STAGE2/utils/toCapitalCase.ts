export const toCapitalCase = (str: string) => {
  return str.replace(/\b\w/g, (match) => match.toUpperCase());
};
