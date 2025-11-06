import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import './ProductDetailsPage.css';
import axios from 'axios';

function ProductDetailsPage() {
  const { id } = useParams();
  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [quantity, setQuantity] = useState(1);

  useEffect(() => {
    const fetchProduct = async () => {
      try {
        setLoading(true);
        const response = await axios.get(`https://fakestoreapi.com/products/${id}`);
        if (!response.ok) {
          throw new Error('Product not found');
        }
        const data =  response.data;
        setProduct(data);
        setError(null);
      } catch (err) {
        setError('Failed to load product details');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchProduct();
  }, [id]);

  const handleQuantityChange = (e) => {
    const value = parseInt(e.target.value);
    if (value > 0) {
      setQuantity(value);
    }
  };
  if (loading) {
    return <div className="loading-container">Loading product details...</div>;
  }

  if (error || !product) {
    return (
      <div className="error-container">
        <p>{error || 'Product not found'}</p>
        <Link to="/" className="back-link">Back to Products</Link>
      </div>
    );
  }

  return (
    <main className="product-details-page">
      <div className="details-container">
        {/* Back Button */}
        <Link to="/" className="back-button">← Back to Products</Link>

        <div className="product-details-wrapper">
          {/* Product Image */}
          <div className="product-image-section">
            <div className="image-box">
              <img
                src={product.image}
                alt={product.title}
                className="detail-product-image"
              />
            </div>
          </div>

          {/* Product Information */}
          <div className="product-info-section">
            <h1 className="detail-product-title">{product.title}</h1>
            
            <p className="detail-product-category">
              Category: <span>{product.category}</span>
            </p>

            <div className="detail-product-rating">
              <span className="rating-star">⭐</span>
              <span className="rating-value">{product.rating?.rate || 'N/A'} / 5</span>
              <span className="rating-count">
                ({product.rating?.count || 0} reviews)
              </span>
            </div>

            <div className="detail-product-price">
              <span className="price">${product.price}</span>
            </div>

            <div className="detail-product-description">
              <h3>Description</h3>
              <p>{product.description}</p>
            </div>

            {/* Purchase Section */}
            <div className="purchase-section">
              <div className="quantity-selector">
                <label htmlFor="quantity">Quantity:</label>
                <select 
                  id="quantity" 
                  value={quantity}
                  onChange={handleQuantityChange}
                  className="quantity-select"
                >
                  {[1, 2, 3, 4, 5, 6, 7, 8, 9, 10].map(num => (
                    <option key={num} value={num}>{num}</option>
                  ))}
                </select>
              </div>

              <button 
                className="add-to-cart-btn-large"
              >
                Add to Cart
              </button>

              <button className="buy-now-btn">
                Buy Now
              </button>
            </div>

            {/* Additional Info */}
            <div className="additional-info">
              <div className="info-item">
                <span>✓ Free Shipping</span>
              </div>
              <div className="info-item">
                <span>✓ 30-Day Returns</span>
              </div>
              <div className="info-item">
                <span>✓ Secure Payment</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  );
}

export default ProductDetailsPage;
