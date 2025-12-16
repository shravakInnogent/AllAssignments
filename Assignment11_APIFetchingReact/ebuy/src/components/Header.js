import React from 'react';
import { Link } from 'react-router-dom';
import './Header.css';

function Header() {
  return (
    <header className="header">
      <div className="header-container">
        <div className="logo">
          <Link to="/">
          <img 
          src={process.env.PUBLIC_URL + '/images/logo.png'} 
          alt="eBuy Logo" 
          className="logo-image"
          />
            <h1>eBuy</h1>
          </Link>
        </div>
        
        <div className="header-icons">
          <div className="icon-item">
             <span className="icon">🛒</span>
             <p>Cart</p>
          </div>
          <div className="icon-item">
            <span className="icon">🔔</span>
            <p>Notifications</p>
          </div>
          <div className="icon-item">
            <span className="icon">👤</span>
            <p>Profile</p>
          </div>
        </div>
      </div>
    </header>
  );
}

export default Header;
