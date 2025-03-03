import React, { useState } from "react";
import { useCart } from "../context/CartContext";

const ProductCard = ({ product }) => {
    const { addToCart } = useCart();
    const [successMessage, setSuccessMessage] = useState("");

    const handleAddToCart = () => {
        addToCart(product);
        setSuccessMessage("Added to cart successfully!");
        setTimeout(() => setSuccessMessage(""), 2000);
    };

    return (
        <div className="bg-white p-4 rounded-lg shadow-md text-center">
            <h2 className="font-bold">{product.name}</h2>
            <p>₹{product.price}</p>
            <button
                className="mt-2 bg-blue-500 text-white px-4 py-2 rounded"
                onClick={handleAddToCart}
            >
                Add to Cart
            </button>
            {successMessage && <p className="text-green-500 mt-2">{successMessage}</p>}
        </div>
    );
};

export default ProductCard;
