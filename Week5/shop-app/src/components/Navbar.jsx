import React from "react";
import { Link } from "react-router-dom";

const Navbar = () => {
    return (
        <nav className="bg-gray-900 text-white p-4 flex justify-between items-center">
            <h1 className="text-2xl font-bold text-yellow-400">Shoppy</h1>
            <div>
                <Link to="/" className="mr-4 text-yellow-300 font-bold hover:text-yellow-500">
                    Home
                </Link>
                <Link to="/cart" className="text-yellow-300 font-bold hover:text-yellow-500">
                    Cart
                </Link>
            </div>
        </nav>
    );
};

export default Navbar;
