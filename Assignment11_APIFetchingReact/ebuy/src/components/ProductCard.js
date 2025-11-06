import React from 'react';
import { Link } from 'react-router-dom';
import './ProductCard.css';

function ProductCard({ product }) {
  return (
    <Link to={`/product/${product.id}`} className="product-card-link">
      <div className="product-card">
        <div className="product-image-container">
          <img 
            src={product.image} 
            alt={product.title} 
            className="product-image"
          />
        </div>
        <div className="product-info">
          <h3 className="product-title">{product.title}</h3>
          <p className="product-category">{product.category}</p>
          <div className="product-rating">
            <span className="rating-star">⭐</span>
            <span>{product.rating?.rate || 'N/A'} / 5</span>
          </div>
          <div className="product-footer">
            <p className="product-price">${product.price}</p>
          </div>
        </div>
      </div>
    </Link>
  );
}

export default ProductCard;
