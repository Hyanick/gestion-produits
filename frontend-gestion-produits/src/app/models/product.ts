export interface Product {
  id: number;
  name: string;
  price: number;
  description: string;
  images: Images[];
  createdAt: string;
}

export interface Images {
  url: string;
}
