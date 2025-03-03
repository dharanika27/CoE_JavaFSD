import React, { useState } from "react";
import { useCart } from "../context/CartContext";

const Cart = () => {
  const { cart, removeFromCart } = useCart();
  const [successMessage, setSuccessMessage] = useState("");

  const handleRemoveFromCart = (id) => {
    removeFromCart(id);
    setSuccessMessage("Removed from cart successfully!");
    setTimeout(() => setSuccessMessage(""), 2000);
  };

  return (
    <div className="p-4">
      <h2 className="text-xl font-bold">Your Cart</h2>

      {successMessage && (
        <p className="text-green-500 mt-2">{successMessage}</p>
      )}

      {cart.length === 0 ? (
        <p>Your cart is empty.</p>
      ) : (
        <ul>
          {cart.map((item) => (
            <li key={item.id} className="flex justify-between p-2 border-b">
              <span>{item.name} - ₹{item.price}</span>
              <button
                className="bg-red-500 text-white px-2 py-1 rounded"
                onClick={() => handleRemoveFromCart(item.id)}
              >
                Remove
              </button>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default Cart;
