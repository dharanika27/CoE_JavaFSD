import React, { useState } from "react";
import ProductCard from "../components/ProductCard";
import { useCart } from "../context/CartContext";

const Home = () => {
  const { addToCart } = useCart();
  const [products, setProducts] = useState([
    { id: 1, name: "Product 1", price: 1999 },
    { id: 2, name: "Product 2", price: 3998 },
    { id: 3, name: "Product 3", price: 5997 }
  ]);

  const [newProduct, setNewProduct] = useState({ name: "", price: "" });

  const handleAddProduct = () => {
    if (newProduct.name && newProduct.price) {
      const productToAdd = { id: Date.now(), name: newProduct.name, price: Number(newProduct.price) };
      setProducts([...products, productToAdd]);  // Update product list
      addToCart(productToAdd);  // Directly add to cart
      setNewProduct({ name: "", price: "" }); // Clear input fields
    }
  };

  return (
    <div className="p-6">
      <h1 className="text-3xl font-bold text-center">
        Welcome to <span className="text-yellow-400">Shoppy</span>
      </h1>

      <div className="bg-gray-200 p-4 rounded-lg text-center my-4">
        <h2 className="text-lg font-bold">Add a New Product</h2>
        <input
          type="text"
          placeholder="Product Name"
          value={newProduct.name}
          onChange={(e) => setNewProduct({ ...newProduct, name: e.target.value })}
          className="border p-2 m-2"
        />
        <input
          type="number"
          placeholder="Price"
          value={newProduct.price}
          onChange={(e) => setNewProduct({ ...newProduct, price: e.target.value })}
          className="border p-2 m-2"
        />
        <button
          onClick={handleAddProduct}
          className="bg-green-500 text-white px-4 py-2 rounded"
        >
          Add to Cart
        </button>
      </div>

      <h2 className="text-xl font-bold text-center">Products</h2>
      <div className="grid grid-cols-3 gap-4">
        {products.map((product) => (
          <ProductCard key={product.id} product={product} />
        ))}
      </div>
    </div>
  );
};

export default Home;
